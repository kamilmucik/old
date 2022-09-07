package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class AllegroOffertImpliedWarrantyDto {

    private String id;
    private String name;

    List<AllegroDictionaryDto> impliedWarranties;

    public AllegroOffertImpliedWarrantyDto() {
    }

    public AllegroOffertImpliedWarrantyDto(String id) {
        this.id = id;
    }

    public AllegroOffertImpliedWarrantyDto(String id, String name) {
        this.id = id;
        this.name = name;
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

    public List<AllegroDictionaryDto> getImpliedWarranties() {
        return impliedWarranties;
    }

    public void setImpliedWarranties(List<AllegroDictionaryDto> impliedWarranties) {
        this.impliedWarranties = impliedWarranties;
    }

    @Override
    public String toString() {
        return "AllegroOffertImpliedWarrantyDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", impliedWarranties=" + impliedWarranties +
                '}';
    }
}
