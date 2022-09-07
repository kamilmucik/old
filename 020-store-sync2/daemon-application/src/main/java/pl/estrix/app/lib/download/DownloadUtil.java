package pl.estrix.app.lib.download;

////import javafx.concurrent.Task;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.estrix.app.backend.event.service.EventService;
import pl.estrix.app.backend.send.service.SendProductService;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.backend.stock.service.StockService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.SendProductSearchCriteriaDto;
import pl.estrix.app.common.dto.model.*;
import pl.estrix.app.frontend.web.settings.allegro.AllegroController;
import pl.estrix.app.lib.download.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//
@Service
public class DownloadUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DownloadUtil.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private SendProductService sendProductService;

    @Autowired
    private EventService eventService;
    @Autowired
    private SettingService settingService;


    public void downloadProduct(StockDto stockDto)  {
        int iterator = 1;
        stockDto.setStatus("0");
        stockService.update(stockDto);
//        LOG.debug("stockDto: " + stockDto.getSiteScratch());
        try {
            if (stockDto.getSiteScratch()) {


            } else {
                Path currentRelativePath = Paths.get("");
                Path resourceRelativePath = Paths.get("");

                Optional<SettingDto> optSetting = settingService.getByCode("BASE_TEMP_DIR");
                if (optSetting.isPresent()) {
                    currentRelativePath = Paths.get(optSetting.get().getValue());
                }
                optSetting = settingService.getByCode("BASE_RESOURCE_DIR");
                if (optSetting.isPresent()) {
                    resourceRelativePath = Paths.get(optSetting.get().getValue());
                }


//                LOG.debug("currentRelativePath: " + currentRelativePath);
//                LOG.debug("currentRelativePath: " + resourceRelativePath);

                String s = currentRelativePath.toAbsolutePath().toString();

                String fileName = stockDto.getName().toLowerCase();
                downloadFile(stockDto.getApiUrl(), s + File.separator + fileName + ".xml");
//                ClassLoader classLoader = getClass().getClassLoader();
//                File file = new File(classLoader.getResource(fileName + ".xsl").getFile());
                String r = resourceRelativePath.toAbsolutePath().toString();
                File file = new File(r + File.separator + fileName + ".xsl");

//                LOG.debug("s: " + s + File.separator + fileName + ".xml");
                eventService.create(EventDto
                        .builder()
                        .name("Pobieram: " + fileName)
                        .active(true)
                        .build());

//                LOG.debug("s: " + s + File.separator + fileName + ".xml");
//                LOG.debug("s: " + file.getAbsolutePath());
//                LOG.debug("s: " + s + File.separator + fileName + "_out.xml");

                transformFile(
                        s + File.separator + fileName + ".xml",
                        file.getAbsolutePath(),
                        s + File.separator + fileName + "_out.xml");
//                LOG.debug("s: " + s + File.separator + fileName + "_out.xml");


                /**
                 * ustaw wszystkie produkty jako nieaktywne
                 */
                stockService.setDisableProductForStock(stockDto.getId());

                List<StockProductDto> productDtoList = parseFile(s + File.separator + fileName + "_out.xml");
                for(StockProductDto stockProduct: productDtoList){
                    stockDto.setStatus(String.valueOf(calculatePercentage(iterator,productDtoList.size())));
                    stockService.update(stockDto);
                    StockProductDto stockProductDto = StockProductDto
                            .builder()
                            .stockId(stockDto.getId())
                            .shopId(0L)
                            .name(stockProduct.getName())
                            .code(stockProduct.getCode())
                            .description("" + stockProduct.getDescription()
                                    .replaceAll("'","")
                                    .replaceAll("\"","'")
                            )
                            .avail(stockProduct.getAvail())
                            .price(stockProduct.getPrice())
                            .priceBrutto(stockProduct.getPriceBrutto())
                            .priceRetail(stockProduct.getPriceRetail())
                            .shippingTime(stockProduct.getShippingTime())
                            .stock(stockProduct.getStock())
                            .stockExt(stockProduct.getStockExt())
                            .extUrl(stockProduct.getExtUrl())
                            .extId(stockProduct.getExtId())
                            .build();

                    stockProductDto.setAttributes(stockProduct.getAttributes());
                    stockProductDto.setImages(stockProduct.getImages());

//                    StockProductDto product = stockService.findByStockIdAndExternalId(stockProductDto.getStockId(), stockProductDto.getExtId());
                    StockProductDto product = stockService.getProductByStockIdAndProductCode(stockProductDto.getStockId(), stockProductDto.getCode());

//                    LOG.debug("product: " + product);
                    if (product != null){

//                        stockService.updateProduct(product);
//                        //update
//                        StockProductDto productToUpdate = stockService.getProductByShopIdAndProductCode(product.getShopId(), product.getCode());
//
//                        if (productToUpdate != null){
                            boolean wasUpdated = false;
//
//                            LOG.debug("product: " + productToUpdate.getStock());
//                            LOG.debug("product: " + stockProduct.getStock());
                            if (!product.getStock().equals(stockProduct.getStock() )){
                                eventService.create(EventDto
                                        .builder()
                                        .name("Aktualizacja produktu(magazyn): " + product.getCode() + " : " + product.getStock() + " na " + stockProduct.getStock())
                                        .active(true)
                                        .build());
                                product.setStock(stockProduct.getStock());
                                wasUpdated = true;
                            }
                            if (!product.getStockExt().equals(stockProduct.getStockExt())){
                                eventService.create(EventDto
                                        .builder()
                                        .name("Aktualizacja produktu(magazyn zew): " + product.getCode() + " : " + product.getStockExt() + " na " + stockProduct.getStockExt())
                                        .active(true)
                                        .build());
                                product.setStockExt(stockProduct.getStockExt());
                                wasUpdated = true;
                            }
                            if (product.getPrice().compareTo(stockProduct.getPrice()) != 0) {
                                eventService.create(EventDto
                                        .builder()
                                        .name("Aktualizacja produktu(cena): " + product.getCode() + " : " + product.getPriceRetail() + " na " + stockProduct.getPriceRetail())
                                        .active(true)
                                        .build());
                                product.setPrice(stockProduct.getPrice());
                                product.setPriceBrutto(stockProduct.getPriceBrutto());
                                product.setPriceRetail(stockProduct.getPriceRetail());
                                wasUpdated = true;
                            }
//
//
                        product.setAvail(stockProduct.getAvail());
                        stockService.updateProduct(product);

                        if (wasUpdated) {
                            int res = sendProductService.upgradeProductInShop(false, product.getPriceRetail(), product.getStock(), product.getCode());
                            eventService.create(EventDto
                                        .builder()
                                        .name("Przekazał do aktualizacji w sklepie: " + product.getCode())
                                        .active(true)
                                        .build());
                        }

                    } else {
                        if (stockProductDto.getImages() != null && stockProductDto.getImages().size() > 0) {

                            for (StockProductImageDto img : stockProductDto.getImages()){
                                if (img.getUrl().trim().equals("")){
                                    System.out.println("\t\t:" + stockProductDto.getCode());
                                    continue;
                                }
                            }
                            eventService.create(EventDto
                                    .builder()
                                    .name("Nowy produkt: " + stockProductDto.getName())
                                    .active(true)
                                    .build());
                            stockService.createProduct(stockProductDto);
                        }
                    }
                    iterator++;
                }
//
            }

        }catch (TransformerException e){
            e.printStackTrace();
            LOG.error(e.getMessage());
        }

        stockDto.setStatus("0");
        stockDto.setLastUpdated(LocalDateTime.now());
        stockService.update(stockDto);

    }

    public void runTask(StockDto stockDto){
//        int iterator = 1;
//        stockDto.setStatus("0");
//        stockService.update(stockDto);
//        LOG.debug("stockDto: " + stockDto.getSiteScratch());
//        if (stockDto.getSiteScratch()){
//
//
//        } else {
//            try {
//                Path currentRelativePath = Paths.get("");
//                Path resourceRelativePath = Paths.get("");
//
//                Optional<SettingDto> optSetting = settingService.getByCode("BASE_TEMP_DIR");
//                if (optSetting.isPresent()) {
//                    currentRelativePath = Paths.get(optSetting.get().getValue());
//                }
//                optSetting = settingService.getByCode("BASE_RESOURCE_DIR");
//                if (optSetting.isPresent()) {
//                    resourceRelativePath = Paths.get(optSetting.get().getValue());
//                }
//
//
//                LOG.debug("currentRelativePath: " + currentRelativePath);
//                LOG.debug("currentRelativePath: " + resourceRelativePath);
//
//                String s = currentRelativePath.toAbsolutePath().toString();
//
//                String fileName = stockDto.getName().toLowerCase();
//                downloadFile(stockDto.getApiUrl(),s + File.separator + fileName + ".xml");
//                ClassLoader classLoader = getClass().getClassLoader();
////                File file = new File(classLoader.getResource(fileName + ".xsl").getFile());
//                String r = resourceRelativePath.toAbsolutePath().toString();
//                File file = new File(r + File.separator + fileName + ".xsl");
//
//                LOG.debug("s: " + s + File.separator + fileName + ".xml");
//                eventService.create(EventDto
//                        .builder()
//                        .name("Pobieram: " + fileName)
//                        .active(true)
//                        .build());
//
//                LOG.debug("s: " + s + File.separator + fileName + ".xml");
//                LOG.debug("s: " + file.getAbsolutePath());
//                LOG.debug("s: " + s + File.separator + fileName + "_out.xml");
//                transformFile(
//                        s + File.separator + fileName + ".xml",
//                        file.getAbsolutePath(),
//                        s + File.separator + fileName + "_out.xml");
//                LOG.debug("s: " + s + File.separator + fileName + "_out.xml");
//                /**
//                 * ustaw wszystkie produkty jako nieaktywne
//                 */
//                stockService.setDisableProductForStock(stockDto.getId());
//
//                List<StockProductDto> productDtoList = parseFile(s + File.separator + fileName + "_out.xml");
//                for(StockProductDto stockProduct: productDtoList){
//                    stockDto.setStatus(String.valueOf(calculatePercentage(iterator,productDtoList.size())));
//                    stockService.update(stockDto);
//                    StockProductDto stockProductDto = StockProductDto
//                            .builder()
//                            .stockId(stockDto.getId())
//                            .shopId(0L)
//                            .name(stockProduct.getName())
//                            .code(stockProduct.getCode())
//                            .description("" + stockProduct.getDescription()
//                                    .replaceAll("'","")
//                                    .replaceAll("\"","'")
//                            )
//                            .avail(stockProduct.getAvail())
//                            .price(stockProduct.getPrice())
//                            .priceBrutto(stockProduct.getPriceBrutto())
//                            .priceRetail(stockProduct.getPriceRetail())
//                            .shippingTime(stockProduct.getShippingTime())
//                            .stock(stockProduct.getStock())
//                            .stockExt(stockProduct.getStockExt())
//                            .extUrl(stockProduct.getExtUrl())
//                            .extId(stockProduct.getExtId())
//                            .build();
//
//                    stockProductDto.setAttributes(stockProduct.getAttributes());
//                    stockProductDto.setImages(stockProduct.getImages());
//
////                    StockProductDto product = stockService.findByStockIdAndExternalId(stockProductDto.getStockId(), stockProductDto.getExtId());
//                    StockProductDto product = stockService.getProductByStockIdAndProductCode(stockProductDto.getStockId(), stockProductDto.getCode());
//
//                    LOG.debug("product: " + product);
//                    if (product != null){
//                        //update
//                        StockProductDto productToUpdate = stockService.getProductByShopIdAndProductCode(product.getShopId(), product.getCode());
//
//                        if (productToUpdate != null){
//                            boolean wasUpdated = false;
//
//                            LOG.debug("product: " + productToUpdate.getStock());
//                            LOG.debug("product: " + stockProduct.getStock());
//                            if (!productToUpdate.getStock().equals(stockProduct.getStock() )){
//                                eventService.create(EventDto
//                                        .builder()
//                                        .name("Aktualizacja produktu(magazyn): " + productToUpdate.getCode() + " : " + productToUpdate.getStock() + " na " + stockProduct.getStock())
//                                        .active(true)
//                                        .build());
//                                productToUpdate.setStock(stockProduct.getStock());
//                                wasUpdated = true;
//                            }
//                            if (!productToUpdate.getStockExt().equals(stockProduct.getStockExt())){
//                                eventService.create(EventDto
//                                        .builder()
//                                        .name("Aktualizacja produktu(magazyn zew): " + productToUpdate.getCode() + " : " + productToUpdate.getStockExt() + " na " + stockProduct.getStockExt())
//                                        .active(true)
//                                        .build());
//                                productToUpdate.setStockExt(stockProduct.getStockExt());
//                                wasUpdated = true;
//                            }
//                            if (!productToUpdate.getPrice().equals(stockProduct.getPrice())){
//                                eventService.create(EventDto
//                                        .builder()
//                                        .name("Aktualizacja produktu(cena): " + productToUpdate.getCode() + " : " + productToUpdate.getPriceRetail() + " na " + stockProduct.getPriceRetail())
//                                        .active(true)
//                                        .build());
//                                productToUpdate.setPrice(stockProduct.getPrice());
//                                productToUpdate.setPriceBrutto(stockProduct.getPriceBrutto());
//                                productToUpdate.setPriceRetail(stockProduct.getPriceRetail());
//                                wasUpdated = true;
//                            }
//
//
//                            productToUpdate.setAvail(stockProduct.getAvail());
//                            stockService.updateProduct(productToUpdate);
//
//                            if (!wasUpdated) continue;
//
////                            eventService.create(EventDto
////                                    .builder()
////                                    .name("Aktualizacja produktu: " + productToUpdate.getName())
////                                    .active(true)
////                                    .build());
//                            //sendProductService
//
//                            ListResponseDto<SendProductDto> sendProducts =  sendProductService.getItems(
//                                    SendProductSearchCriteriaDto
//                                            .builder()
//                                            .productId(productToUpdate.getId())
//                                            .build(),null);
//
//                            sendProducts.getData().stream().forEach( prod -> {
//                                Integer stock = productToUpdate.getStock() + productToUpdate.getStockExt();
//                                prod.setStock(stock);
//
//                                BigDecimal price = productToUpdate.getPriceRetail();
//                                prod.setPrice(price);
//
//                                prod.setProcessed(false);
//                                sendProductService.update(prod);
//
//                                eventService.create(EventDto
//                                        .builder()
//                                        .name("Aktualizacja produktu w sklepie: " + productToUpdate.getName())
//                                        .active(true)
//                                        .build());
//                            });
//
//
//                        }
//                    } else {
//                        LOG.debug("product: " + stockProductDto.getCode() + " : " + stockProductDto.getName());
//                        System.out.println(":" + stockProductDto.getCode());
//                        if (stockProductDto.getImages() != null && stockProductDto.getImages().size() > 0) {
//
//
//                            LOG.debug("getImages: " + stockProductDto.getImages());
//                            for (StockProductImageDto img : stockProductDto.getImages()){
//                                if (img.getUrl().trim().equals("")){
//                                    System.out.println("\t\t:" + stockProductDto.getCode());
//                                    continue;
//                                }
//                            }
//                            eventService.create(EventDto
//                                    .builder()
//                                    .name("Nowy produkt: " + stockProductDto.getName())
//                                    .active(true)
//                                    .build());
////                            System.out.println(":" + stockProductDto);
//                            stockService.createProduct(stockProductDto);
//                        }
//                    }
//                    iterator++;
//                }
//
//                stockDto.setStatus("0");
//                stockDto.setLastUpdated(LocalDateTime.now());
//                stockService.update(stockDto);
//            } catch (TransformerException e) {
//                e.printStackTrace();
//                LOG.debug("error: " + e.getMessage());
//                LOG.debug("error: " + e.getMessageAndLocation());
//                LOG.debug("error: " + e.getLocalizedMessage());
//                LOG.debug("error: " + e.getException());
//                LOG.debug("error: " + e.getCause());
//                LOG.error("error: " + e.getLocationAsString());
//            }
//        }
    }


//
////    public void downloadStockProduct(StockDto selected) {
////
////        try {
////            Path currentRelativePath = Paths.get("");
////            String s = currentRelativePath.toAbsolutePath().toString();
////            String fileName = selected.getName().toLowerCase();
////            downloadUtil.downloadFile(selected.getApiUrl(),fileName + ".xml");
////            ClassLoader classLoader = getClass().getClassLoader();
////            File file = new File(classLoader.getResource(fileName + ".xsl").getFile());
////
////            downloadUtil.transformFile(
////                    s + File.separator + fileName + ".xml",
////                    file.getAbsolutePath(),
////                    s + File.separator + fileName + "_out.xml");
////
////            List<StockProductDto> productDtoList = downloadUtil.parseFile(s + File.separator + fileName + "_out.xml");
////            for(StockProductDto stockProduct: productDtoList){
////
////                StockProductDto stockProductDto = StockProductDto
////                        .builder()
////                        .stockId(selected.getId())
////                        .shopId(0L)
////                        .name(stockProduct.getName())
////                        .code(stockProduct.getCode())
////                        .description(stockProduct.getDescription())
////                        .avail(stockProduct.getAvail())
////                        .price(stockProduct.getPrice())
////                        .priceBrutto(stockProduct.getPriceBrutto())
////                        .priceRetail(stockProduct.getPriceRetail())
////                        .shippingTime(stockProduct.getShippingTime())
////                        .stock(stockProduct.getStock())
////                        .stockExt(stockProduct.getStockExt())
////                        .extUrl(stockProduct.getExtUrl())
////                        .extId(stockProduct.getExtId())
////                        .build();
////
////                stockProductDto.setAttributes(stockProduct.getAttributes());
////                stockProductDto.setImages(stockProduct.getImages());
////
////                StockProductDto product = readProductExecutor.findByStockIdAndExternalId(stockProductDto.getStockId(), stockProductDto.getExtId());
//////                StockProductDto product = readProductExecutor.findByStockIdAndProductCode(stockProductDto.getStockId(), stockProductDto.getCode());
////
////                if (product != null){
////                    //update
////                } else {
////                    createProductExecutor.create(stockProductDto);
////                }
////            }
////            selected.setLastUpdated(LocalDateTime.now());
////            selected.setProductCounter(productDtoList.size());
////            updateExecutor.update(selected);
////
////        } catch (TransformerException e) {
////            e.printStackTrace();
////        }
////    }
//
    public double calculatePercentage(double obtained, double total) {
        return obtained * 100 / total;
    }
    public int calculatePercentage(int obtained, int total) {
        return obtained * 100 / total;
    }

    public Boolean downloadFile(String url, String fileName) {
        try {
            Files.deleteIfExists(Paths.get(fileName));

            URL url2 = new URL(url);
            URLConnection conn = url2.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            conn.connect();
            InputStream in3 = conn.getInputStream();
            Files.copy(in3, Paths.get(fileName));
        } catch (IOException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return false;

        }
        return true;

//        if(new NetFile(new File(fileName), url, -1).load()) {
//            return true;
//        } else {
//            return false;
//        }
    }

    public void transformFile(String inXML,String inXSL,String outTXT)throws TransformerConfigurationException,
            TransformerException {

        LOG.debug("s.1: " );
        TransformerFactory factory = TransformerFactory.newInstance();
        LOG.debug("s.2: " + factory );
        StreamSource xslStream = new StreamSource(inXSL);
        LOG.debug("s.3: " + xslStream);
        Transformer transformer = factory.newTransformer(xslStream);
        LOG.debug("s.4: " + transformer);
        transformer.setErrorListener(new TransformErrorListener());
        LOG.debug("s.5: " );
        StreamSource in = new StreamSource(inXML);
        LOG.debug("s.6: " + in);
        StreamResult out = new StreamResult(outTXT);
        LOG.debug("s.7: " + out );
        transformer.transform(in,out);
        LOG.debug("s.8: " );
    }

    private Integer parse(String stock){
//        LOG.debug("parse: " + stock);
        try {
            if (stock.contains(".")) {
                return new BigDecimal(stock).intValue();
            } else {
                return Integer.parseInt(stock);
            }

        }catch (Exception e){
            return 0;
        }
    }

    public List<StockProductDto> parseFile(String fileName){
        List<StockProductDto> result = new ArrayList<>();
        JAXBContext jaxbContext = null;

        try{
            jaxbContext = JAXBContext.newInstance(DownloadProducts.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            DownloadProducts products = (DownloadProducts) unmarshaller.unmarshal(new File( fileName));
            for (DownloadProduct prod : products.getProducts()){

                StockProductDto stockProductDto = StockProductDto
                        .builder()
                        .name(prod.getName())
                        .code(prod.getCode())
                        .description(("" + prod.getDesc())
                                .replaceAll("'","")
                                .replaceAll("\"","'")
                        )
                        .price(prod.getPrice())
                        .priceBrutto(prod.getPrice_brutto())
                        .priceRetail(prod.getPrice_retail())
                        .extId(prod.getExt_id())
                        .extUrl(prod.getExt_url())
                        .avail(prod.getAvail())
                        .stock(parse(prod.getStock()))
                        .stockExt(parse(prod.getStock_ext()))
                        .shippingTime(prod.getShipping_time())
                        .build();

                if (prod.getImages() != null && prod.getImages().size()>0) {
                    for (DownloadProductImg img : prod.getImages()) {
                        stockProductDto.addImage(StockProductImageDto
                                .builder()
                                .url(img.getDescription())
                                .build());
                    }
                }

                if (prod.getAttributes() != null && prod.getAttributes().size()>0) {
                    for (DownloadProductAttr attr : prod.getAttributes()) {
                        stockProductDto.addAttributes(StockProductAttributeDto
                                .builder()
                                .name(attr.getName())
                                .value(attr.getDescription())
                                .build());
                    }
                }

                result.add(stockProductDto);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }finally {
//            dstFile.delete();
        }
        return result;
    }
//
//    public Task<Void> getTask() {
//        return task;
//    }
}
