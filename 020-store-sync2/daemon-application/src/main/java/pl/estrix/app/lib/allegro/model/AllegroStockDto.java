package pl.estrix.app.lib.allegro.model;

public class AllegroStockDto {

    private String unit;

    private Integer available;

    public AllegroStockDto(String unit, Integer available) {
        this.unit = unit;
        this.available = available;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "AllegroStockDto{" +
                "unit='" + unit + '\'' +
                ", available=" + available +
                '}';
    }
}
