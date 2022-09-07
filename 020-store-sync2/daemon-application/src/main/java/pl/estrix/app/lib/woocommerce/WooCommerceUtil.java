package pl.estrix.app.lib.woocommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import javafx.concurrent.Task;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.app.backend.shop.service.ShopService;
import pl.estrix.app.common.dto.model.ShopCategoryDto;
import pl.estrix.app.common.dto.model.ShopDto;
import pl.estrix.app.lib.woocommerce.core.ApiVersionType;
import pl.estrix.app.lib.woocommerce.core.EndpointBaseType;
import pl.estrix.app.lib.woocommerce.core.WooCommerce;
import pl.estrix.app.lib.woocommerce.core.WooCommerceAPI;
import pl.estrix.app.lib.woocommerce.core.oauth.OAuthConfig;
import pl.estrix.app.lib.woocommerce.model.*;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class WooCommerceUtil {
    private static Logger LOG = LoggerFactory.getLogger(WooCommerceUtil.class);

    @Autowired
    private ShopService shopService;

//    private Task<Void> task;

    private OAuthConfig config;
    private WooCommerce wooCommerce;
    private WooCategoryDto rootCategory;

    private final Gson gson = new GsonBuilder().create();
    private ObjectMapper oMapper = new ObjectMapper();

    public void runTask(ShopDto shopDto){
        config = new OAuthConfig(shopDto.getApiUrl(), shopDto.getConsumerKey(), shopDto.getConsumerSecret());
        wooCommerce = new WooCommerceAPI(config, ApiVersionType.V2);

        shopService.deleteAllCategory(shopDto.getId());
        WooCategoryDto dto = getWooCategories();
        int iterator = 1;
        dto.getCategoryDtoList().clear();
        List<WooCategoryDto> list = dto.printFlatList(0);
//        System.out.println("cat: " + dto1.getName() + " : " + dto1.getId() + " : " + dto1.getParentcategory().getId());

        for (WooCategoryDto dto1 : list){
//                    System.out.println("cat: " + dto1.getName() + " : " + dto1.getId() + " : " + dto1.getParentcategory().getId());

            shopDto.setStatus(String.valueOf(calculatePercentage(iterator,list.size())));
            shopService.update(shopDto);

            shopService.createCategory(ShopCategoryDto
                    .builder()
                    .name(dto1.getName())
                    .externalId(dto1.getId())
                    .parentId(dto1.getParentcategory().getId())
                    .shopId(shopDto.getId().intValue())
                    .allegroId("")
                    .allegroLeaf(false)
                    .allegroParentId("")
                    .type("woo")
                    .build());
            iterator++;
        }


//        for (int i = 0; i < 100; i++) {
//            LOG.debug("progress: " + i);
//            shopDto.setStatus(String.valueOf(i));
//            shopService.update(shopDto);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        shopDto.setStatus("0");
        shopDto.setLastUpdated(LocalDateTime.now());
        shopService.update(shopDto);
    }

    public double calculatePercentage(double obtained, double total) {
        return obtained * 100 / total;
    }
    public int calculatePercentage(int obtained, int total) {
        return obtained * 100 / total;
    }

    public WooCategoryDto getWooCategories(){
        List<WooCategoryDto> result = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("per_page","100");
        params.put("offset","0");
        List response = wooCommerce.getAll(EndpointBaseType.PRODUCTS_CATEGORIES.getValue(), params);
//        System.out.println(response);
        for (Object record : response){
            WooCategoryDto categoryDto =new WooCategoryDto();

            String rec = record.toString();
            rec = rec.substring(1,rec.length()-2);
            String [] parts = rec.split(",");
            for (String part : parts){
                String [] entry = part.split("=");
                if ("id".equals(entry[0].trim())){
                    categoryDto.setId(Integer.parseInt(entry[1].trim()));
                }else if ("parent".equals(entry[0].trim())){
                    categoryDto.setParent(Integer.parseInt(entry[1].trim()));
                }else if ("name".equals(entry[0].trim())){
                    categoryDto.setName(entry[1].trim());
                }
            }
            result.add(categoryDto);
        }

        Collections.sort(result, new WooCategoryDtoSort());

        rootCategory = new WooCategoryDto(0,-1,"root", null, new ArrayList<>(), new ArrayList<>());
        List<WooCategoryDto> copyList = new ArrayList<>();
        //zbieram główne kategorie
        for (WooCategoryDto dto : result){
            if (dto.getParent() == 0){
                dto.setParentcategory(rootCategory);
                rootCategory.getSubcategory().add(dto);
                copyList.add(dto);
            }
        }
        result.removeAll(copyList);

        for (WooCategoryDto dto : rootCategory.getSubcategory()){
            for (WooCategoryDto dto2 : result){
                System.out.println(dto2.getName() + " : " + dto.getId()+"/"+dto2.getParent());
                if (dto2.getParent() == dto.getId()){
                    dto2.setParentcategory(dto);
                    dto.getSubcategory().add(dto2);
                    copyList.add(dto2);
                }
            }
        }
        result.removeAll(copyList);

        for (WooCategoryDto dto : result){
            WooCategoryDto resultdto = rootCategory.findInChild(dto.getParent(),1, false);
            if (resultdto != null){
                dto.setParentcategory(resultdto);
                resultdto.getSubcategory().add(dto);
                copyList.add(dto);
            }
        }
        result.removeAll(copyList);

        for (WooCategoryDto dto : result){
            WooCategoryDto resultdto = rootCategory.findInChild(dto.getParent(),1, false);
            if (resultdto != null){
                dto.setParentcategory(resultdto);
                resultdto.getSubcategory().add(dto);
                copyList.add(dto);
            }
        }
        result.removeAll(copyList);


        List<WooCategoryDto>  list = rootCategory.printFlatList(0);
        for (WooCategoryDto dto: list){
            System.out.println(dto);
        }
//        rootCategory.printTree(0);
//
//        List<Integer> ids = rootCategory.getParentIds(26, 0 , true);
        System.out.println(rootCategory);


        return rootCategory;
    }

    public WooImageDto createImage(WooImageDto img, String apiUrl, String customerKey, String customerSecret){
        config = new OAuthConfig(apiUrl, customerKey, customerSecret);
        wooCommerce = new WooCommerceAPI(config, ApiVersionType.V2);

        Map<String, Object> request = oMapper.convertValue(img, Map.class);
//        request.forEach( (k,v) -> {
//            System.out.println("req - " + k + " : " + v);
//        });
        Map response = wooCommerce.create(EndpointBaseType.MEDIA.getValue(),request);
        JSONObject json = new JSONObject(response);
//        response.forEach( (k,v) -> {
//            System.out.println("res - " + k + " : " + v);
//        });
//        System.out.println(" json " + json.toString());

        try {
            return gson.fromJson(json.toString(), WooImageDto.class);
        } catch (com.google.gson.JsonSyntaxException e ){

//            System.out.println(" JsonSyntaxException " + json.toString());
            return null;
//            return new SaveShipmentProductDto(408 /*Request Timeout*/);
        }
    }

    public WooProductDto createProduct(WooProductDto productDto, String apiUrl, String customerKey, String customerSecret){
        config = new OAuthConfig(apiUrl, customerKey, customerSecret);
        wooCommerce = new WooCommerceAPI(config, ApiVersionType.V2);
        Map<String, Object> request = oMapper.convertValue(productDto, Map.class);
//        request.forEach( (k,v) -> {
//            System.out.println("req - " + k + " : " + v);
//        });
//
//        JSONObject json2 = new JSONObject(request);
//        System.out.println(" return " + json2.toString());
        Map response = wooCommerce.create(EndpointBaseType.PRODUCTS.getValue(),request);
        JSONObject json = new JSONObject(response);

//        response.forEach( (k,v) -> {
//            System.out.println("res - " + k + " : " + v);
//        });
//        System.out.println(" json " + json.toString());
        return gson.fromJson(json.toString(), WooProductDto.class);
    }
    public WooProductDto createProduct(WooProductDto productDto){

        Map<String, Object> request = oMapper.convertValue(productDto, Map.class);
        Map response = wooCommerce.create(EndpointBaseType.PRODUCTS.getValue(),request);
        JSONObject json = new JSONObject(response);
//        response.forEach( (k,v) -> {
//            System.out.println(" - " + k + " : " + v);
//        });
//        System.out.println(" json " + json.toString());
        return gson.fromJson(json.toString(), WooProductDto.class);
    }
    public UpdateWooProductDto updateProduct(UpdateWooProductDto productDto, String apiUrl, String customerKey, String customerSecret){
        UpdateWooProductDto result = null;
        config = new OAuthConfig(apiUrl, customerKey, customerSecret);
        wooCommerce = new WooCommerceAPI(config, ApiVersionType.V2);
        Map<String, Object> request = oMapper.convertValue(productDto, Map.class);
//        request.forEach( (k,v) -> {
//            System.out.println("req: " + k + " : " + v);
//        });
        JSONObject json2 = new JSONObject(request);
//        response.forEach( (k,v) -> {
//        System.out.println(" return " + json2.toString());
        try {
            Map response = wooCommerce.update(EndpointBaseType.PRODUCTS.getValue(), productDto.getId(), request);
            JSONObject json = new JSONObject(response);
//        response.forEach( (k,v) -> {
//            System.out.println("res: " + k + " : " + v);
//        });
//        System.out.println(" return " + json.toString());
            result = gson.fromJson(json.toString(), UpdateWooProductDto.class);
            result.setCode("");
            result.setMessage("");
        }catch (Exception e){
            result = new UpdateWooProductDto();
            result.setMessage( e.getMessage());
            result.setCode("Exception");
        }
        return result;
    }
    public WooProductDto updateProduct(WooProductDto productDto){
        Map<String, Object> request = oMapper.convertValue(productDto, Map.class);
//        Map response = wooCommerce.update(EndpointBaseType.PRODUCTS.getValue(),productDto.getId(),request);
//        JSONObject json = new JSONObject(response);
//        response.forEach( (k,v) -> {
//            System.out.println(" - " + k + " : " + v);
//        });
//        System.out.println(" json " + json.toString());
//        return gson.fromJson(json.toString(), WooProductDto.class);
        return null;
    }



//    public Task<Void> getTask() {
//        return task;
//    }
}
