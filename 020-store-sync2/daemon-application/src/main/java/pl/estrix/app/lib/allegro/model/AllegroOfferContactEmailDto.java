package pl.estrix.app.lib.allegro.model;

public class AllegroOfferContactEmailDto {

    private String address;

    public AllegroOfferContactEmailDto() {
    }

    public AllegroOfferContactEmailDto(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
