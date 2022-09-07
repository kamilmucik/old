package pl.estrix.app.lib.download.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "attr")
@XmlAccessorType(XmlAccessType.FIELD)
public class DownloadProductAttr implements Serializable{


    @XmlAttribute(name = "name")
    private String name;
    @XmlValue
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DownloadProductAttr{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
