package pl.estrix.app.lib.allegro.model;

public class AllegroSaleImageDto {

    private String expiresAt;

    private String location;

    public AllegroSaleImageDto() {
    }

    public AllegroSaleImageDto(String expiresAt, String location) {
        this.expiresAt = expiresAt;
        this.location = location;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
