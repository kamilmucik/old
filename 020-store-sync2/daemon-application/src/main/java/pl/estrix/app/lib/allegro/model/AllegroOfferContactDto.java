package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class AllegroOfferContactDto {

    private String id;

    private String name;

    private List<AllegroOfferContactEmailDto> emails;

    private List<AllegroOfferContactPhoneDto> phones;

    public AllegroOfferContactDto() {
    }

    public AllegroOfferContactDto(String id, String name, List<AllegroOfferContactEmailDto> emails, List<AllegroOfferContactPhoneDto> phones) {
        this.id = id;
        this.name = name;
        this.emails = emails;
        this.phones = phones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AllegroOfferContactEmailDto> getEmails() {
        return emails;
    }

    public void setEmails(List<AllegroOfferContactEmailDto> emails) {
        this.emails = emails;
    }

    public List<AllegroOfferContactPhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<AllegroOfferContactPhoneDto> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "AllegroOfferContactDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", emails=" + emails +
                ", phones=" + phones +
                '}';
    }
}
