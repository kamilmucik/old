package pl.estrix.app.lib.download.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "attributes")
public class DownloadProductAttrs implements Serializable {

    private List<DownloadProductAttr> attr = new ArrayList<>();

    public List<DownloadProductAttr> getAttr() {
        return attr;
    }

//    @XmlElementWrapper(name="attr")
    @XmlElement(name="attr")
    public void setAttr(List<DownloadProductAttr> attr) {
        this.attr = attr;
    }

    @Override
    public String toString() {
        return "DownloadProductAttrs{" +
                "attr=" + attr +
                ", attr=" + attr.size() +
                '}';
    }
}
