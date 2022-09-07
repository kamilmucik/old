package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class AllegroOffertShippingRatesDto {

    private String id;
    private String name;
   List<AllegroDictionaryDto> shippingRates;

    public AllegroOffertShippingRatesDto() {
    }

    public AllegroOffertShippingRatesDto(String id) {
        this.id = id;
    }

    public AllegroOffertShippingRatesDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public AllegroOffertShippingRatesDto(List<AllegroDictionaryDto> shippingRates) {
        this.shippingRates = shippingRates;
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
    public List<AllegroDictionaryDto> getShippingRates() {
        return shippingRates;
    }

    public void setShippingRates(List<AllegroDictionaryDto> shippingRates) {
        this.shippingRates = shippingRates;
    }

    @Override
    public String toString() {
        return "AllegroOffertShippingRatesDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shippingRates=" + shippingRates +
                '}';
    }
}
