package pl.estrix.app.frontend.web;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.app.FacesViewScope;
import pl.estrix.app.backend.stock.model.StockProductImage;
import pl.estrix.app.backend.stock.service.StockService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Getter
@Setter
@Scope(FacesViewScope.NAME)
@Component
@ManagedBean(name = "imageController")
public class ImageController extends MainController{

    private StreamedContent image;
    private String mainThumb;

    @Autowired
    private StockService stockService;

    @PostConstruct
    public void init() {
        System.out.println("-" + getRequest().getParameter("image"));
        if (getRequest().getParameter("image")!= null) {
            try {
                StockProductImage img = stockService.getImage(Long.parseLong(getRequest().getParameter("image")));
                mainThumb = img.getBase64Content();
//                System.out.println("img:" + img.getStoredFile());
//                File file = new File("/path/to/your/image.png");
//                InputStream input = new FileInputStream(file);
//                InputStream input = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(img.getBase64Content()));
//                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//                setImage(new DefaultStreamedContent(input, externalContext.getMimeType("imagename"), "imagename"));

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
