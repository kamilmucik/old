package pl.estrix.app.lib.download.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
public class DownloadProducts {

    List<DownloadProduct> products;

    public List<DownloadProduct> getProducts() {
        return products;
    }

    @XmlElement(name = "product")
    public void setProducts(List<DownloadProduct> products) {
        this.products = products;
    }
}
