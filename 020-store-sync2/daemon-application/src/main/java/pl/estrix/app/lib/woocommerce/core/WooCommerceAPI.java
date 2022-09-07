package pl.estrix.app.lib.woocommerce.core;


import pl.estrix.app.lib.woocommerce.core.oauth.OAuthConfig;
import pl.estrix.app.lib.woocommerce.core.oauth.OAuthSignature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WooCommerceAPI implements WooCommerce {

    private static final String API_URL_FORMAT = "%s/wp-json/wc/%s/%s";
    private static final String API_URL_FORMAT_ACTION = "%s/wc-api/%s/%s/%s";
    private static final String API_URL_ONE_ENTITY_FORMAT = "%s/wp-json/wc/%s/%s/%d";
    private static final String URL_SECURED_FORMAT = "%s?%s";
    private static final String URL_SECURED_FORMAT_ACTION = "%s?%s -u %s:%s";

    private HttpClient client;
    private OAuthConfig config;
    private String apiVersion;

    public WooCommerceAPI(OAuthConfig config, ApiVersionType apiVersion) {
        this.config = config;
        this.client = new DefaultHttpClient();
        this.apiVersion = apiVersion.getValue();
    }

    @Override
    public Map create(String endpointBase, Map<String, Object> object) {
        String url = String.format(API_URL_FORMAT, config.getUrl(), apiVersion, endpointBase);
        return client.post(url, OAuthSignature.getAsMap(config, url, HttpMethod.POST), object);
    }

    @Override
    public Map get(String endpointBase, int id) {
        String url = String.format(API_URL_ONE_ENTITY_FORMAT, config.getUrl(), apiVersion, endpointBase, id);
        String signature = OAuthSignature.getAsQueryString(config, url, HttpMethod.GET);
        String securedUrl = String.format(URL_SECURED_FORMAT, url, signature);
        return client.get(securedUrl);
    }

    @Override
    public List getAll(String endpointBase, Map<String, String> params) {
        String url = String.format(API_URL_FORMAT, config.getUrl(), apiVersion, endpointBase);
        String signature = OAuthSignature.getAsQueryString(config, url, HttpMethod.GET, params);
        String securedUrl = String.format(URL_SECURED_FORMAT, url, signature);
        System.out.println("securedUrl: " + securedUrl);
        return client.getAll(securedUrl);
    }

    @Override
    public Map update(String endpointBase, int id, Map<String, Object> object) {
        String url = String.format(API_URL_ONE_ENTITY_FORMAT, config.getUrl(), apiVersion, endpointBase, id);
        return client.put(url, OAuthSignature.getAsMap(config, url, HttpMethod.PUT), object);
    }

    @Override
    public Map delete(String endpointBase, int id) {
        String url = String.format(API_URL_ONE_ENTITY_FORMAT, config.getUrl(), apiVersion, endpointBase, id);
        Map<String, String> params = OAuthSignature.getAsMap(config, url, HttpMethod.DELETE);
        return client.delete(url, params);
    }

    @Override
    public Integer count(String endpointBase) {
        Map<String, String> params = new HashMap<>();
        String url = String.format(API_URL_FORMAT_ACTION, config.getUrl(), apiVersion, endpointBase, "count");
//        System.out.println("url: " + url);
        String signature = OAuthSignature.getAsQueryString(config, url, HttpMethod.GET, params);
//        System.out.println("signature: " + signature);
        String securedUrl = String.format(URL_SECURED_FORMAT_ACTION, url, signature, config.getConsumerKey(), config.getConsumerSecret());
//        System.out.println("securedUrl: " + securedUrl);
//        Map response = client.get(securedUrl);
//        System.out.println("response: " + response);
        return 0;
    }
}
