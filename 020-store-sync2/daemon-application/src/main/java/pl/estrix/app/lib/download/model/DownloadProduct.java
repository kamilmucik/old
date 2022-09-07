package pl.estrix.app.lib.download.model;

import pl.estrix.app.common.dto.model.DownloadProductImg;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "product")
public class DownloadProduct {

    private String name;
    private String code;
    private String desc;

    private BigDecimal price;
    private BigDecimal price_brutto;
    private BigDecimal price_retail;

    private String stock;
    private String stock_ext;
    private Boolean avail;

    private String ext_id;
    private String ext_url;
    private String shipping_time;

    private List<DownloadProductAttr> attributes = new ArrayList<>();

    private List<DownloadProductImg> images = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice_brutto() {
        return price_brutto;
    }

    public void setPrice_brutto(BigDecimal price_brutto) {
        this.price_brutto = price_brutto;
    }

    public BigDecimal getPrice_retail() {
        return price_retail;
    }

    public void setPrice_retail(BigDecimal price_retail) {
        this.price_retail = price_retail;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStock_ext() {
        return stock_ext;
    }

    public void setStock_ext(String stock_ext) {
        this.stock_ext = stock_ext;
    }

    public Boolean getAvail() {
        return avail;
    }

    public void setAvail(Boolean avail) {
        this.avail = avail;
    }

    public String getExt_id() {
        return ext_id;
    }

    public void setExt_id(String ext_id) {
        this.ext_id = ext_id;
    }

    public String getExt_url() {
        return ext_url;
    }

    public void setExt_url(String ext_url) {
        this.ext_url = ext_url;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }

    public List<DownloadProductImg> getImages() {
        return images;
    }

//    @XmlElement(name = "images")
    @XmlElementWrapper(name="images")
    @XmlElement(name="url")
    public void setImages(List<DownloadProductImg> images) {
        this.images = images;
    }

    public List<DownloadProductAttr> getAttributes() {
        return attributes;
    }

    @XmlElementWrapper(name="attributes")
    @XmlElement(name="attr")
    public void setAttributes(List<DownloadProductAttr> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "DownloadProduct{" +
                "\nname='" + name + '\'' +
                ", \ncode='" + code + '\'' +
//                ", \ndesc='" + desc + '\'' +
                ", \nprice=" + price +
                ", \nprice_brutto=" + price_brutto +
                ", \nprice_retail=" + price_retail +
                ", \nstock=" + stock +
                ", \nstock_ext=" + stock_ext +
                ", \navail=" + avail +
                ", \next_id='" + ext_id + '\'' +
                ", \next_url='" + ext_url + '\'' +
                ", \nshipping_time='" + shipping_time + '\'' +
//                ", \nattributes=" + attributes +
//                ", \nimages=" + images +
                '}';
    }
}
