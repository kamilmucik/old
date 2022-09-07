package pl.estrix.app.lib.allegro.model;

public class AllegroOfferContactPhoneDto {

    private String number;

    public AllegroOfferContactPhoneDto() {
    }

    public AllegroOfferContactPhoneDto(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
