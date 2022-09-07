package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class AllegroSearchOfferItemsDto {

    private List<AllegroSearchOfferItemDto> promoted;

    private List<AllegroSearchOfferItemDto> regular;

    public AllegroSearchOfferItemsDto(List<AllegroSearchOfferItemDto> promoted, List<AllegroSearchOfferItemDto> regular) {
        this.promoted = promoted;
        this.regular = regular;
    }

    public List<AllegroSearchOfferItemDto> getPromoted() {
        return promoted;
    }

    public void setPromoted(List<AllegroSearchOfferItemDto> promoted) {
        this.promoted = promoted;
    }

    public List<AllegroSearchOfferItemDto> getRegular() {
        return regular;
    }

    public void setRegular(List<AllegroSearchOfferItemDto> regular) {
        this.regular = regular;
    }

    @Override
    public String toString() {
        return "AllegroSearchOfferItemsDto{" +
                "promoted=" + promoted +
                ", regular=" + regular +
                '}';
    }
}
