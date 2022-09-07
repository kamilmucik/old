package pl.estrix.app.lib.allegro.model;

public class AllegroOffertSellingModeDto {

    private String format;
    private AllegroOffertSellingModePriceDto price;
    private String startingPrice;
    private String minimalPrice;

    public AllegroOffertSellingModeDto() {
    }

    public AllegroOffertSellingModeDto(String format, String price) {
        this.format = format;
        this.price = new AllegroOffertSellingModePriceDto(price, "PLN");
        this.startingPrice = null;
        this.minimalPrice = null;
    }
    public AllegroOffertSellingModeDto(String format, AllegroOffertSellingModePriceDto price, String startingPrice, String minimalPrice) {
        this.format = format;
        this.price = price;
        this.startingPrice = startingPrice;
        this.minimalPrice = minimalPrice;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public AllegroOffertSellingModePriceDto getPrice() {
        return price;
    }

    public void setPrice(AllegroOffertSellingModePriceDto price) {
        this.price = price;
    }

    public String getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getMinimalPrice() {
        return minimalPrice;
    }

    public void setMinimalPrice(String minimalPrice) {
        this.minimalPrice = minimalPrice;
    }
}
