package pl.estrix.app.lib.allegro.model;

public class AllegroSearchOfferDto {

    private AllegroSearchOfferItemsDto items;

//    private String categories;
//    private String filters;
//    private String searchMeta;
//    private String sort;


    public AllegroSearchOfferDto(AllegroSearchOfferItemsDto items) {
        this.items = items;
    }

    public AllegroSearchOfferItemsDto getItems() {
        return items;
    }

    public void setItems(AllegroSearchOfferItemsDto items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "AllegroSearchOfferDto{" +
                "items=" + items +
                '}';
    }
}
