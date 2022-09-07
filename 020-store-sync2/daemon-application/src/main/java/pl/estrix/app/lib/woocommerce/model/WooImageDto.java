package pl.estrix.app.lib.woocommerce.model;

import lombok.*;

import java.io.Serializable;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WooImageDto extends WooBaseDto {

    private Integer id;
    private String title;
    private String media_attachment;
    private String media_type;
    private String source_url;
    private String date_created;
    private String date_created_gmt;
    private String date_modified;
    private String date_modified_gmt;
    private String src;
    private String name;
    private String base64;
    private String alt;
    private String status;
    private Integer position;

    @Builder
    public WooImageDto(String code, String message, String title,String status,String source_url, String media_attachment,String media_type, WooBaseDataDto data, Integer id, String date_created, String date_created_gmt, String date_modified, String date_modified_gmt, String src, String name, String base64, String alt, Integer position) {
        super(code, message, data);
        this.id = id;
        this.title = title;
        this.status = status;
        this.source_url = source_url;
        this.media_type = media_type;
        this.media_attachment = media_attachment;
        this.date_created = date_created;
        this.date_created_gmt = date_created_gmt;
        this.date_modified = date_modified;
        this.date_modified_gmt = date_modified_gmt;
        this.src = src;
        this.name = name;
        this.base64 = base64;
        this.alt = alt;
        this.position = position;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public WooBaseDataDto getData() {
        return data;
    }

    @Override
    public String toString() {
        return "WooImageDto{" +
                "id=" + getId() +
                ", src='" + src + '\'' +
                '}';
    }
}
