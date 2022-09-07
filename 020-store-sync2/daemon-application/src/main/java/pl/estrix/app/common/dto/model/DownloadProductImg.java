package pl.estrix.app.common.dto.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "url")
@XmlAccessorType(XmlAccessType.FIELD)
public class DownloadProductImg {


    @XmlValue
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DownloadProductImg{" +
                "description='" + description + '\'' +
                '}';
    }
}
