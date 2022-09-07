package pl.estrix.app.lib.allegro.model;

public class AllegroOffertDeliveryDto {

    private AllegroOffertShippingRatesDto shippingRates;

    private String handlingTime;
    private String additionalInfo;
    private String shipmentDate;

    public AllegroOffertDeliveryDto() {
    }

    public AllegroOffertDeliveryDto(String shippingRates, String handlingTime, String additionalInfo) {
        this.shippingRates = new AllegroOffertShippingRatesDto(shippingRates);
        this.handlingTime = handlingTime;
        this.additionalInfo = additionalInfo;
        this.shipmentDate = null;
    }
    public AllegroOffertDeliveryDto(AllegroOffertShippingRatesDto shippingRates, String handlingTime, String additionalInfo, String shipmentDate) {
        this.shippingRates = shippingRates;
        this.handlingTime = handlingTime;
        this.additionalInfo = additionalInfo;
        this.shipmentDate = shipmentDate;
    }

    public AllegroOffertShippingRatesDto getShippingRates() {
        return shippingRates;
    }

    public void setShippingRates(AllegroOffertShippingRatesDto shippingRates) {
        this.shippingRates = shippingRates;
    }

    public String getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(String handlingTime) {
        this.handlingTime = handlingTime;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }
}
