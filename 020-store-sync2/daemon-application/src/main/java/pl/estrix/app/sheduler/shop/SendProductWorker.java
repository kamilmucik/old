package pl.estrix.app.sheduler.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.send.service.SendProductService;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.backend.shop.service.ShopService;
import pl.estrix.app.backend.stock.service.StockService;
import pl.estrix.app.common.dto.model.*;
import pl.estrix.app.lib.woocommerce.WooCommerceUtil;
import pl.estrix.app.lib.woocommerce.model.UpdateWooProductDto;
import pl.estrix.app.lib.woocommerce.model.WooCategoryDto;
import pl.estrix.app.lib.woocommerce.model.WooImageDto;
import pl.estrix.app.lib.woocommerce.model.WooProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SendProductWorker {

    private static Logger LOG = LoggerFactory.getLogger(SendProductWorker.class);

    @Autowired
    private SendProductService sendProductService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private StockService stockService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private WooCommerceUtil wooCommerceUtil;

    @Scheduled(cron="0 * * * * *")
    public void runTask() {
        Optional<SettingDto> optSetting = settingService.getByCode("SCHEDULER_SEND_PRODUCT_SHOP");
        if (optSetting.isPresent()) {
            if (optSetting.get().getValue().equals("1")) {
                SendProductDto sendProductDto = sendProductService.getLastProductToSend();
                if (sendProductDto != null) {
                    StockProductDto stockProductDto = stockService.getProduct(sendProductDto.getProductId());
                    ShopDto shopDto = shopService.get(sendProductDto.getShopId());
                    if(sendProductDto.getShopProductId() != null) {
                        List<WooImageDto> images = new ArrayList<>();
                        Integer i = 0;
                        if (stockProductDto.getImages() != null) {
                            for (StockProductImageDto imgDto : stockProductDto.getImages()) {
                                images.add(WooImageDto
                                        .builder()
                                        .id(0)
                                        .src(imgDto.getUrl())
                                        .name("")
                                        .alt("")
                                        .position(i)
                                        .build());
                                i++;
                            }
                        }

                        List<WooCategoryDto> categoriesDto = new ArrayList<>();
                        for (String cattId : sendProductDto.getShopCategories().split(",")){
                            categoriesDto.add(WooCategoryDto
                                    .builder()
                                    .id(Integer.parseInt(cattId))
                                    .name("")
                                    .build());
                        }

                        UpdateWooProductDto productDto = UpdateWooProductDto
                                .builder()
                                .stock_quantity(sendProductDto.getStock())
                                .regular_price(sendProductDto.getPrice().toString())
                                .categories(categoriesDto)
                                .build();
                        productDto.setId(sendProductDto.getShopProductId().intValue());
                        productDto = wooCommerceUtil.updateProduct(productDto,shopDto.getApiUrl(),shopDto.getConsumerKey(),shopDto.getConsumerSecret());

                        sendProductDto.setMessage(productDto.getMessage());
                        if  (productDto.getCode().isEmpty()) {
                            sendProductDto.setProcessed(true);
                        }else {
                            sendProductDto.setCode(productDto.getCode());
                        }
                        sendProductService.update(sendProductDto);

//                        sendProductService.upgradeProductInShop(sendProductDto);



                    } else {
                        sendProductDto.setPermalink("");
                        sendProductDto.setProcessed(true);
                        sendProductService.update(sendProductDto);

                        String shipTime = "<p>* Czas realizacji do: " + stockProductDto.getShippingTime() + " dni.</p>";

                        List<WooImageDto> images = new ArrayList<>();
                        Integer i = 0;
                        if (stockProductDto.getImages() != null) {
                            for (StockProductImageDto imgDto : stockProductDto.getImages()) {
                                if (i >=1) break;

                                images.add(WooImageDto
                                        .builder()
                                        .id(0)
                                        .src(imgDto.getUrl())
                                        .name("Logo"+i)
                                        .alt("logo"+i)
                                        .position(i)
                                        .build());
                                i++;
                            }
                        }

                        List<WooCategoryDto> categoriesDto = new ArrayList<>();
                        for (String cattId : sendProductDto.getShopCategories().split(",")){
                            categoriesDto.add(WooCategoryDto
                                    .builder()
                                    .id(Integer.parseInt(cattId))
                                    .name("")
                                    .build());
                        }

                        WooProductDto productDto = WooProductDto
                                .builder()
                                .type("simple")
                                .name(stockProductDto.getName())
                                .sku(stockProductDto.getCode())
                                .description(stockProductDto.getDescription() + shipTime)
                                .manage_stock(true)
                                .stock_quantity(sendProductDto.getStock())
                                .regular_price(sendProductDto.getPrice().toString())
                                .status("publish")
                                .images(images)
                                .categories(categoriesDto)
                                .build();

                        productDto = wooCommerceUtil.createProduct(productDto,shopDto.getApiUrl(),shopDto.getConsumerKey(),shopDto.getConsumerSecret());

                        if (productDto.getCode() == null) {
                            sendProductDto.setShopProductId(productDto.getId().longValue());
                            sendProductDto.setPermalink(productDto.getPermalink());

                            for(WooImageDto image : productDto.getImages()){
//                        System.out.println("image: " + image.getSrc());
                                sendProductDto.addImages(SendProductImageDto
                                        .builder()
                                        .position(image.getPosition().longValue())
                                        .src(image.getSrc())
                                        .build());
                            }


                        } else {
                            sendProductDto.setCode(productDto.getCode());
                            sendProductDto.setMessage(productDto.getMessage());
                            sendProductDto.setPermalink("");
                        }

                        sendProductDto.setProcessed(true);
                        sendProductService.update(sendProductDto);
                    }
                }
            }
        }
    }

}
