package pl.estrix;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.estrix.app.lib.woocommerce.WooCommerceUtil;
import pl.estrix.app.lib.woocommerce.model.WooImageDto;
import pl.estrix.app.lib.woocommerce.model.WooProductDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WooCommerceTest {


    private WooCommerceUtil wooCommerceUtil;

    private static final String IMAGE = "R0lGODlhAQABAIAAAAAAAAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
    private static final String ApiUrl = "http://rappi.e-strix.com";
    private static final String ConsumerKey = "ck_e64a0065b007fd5067876af603894deca5d55f40";
    private static final String ConsumerSecret = "cs_0b70d8a6c94ed5e4cde5acbaf58e83f99c386996";

    @Before
    public void before() throws Exception {
        wooCommerceUtil = new WooCommerceUtil();
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void shouldCreateproduct(){
        List<WooImageDto> images = new ArrayList<>();
        images.add(WooImageDto
                .builder()
                .id(0)
                .src("https://www.ikonka.com.pl/img/product_media/36001-37000/KX778755363.jpg")
                .name("Logo3")
                .alt("logo3")
                .position(0)
                .build());

        WooProductDto productDto = WooProductDto
                .builder()
                .type("simple")
                .name("Prosty produkt 4")
//                .sku(stockProductDto.getCode())
                .description("opis")
                .manage_stock(true)
                .stock_quantity(321)
                .regular_price("1200.15")
                .status("publish")
                .images(images)
//                .categories(categoriesDto)
                .build();

        WooProductDto response = wooCommerceUtil.createProduct(productDto,ApiUrl,ConsumerKey,ConsumerSecret);

//        System.out.println("response: " + response.getData().getStatus() + " : " + response.getMessage());
//        assertNotEquals("rest_upload_file_error",response.getCode());
    }

    @Test
    public void shouldUploadImage(){

        WooImageDto response = wooCommerceUtil.createImage(
                WooImageDto
                        .builder()
//                        .status("future")
//                        .media_type("image")
                        .title("obrazektestowy")
//                        .media_attachment("http://e-strix.com/wp-content/uploads/2018/04/logo-244x300.png")
                        .media_attachment(IMAGE)
                        .build(),
                ApiUrl,
                ConsumerKey,
                ConsumerSecret);

//        System.out.println("response: " + response.getData().getStatus() + " : " + response.getMessage());
//        assertNotEquals("rest_upload_file_error",response.getCode());

    }
}
