package pl.estrix.app.lib.allegro.model;

public class AllegroSellngModeDto {

    private String format;

    private AllegroPriceDto price;

    public AllegroSellngModeDto() {
    }

    public AllegroSellngModeDto(String format, AllegroPriceDto price) {
        this.format = format;
        this.price = price;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public AllegroPriceDto getPrice() {
        return price;
    }

    public void setPrice(AllegroPriceDto price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AllegroSellngModeDto{" +
                "format='" + format + '\'' +
                ", price=" + price +
                '}';
    }
}
