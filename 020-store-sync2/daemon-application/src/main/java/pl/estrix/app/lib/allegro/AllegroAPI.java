package pl.estrix.app.lib.allegro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import pl.estrix.app.lib.allegro.model.*;
import pl.estrix.app.lib.allegro.oauth.OAuthConfig;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AllegroAPI implements Allegro {

    private static final String API_URL = "https://api.allegro.pl";
    private static final String API_URL_FORMAT = "%s/auth/oauth/device";
    private static final String API_REFRESH_URL_FORMAT = "%s/auth/oauth/token";
    private static final String API_GET_LAST_EVENT_URL_FORMAT = "%s/order/event-stats";
    private static final String API_GET_PRODUCTS_URL_FORMAT = "%s/sale/products";
    private static final String API_GET_OFFERTS_URL_FORMAT = "%s/sale/offers";
    private static final String API_GET_SEARCH_OFFER_URL_FORMAT = "%s/offers/listing";
    private static final String API_GET_CATEGORIES_URL_FORMAT = "%s/sale/categories";
    private static final String URL_SECURED_FORMAT = "%s?%s";

    private org.apache.http.client.HttpClient client;
    private OAuthConfig config;
    private String base64Secure;

    private Base64.Encoder encoder = Base64.getEncoder();
    private final Gson gson = new GsonBuilder().create();

    public AllegroAPI(OAuthConfig config) {
        this.config = config;
        this.client = HttpClientBuilder.create().setConnectionTimeToLive(3000, TimeUnit.MILLISECONDS).build();
        base64Secure = encoder.encodeToString((config.getConsumerKey()+":" + config.getConsumerSecret()).getBytes());
    }

    @Override
    public AllegroDeviceDto doConnectionWithApp() {
        try {
            String url = String.format(API_URL_FORMAT, config.getUrl());

            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            post.setHeader("Authorization", "Basic " + base64Secure);

            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("client_id", config.getConsumerKey()));

            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            HttpResponse response = client.execute(post);
//            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }


            return gson.fromJson(result.toString(), AllegroDeviceDto.class);
        } catch (IOException e ){
            return null;
        }
    }

    @Override
    public AllegroAccessDto waitForUserAccept(AllegroDeviceDto device) throws IOException {

        StringBuffer result = new StringBuffer();
        String url2 = "https://allegro.pl/auth/oauth/token?grant_type=urn%3Aietf%3Aparams%3Aoauth%3Agrant-type%3Adevice_code&device_code="+device.getDevice_code();
        org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url2);

        post.setHeader("Authorization", "Basic " + base64Secure);

        for (int i =0; i<20;i++) {
            try {
                result.delete(0,result.toString().length());
                HttpResponse response = client.execute(post);

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                response.getEntity().getContent().close();
                System.out.println("waitForUserAccept: " + result.toString());
                if (response.getStatusLine().getStatusCode() == 200 ) {
                    AllegroAccessDto accessDto = gson.fromJson(result.toString(), AllegroAccessDto.class);
                    return accessDto;
                }
                try{
                    Thread.sleep(5500);
//                    this.wait(5500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (NoHttpResponseException | IllegalMonitorStateException e){
                break;
            }
        }

        return null;
    }

    @Override
    public AllegroAccessDto refreshToken(AllegroAccessDto accessDto) {
        try {
            String url = String.format(API_REFRESH_URL_FORMAT, config.getUrl());

            HttpPost post = new HttpPost(url);
            post.setHeader("Authorization", "Basic " + base64Secure);

            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("grant_type", accessDto.getToken_type()));
//            urlParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
            urlParameters.add(new BasicNameValuePair("refresh_token", accessDto.getRefresh_token()));

            String url2 = new UrlEncodedFormEntity(urlParameters).toString();

//            System.out.println("refreshToken: " + url2);

            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();

            HttpResponse response = client.execute(post);
//            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

//            System.out.println("refreshToken: " + result.toString());

            accessDto = gson.fromJson(result.toString(), AllegroAccessDto.class);

//            System.out.println("------------ Decode JWT ------------");
            String[] split_string = accessDto.getAccess_token().split("\\.");
            String base64EncodedHeader = split_string[0];
            String base64EncodedBody = split_string[1];
            String base64EncodedSignature = split_string[2];

//            System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
            org.apache.commons.codec.binary.Base64 base64Url = new org.apache.commons.codec.binary.Base64(true);
            String header = new String(base64Url.decode(base64EncodedHeader));
//            System.out.println("JWT Header : " + header);


//            System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
            String body = new String(base64Url.decode(base64EncodedBody));
//            System.out.println("JWT Body : "+body);
            AllegroAccessHelpDto hlp = gson.fromJson(body, AllegroAccessHelpDto.class);

            accessDto.setJti(hlp.getJti());
            accessDto.setUser_name(hlp.getUser_name());
            accessDto.setClient_id(hlp.getClient_id());


            return accessDto;
        } catch (IOException e ){
            return null;
        }
    }

    @Override
    public AllegroEventsDto getLastEvent(String token) throws IOException {
        AllegroEventsDto resultDto;
        String url = String.format(API_GET_LAST_EVENT_URL_FORMAT, API_URL);


//        System.out.println("url: " + url);
        HttpGet request = new HttpGet(url);
        request.setHeader("authorization", "Bearer  " + token);
        request.setHeader("accept", "application/vnd.allegro.beta.v1+json");

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println("result: " + result.toString());

        resultDto = gson.fromJson(result.toString(), AllegroEventsDto.class);

        return resultDto;
    }

    @Override
    public List<AllegroEventsDto> getEventFrom(AllegroEventsDto event) {
        return null;
    }

    @Override
    public List<AllegroProductDto> getProducts(String token) throws IOException {
        AllegroProductDto resultDto;
        String url = String.format(API_GET_PRODUCTS_URL_FORMAT, API_URL);
        System.out.println("url: " + url);
        HttpGet request = new HttpGet(url);
        request.setHeader("authorization", "Bearer  " + token);
        request.setHeader("accept", "application/vnd.allegro.beta.v1+json");

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println("getProducts: " + result.toString());

//        resultDto = gson.fromJson(result.toString(), AllegroProductDto.class);

        return null;
    }

    @Override
    public AllegroOffertListDto getOfferts(String token) throws IOException {
        AllegroOffertListDto resultDto;
        String url = String.format(API_GET_OFFERTS_URL_FORMAT, API_URL);
        System.out.println("url: " + url);
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer  " + token);
        request.setHeader("accept", "application/vnd.allegro.beta.v1+json");
        request.setHeader("content-type", "application/vnd.allegro.beta.v1+json");

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
//        System.out.println("getOfferts: " + result.toString());

        resultDto = gson.fromJson(result.toString(), AllegroOffertListDto.class);

        return resultDto;
    }

    @Override
    public AllegroSearchOfferDto getSearchOfferts(String token, String sellerId) throws IOException {
        org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();

        AllegroSearchOfferDto resultDto;
        String url = String.format(API_GET_SEARCH_OFFER_URL_FORMAT, API_URL);
        url += "?seller.id=" + sellerId;
        System.out.println("url: " + url);
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer  " + token);
        request.setHeader("Accept", "application/vnd.allegro.public.v1+json");

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
//        System.out.println("getOfferts: " + result.toString());

        resultDto = gson.fromJson(result.toString(), AllegroSearchOfferDto.class);
//        System.out.println("resultDto: " + resultDto);
        return resultDto;
    }

    @Override
    public AllegroCategoriesDto getCategories(String token) throws IOException {
        AllegroCategoriesDto resultDto = null;

//        List<NameValuePair> urlParameters = new ArrayList<>();
//        urlParameters.add(new BasicNameValuePair("parent.id=0", "0"));

//        request.setP.setEntity(new UrlEncodedFormEntity(urlParameters));

//        System.out.println("token: " + token);

        String url = "https://api.allegro.pl/sale/categories";
//        String url = "https://api.allegro.pl/sale/categories?parent.id=1";
//        String url = String.format(API_GET_CATEGORIES_URL_FORMAT, API_URL);
        System.out.println("url: " + url);
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer  " + token);
        request.setHeader("Accept", "application/vnd.allegro.public.v1+json");
//        request.setHeader("content-type", "application/vnd.allegro.beta.v1+json");


        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent(), "UTF8"));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println("getCategories: " + result.toString());
        resultDto = gson.fromJson(result.toString(), AllegroCategoriesDto.class);

        return resultDto;
    }
    @Override
    public AllegroCategoriesDto getCategories(String token, String parentId) throws IOException {
        AllegroCategoriesDto resultDto = null;
        String url = "https://api.allegro.pl/sale/categories?parent.id="+ parentId;
//        System.out.println("url: " + url);
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer  " + token);
        request.setHeader("Accept", "application/vnd.allegro.public.v1+json");


        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent(), "UTF8"));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        response.getEntity().getContent().close();
//        System.out.println("url: " + result.toString());
        resultDto = gson.fromJson(result.toString(), AllegroCategoriesDto.class);

        return resultDto;
    }

    @Override
    public AllegroCategoryParamsDto getCategoryParams(String token, String categoryId) throws IOException {
        AllegroCategoryParamsDto resultDto;
        String url = "https://api.allegro.pl/sale/categories/"+categoryId+"/parameters";
//        System.out.println("url: " + url);
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer  " + token);
        request.setHeader("Accept", "application/vnd.allegro.public.v1+json");

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent(), "UTF8"));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        response.getEntity().getContent().close();
        System.out.println("getCategoryParams: " + result.toString());
        resultDto = gson.fromJson(result.toString(), AllegroCategoryParamsDto.class);

        return resultDto;
    }

    @Override
    public AllegroSaleTags getSaleOfferTags(String token, String userId) throws IOException {
        AllegroSaleTags resultDto = null;
        String url = "https://api.allegro.pl/sale/offer-tags?user.id="+userId;
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer  " + token);
        request.setHeader("Accept", "application/vnd.allegro.public.v1+json");


        HttpResponse response = client.execute(request);

//        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");

//        System.out.println("[" + statusCode + "] " + responseString);

        System.out.println("getSaleOfferTags.result : " + responseString);
        resultDto = gson.fromJson(responseString, AllegroSaleTags.class);

        return resultDto;
    }

    @Override
    public AllegroOffertShippingRatesDto getSaleShippingRates(String token, String userId) throws IOException {
        AllegroOffertShippingRatesDto resultDto = null;
        String url = "https://api.allegro.pl/sale/shipping-rates?seller.id="+userId;
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer  " + token);
        request.setHeader("Accept", "application/vnd.allegro.public.v1+json");


        HttpResponse response = client.execute(request);

//        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("[" + statusCode + "] " + responseString);

        System.out.println("getSaleShippingRates.result : " + responseString);
        resultDto = gson.fromJson(responseString, AllegroOffertShippingRatesDto.class);

        return resultDto;
    }

    @Override
    public AllegroOffertImpliedWarrantyDto getImpliedWarranty(String token, String userId) throws IOException {
        AllegroOffertImpliedWarrantyDto resultDto = null;
        String url = "https://api.allegro.pl/after-sales-service-conditions/implied-warranties?seller.id="+userId;
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer  " + token);
        request.setHeader("Accept", "application/vnd.allegro.public.v1+json");


        HttpResponse response = client.execute(request);

//        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("[" + statusCode + "] " + responseString);

        System.out.println("getImpliedWarranty.result : " + responseString);
        resultDto = gson.fromJson(responseString, AllegroOffertImpliedWarrantyDto.class);

        return resultDto;
    }

    @Override
    public AllegroOffertReturnPolicyDto getReturnPolicy(String token, String userId) throws IOException {
        AllegroOffertReturnPolicyDto resultDto = null;
        String url = "https://api.allegro.pl/after-sales-service-conditions/return-policies?seller.id="+userId;
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer  " + token);
        request.setHeader("Accept", "application/vnd.allegro.public.v1+json");


        HttpResponse response = client.execute(request);

//        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("[" + statusCode + "] " + responseString);

        System.out.println("getReturnPolicy.result : " + responseString);
        resultDto = gson.fromJson(responseString, AllegroOffertReturnPolicyDto.class);

        return resultDto;
    }

    @Override
    public List getAll(String endpointBase, Map<String, String> params) {
        return null;
    }

    @Override
    public GetSaleOfferContactsResponse getSaleOfferContacts(String token, String sellerId) throws IOException {
        GetSaleOfferContactsResponse resultDto = null;
        String url = "https://api.allegro.pl/sale/offer-contacts/?seller.id="+ sellerId;
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer  " + token);
        request.setHeader("Accept", "application/vnd.allegro.public.v1+json");

        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent(), "UTF8"));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        response.getEntity().getContent().close();

        System.out.println("getSaleOfferContacts: " + result.toString());
        resultDto = gson.fromJson(result.toString(), GetSaleOfferContactsResponse.class);
        System.out.println("resultDto: " + resultDto);

        return resultDto;
    }

    @Override
    public AllegroOfferContactDto createSaleOfferContact(String token, AllegroOfferContactDto dto) throws IOException {
        try {
            String url = String.format(API_URL_FORMAT, config.getUrl());

            HttpPost request = new HttpPost("https://api.allegro.pl/sale/offer-contacts");
            request.setHeader("Content-Type", "application/vnd.allegro.public.v1+json");
            request.setHeader("Accept", "application/vnd.allegro.public.v1+json");
            request.setHeader("Authorization", "Bearer  " + token);

            String json = gson.toJson(dto);
            StringEntity params =new StringEntity(json);
            request.setEntity(params);

            HttpResponse response = client.execute(request);
            System.out.println("createSaleOfferContact.ResponseCode : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            System.out.println("createSaleOfferContact.result : " + result.toString());

            return gson.fromJson(result.toString(), AllegroOfferContactDto.class);
        } catch (IOException e ){
            return null;
        }
    }

    @Override
    public AllegroSaleImageDto sendSaleImage(String token, String imagePath) throws IOException {

        HttpPost request = new HttpPost("https://upload.allegro.pl/sale/images");
        request.addHeader("Authorization", "Bearer  " + token);
//        request.addHeader("Accept", "application/vnd.allegro.public.v1+json");
        request.addHeader("Content-Type", "image/png"); //"image/jpeg", "image/png", albo "image/gif”' \
        request.addHeader("Accept-Language", "pl-PL");//albo en-EN) pl-PL

        File file = new File(imagePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        request.setEntity(new ByteArrayEntity(fileContent));

        HttpResponse response = client.execute(request);

//        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");

//        System.out.println("[" + statusCode + "] " + responseString);

//        System.out.println("createSaleOfferContact.result : " + result.toString());
        return gson.fromJson(responseString, AllegroSaleImageDto.class);

    }

    @Override
    public AllegroOffertResponse createOfferDraft(String token, AllegroOffertRequest dto) throws IOException {
        HttpPost request = new HttpPost("https://api.allegro.pl/sale/offers");
        request.addHeader("Authorization", "Bearer " + token);
        request.addHeader("Accept", "application/vnd.allegro.public.v1+json");
        request.addHeader("Content-Type", "application/vnd.allegro.public.v1+json; charset=utf-8");

        String json = gson.toJson(dto);
        System.out.println("json : " + json);

//        return  null;
        StringEntity params =new StringEntity(json, Charset.forName("UTF-8"));
//        StringEntity params =new StringEntity(json, ContentType.APPLICATION_JSON);
        request.setEntity(params);

        HttpResponse response = client.execute(request);
        System.out.println("response: " + response.getStatusLine().getStatusCode());


        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("[" + statusCode + "] " + responseString);

        return gson.fromJson(responseString, AllegroOffertResponse.class);
    }

    @Override
    public AllegroOffertShippingRatesDto getUserInfo(String token, String userId) throws IOException {
        AllegroOffertShippingRatesDto resultDto = null;
//        String url = "https://api.allegro.pl/sale/offer-contacts/?seller.id="+ sellerId;
//        HttpGet request = new HttpGet("https://api.allegro.pl/account/additional-emails/"+ userId );
        HttpGet request = new HttpGet("https://api.allegro.pl/sale/user-ratings?user.id="+ userId + "&recommended=false");
        request.setHeader("Authorization", "Bearer  " + token);
        request.setHeader("Accept", "application/vnd.allegro.public.v1+json");



        HttpResponse response = client.execute(request);
//        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent(), "UTF8"));

        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("getUserInfo:[" + statusCode + "] " + responseString);

//        resultDto = gson.fromJson(result.toString(), GetSaleOfferContactsResponse.class);
//        System.out.println("resultDto: " + resultDto);

        return resultDto;
    }
}
