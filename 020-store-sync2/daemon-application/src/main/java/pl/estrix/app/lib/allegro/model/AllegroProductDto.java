package pl.estrix.app.lib.allegro.model;

public class AllegroProductDto {

    private String name;

    public AllegroProductDto() {
    }
    public AllegroProductDto(String name) {
        this.name = name;
    }

    public String getLatestEvent() {
        return name;
    }

    public void setLatestEvent(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AllegroProductDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
