package pl.estrix.app.backend.stock.service.impl;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.backend.stock.dao.StockProductImageRepository;
import pl.estrix.app.backend.stock.dao.StockProductRepository;
import pl.estrix.app.backend.stock.executor.*;
import pl.estrix.app.backend.stock.model.StockProduct;
import pl.estrix.app.backend.stock.model.StockProductImage;
import pl.estrix.app.backend.stock.service.StockService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.StockProductSearchCriteriaDto;
import pl.estrix.app.common.dto.StockSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SettingDto;
import pl.estrix.app.common.dto.model.StockDto;
import pl.estrix.app.common.dto.model.StockProductDto;
//import pl.estrix.app.lib.download.DownloadUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Service
@Primary
public class StockServiceImpl implements StockService {


    private static final Logger LOG = LoggerFactory.getLogger(StockService.class);


    @Autowired
    private SettingService settingService;

    @Autowired
    private ReadStockCommandExecutor readExecutor;
    @Autowired
    private CreateStockCommandExecutor createExecutor;
    @Autowired
    private DeleteStockCommandExecutor deleteExecutor;
    @Autowired
    private UpdateStockCommandExecutor updateExecutor;
    @Autowired
    private ReadStockProductCommandExecutor readProductExecutor;
    @Autowired
    private CreateStockProductCommandExecutor createProductExecutor;
    @Autowired
    private UpdateStockProductCommandExecutor updateProductExecutor;
    @Autowired
    protected StockProductImageRepository repositoryImage;
    @Autowired
    protected StockProductRepository stockProductRepository;
//
//    @Autowired
//    private DownloadUtil downloadUtil;

    @Override
    public StockDto get(Long id) {
        return readExecutor.findById(id);
    }

    @Override
    public StockDto create(StockDto dto) {
        return createExecutor.create(dto);
    }

    @Override
    public StockDto update(StockDto dto) {
        return updateExecutor.update(dto);
    }

    @Override
    public void delete(Long id) {
        deleteExecutor.delete(id);
    }

    @Override
    public void setDisableProductForStock(Long id) {
        updateProductExecutor.disableProductForStock(id);
    }

    @Override
    public ListResponseDto<StockDto> getItems(StockSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        ListResponseDto<StockDto> listResponseDto = readExecutor.find(searchCriteria,pagingCriteria);

        return listResponseDto;
    }

    @Override
    public StockProductDto getProduct(Long id) {
        return readProductExecutor.findById(id);
    }

    @Override
    public StockProductImage getImage(Long id) {
        return repositoryImage.findOne(id);
    }

    @Override
    public StockProductDto createProduct(StockProductDto dto) {
        return createProductExecutor.create(dto);
    }
    @Override
    public StockProductDto updateProduct(StockProductDto dto) {
        return updateProductExecutor.update(dto);
    }

    @Override
    public ListResponseDto<StockProductDto> getProductItems(StockProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        ListResponseDto<StockProductDto> listResponseDto = readProductExecutor.find(searchCriteria,pagingCriteria);

        return listResponseDto;
    }

    @Override
    public void downloadStockProduct(StockDto selected) {

    }

    @Override
    public void downloadStockProductImage() {
        List<StockProductImage> images = repositoryImage.findTop1ByStoredFileOrderByProductIdDesc("stored");
        LOG.debug("\t-> " + images);
        boolean savedImage = false;
        for (StockProductImage image : images){
            LOG.debug("\t\t-> " + image.toString());

            Image image2 = null;
            try {
                URL url = new URL(image.getUrl());
                URLConnection conn = url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");

                conn.connect();
                InputStream in3 = conn.getInputStream();

                    Path currentRelativePath = Paths.get("");
                    Optional<SettingDto> optSetting = settingService.getByCode("BASE_TEMP_DIR");
                    if (optSetting.isPresent()) {
                        currentRelativePath = Paths.get(optSetting.get().getValue());
                    }

                    String extension = image.getUrl().substring(image.getUrl().lastIndexOf("."));
                    Path path = Paths.get(currentRelativePath + File.separator + image.getId() + extension);

                Files.copy(in3, path );



//                    System.out.println("Zapisuje: " + path.toAbsolutePath().toString());
                    LOG.debug("Zapisuje: " + path.toAbsolutePath().toString());
                    image.setStoredFile(path.toAbsolutePath().toString());
                    repositoryImage.save(image);

                    BufferedImage originalBufferedImage = ImageIO.read(path.toFile());
                    int thumbnailWidth = 120;
                    int widthToScale, heightToScale;
                    if (originalBufferedImage.getWidth() > originalBufferedImage.getHeight()) {

                        heightToScale = (int)(1.1 * thumbnailWidth);
                        widthToScale = (int)((heightToScale * 1.0) / originalBufferedImage.getHeight()
                                * originalBufferedImage.getWidth());

                    } else {
                        widthToScale = (int)(1.1 * thumbnailWidth);
                        heightToScale = (int)((widthToScale * 1.0) / originalBufferedImage.getWidth()
                                * originalBufferedImage.getHeight());
                    }

                    BufferedImage bufferedIconImage =
                            Thumbnails.of(currentRelativePath + File.separator + image.getId() + extension)
                                    .size(widthToScale, heightToScale)
                                    .allowOverwrite(true)
                                    .outputFormat("png")
                                    .asBufferedImage();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedIconImage, "PNG", baos);

                    byte[] encodeBase64 = Base64.encodeBase64(baos.toByteArray());

                    String base64Encoded = new String(encodeBase64);
                    base64Encoded = "data:image/png;base64,"+base64Encoded;
                    baos.close();

                    image.setBase64Content(base64Encoded);

                    if(image.getProductId() != null && !savedImage){

                        LOG.debug("\t\t ZAPISUJE MAIN THUMB-=> " + image.getProductId());
                        StockProduct stockProduct = stockProductRepository.getOne(image.getProductId());
                        stockProduct.setMainThumb(image.getBase64Content());
                        stockProductRepository.save(stockProduct);
                        savedImage= true;
                        LOG.debug("\t\t ZAPISAL MAIN THUMB-=> " + image.getProductId() + " : " + savedImage);
                    }

                    Files.delete(path);
//                    path
                } catch (IOException e) {
                    LOG.error("" + image.getUrl());
                    LOG.error(e.getMessage());
                    LOG.error(e.getLocalizedMessage());
                    LOG.error("" + e.getCause());
                    e.printStackTrace();

                image.setStoredFile("error");
                }

            repositoryImage.save(image);
        }
//        StockProductImage image = repositoryImage.findTop3ByStoredFileOrderByProductIdDesc("stored");
//        LOG.debug("\t-> " + image.getUrl());
    }

    @Override
    public StockProductDto findByStockIdAndExternalId(Long shopId, String externalId) {
        return readProductExecutor.findByStockIdAndExternalId(shopId, externalId);
    }

    @Override
    public StockProductDto getProductByShopIdAndProductCode(Long shopId, String code) {
        return readProductExecutor.findByShopIdAndProductCode(shopId,code);
    }
    @Override
    public StockProductDto getProductByStockIdAndProductCode(Long stockId, String code) {
        return readProductExecutor.findByStockIdAndProductCode(stockId,code);
    }
}
