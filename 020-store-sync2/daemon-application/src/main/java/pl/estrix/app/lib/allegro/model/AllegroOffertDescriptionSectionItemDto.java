package pl.estrix.app.lib.allegro.model;

public class AllegroOffertDescriptionSectionItemDto {

    private String type;
    private String content;
    private String url;

    public AllegroOffertDescriptionSectionItemDto() {
    }

    public AllegroOffertDescriptionSectionItemDto(String type, String content, String url) {
        this.type = type;
        this.content = content;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AllegroOffertDescriptionSectionItemDto{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
