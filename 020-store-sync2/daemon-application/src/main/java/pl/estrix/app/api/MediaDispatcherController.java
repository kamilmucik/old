package pl.estrix.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.estrix.app.backend.stock.model.StockProductImage;
import pl.estrix.app.backend.stock.service.StockService;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;

@RestController
@RequestMapping("/media")
//@Controller
//@RequestMapping
public class MediaDispatcherController {


    @Autowired
    private StockService stockService;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping(value = "/picture/{id}", method = RequestMethod.GET)
    public void picture(HttpServletResponse response, @PathVariable Long id) {
        StockProductImage img = stockService.getImage(id);
                System.out.println("img:" + img.getStoredFile());

//        Image image = imageDao.get(id);
//        File imageFile = new File(fileUploadDirectory+"/"+image.getNewFilename());
//        response.setContentType(image.getContentType());
//        response.setContentLength(image.getSize().intValue());
//        try {
//            InputStream is = new FileInputStream(imageFile);
//            IOUtils.copy(is, response.getOutputStream());
//        } catch(IOException e) {
//            log.error("Could not show picture "+id, e);
//        }
    }

}
