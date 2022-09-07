package pl.estrix.app.lib.allegro.model;

public class AllegroImageDto {

    private String url;

    public AllegroImageDto() {
    }

    public AllegroImageDto(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AllegroImageDto{" +
                "url='" + url + '\'' +
                '}';
    }
}
