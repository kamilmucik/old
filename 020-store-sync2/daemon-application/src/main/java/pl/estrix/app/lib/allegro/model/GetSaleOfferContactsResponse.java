package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class GetSaleOfferContactsResponse {

    private List<AllegroOfferContactDto> contacts;

    public GetSaleOfferContactsResponse() {
    }

    public GetSaleOfferContactsResponse(List<AllegroOfferContactDto> contacts) {
        this.contacts = contacts;
    }

    public List<AllegroOfferContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<AllegroOfferContactDto> contacts) {
        this.contacts = contacts;
    }
}
