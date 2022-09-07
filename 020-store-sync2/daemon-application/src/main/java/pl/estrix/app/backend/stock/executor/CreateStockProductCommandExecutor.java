package pl.estrix.app.backend.stock.executor;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import net.coobird.thumbnailator.Thumbnails;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.backend.stock.model.StockProductAttribute;
import pl.estrix.app.backend.stock.model.StockProductImage;
import pl.estrix.app.common.dto.model.SettingDto;
import pl.estrix.app.common.dto.model.StockProductAttributeDto;
import pl.estrix.app.common.dto.model.StockProductDto;
import pl.estrix.app.common.dto.model.StockProductImageDto;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class CreateStockProductCommandExecutor extends BaseStockProductCommandExecutor {


    private static final Logger LOG = LoggerFactory.getLogger(CreateStockProductCommandExecutor.class);


    @Autowired
    private SettingService settingService;

    public StockProductDto create(StockProductDto dto) {
        StockProductDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        if (dto.getAttributes() != null) {
            for (StockProductAttributeDto attrDto : dto.getAttributes()){
                StockProductAttribute attribute = new StockProductAttribute();
                attribute.setName(attrDto.getName());
                attribute.setDescription(attrDto.getValue());
                attribute.setProductId(result.getId());
                repositoryAttribute.save(attribute);
            }
        }

        if (dto.getImages() != null) {
            for (StockProductImageDto img : dto.getImages()) {
                if (
                        img.getUrl().trim().isEmpty() ||
                        img.getUrl().contains("(") ||
                        img.getUrl().contains(")") ||
                        img.getUrl().contains(" ") ||
                        img.getUrl().trim().length() < 20

                ){
                    continue;
                }

                StockProductImage image = new StockProductImage();
                image.setProductId(result.getId());
                image.setStoredFile("stored");
                image.setUrl(img.getUrl());


                System.out.println("->" + img.getUrl());
                LOG.debug("->" + img.getUrl());


                StockProductImage stockProductImage = repositoryImage.save(image);

            }
        }

        return result;
    }

    /**
     * Resize an image
     * By Dean Williams - http://dean.resplace.net
     * @param sourceImg	The source of the image to resize.
     * @param destImg	The destination of the resized image.
     * @param Width		The maximum width you want the new image to be, use 0 for source width.
     * @param Height		The maximum height you want the new image to be, use 0 for source height.
     * @return			true if successful and false if unsuccessful.
     */
    public static Boolean resizeImage(String sourceImg, String destImg, Integer Width, Integer Height) {
        BufferedImage origImage;
        try {

            origImage = ImageIO.read(new File(sourceImg));
            int type = origImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : origImage.getType();

            //*Special* if the width or height is 0 use image src dimensions
            if (Width == 0) {
                Width = origImage.getWidth();
            }
            if (Height == 0) {
                Height = origImage.getHeight();
            }

            int fHeight = Height;
            int fWidth = Width;

            //Work out the resized width/height
            if (origImage.getHeight() > Height || origImage.getWidth() > Width) {
                fHeight = Height;
                int wid = Width;
                float sum = (float)origImage.getWidth() / (float)origImage.getHeight();
                fWidth = Math.round(fHeight * sum);

                if (fWidth > wid) {
                    //rezise again for the width this time
                    fHeight = Math.round(wid/sum);
                    fWidth = wid;
                }
            }

            BufferedImage resizedImage = new BufferedImage(fWidth, fHeight, type);
            Graphics2D g = resizedImage.createGraphics();
            g.setComposite(AlphaComposite.Src);

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.drawImage(origImage, 0, 0, fWidth, fHeight, null);
            g.dispose();

            ImageIO.write(resizedImage, "png", new File(destImg));

        } catch (IOException ex) {
//            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
