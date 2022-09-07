package pl.estrix.app.lib.allegro.model;

import javax.persistence.criteria.CriteriaBuilder;

public class AllegroOffertStockDto {

    private Integer available;

//    -- wymagane, obecnie dostępne są trzy
//    wartości: UNIT (sztuki); SET (komplety);
//    PAIR (pary). WAŻNE! Nie uzupełniaj tego
//    pola dla oferty typu ADVERTISEMENT (ogłoszenie)
    private String unit;

    public AllegroOffertStockDto() {
    }

    public AllegroOffertStockDto(Integer available, String unit) {
        this.available = available;
        this.unit = unit;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
