package pl.estrix.app.lib.allegro.model;

public class AllegroOffertDto {

    private String id;
    private String name;
    private AllegroImageDto primaryImage;
    private AllegroSellngModeDto sellingMode;

    public AllegroOffertDto() {
    }

    public AllegroOffertDto(String id, String name, AllegroImageDto primaryImage, AllegroSellngModeDto sellingMode) {
        this.id = id;
        this.name = name;
        this.primaryImage = primaryImage;
        this.sellingMode = sellingMode;
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

    public AllegroImageDto getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(AllegroImageDto primaryImage) {
        this.primaryImage = primaryImage;
    }

    public AllegroSellngModeDto getSellingMode() {
        return sellingMode;
    }

    public void setSellingMode(AllegroSellngModeDto sellingMode) {
        this.sellingMode = sellingMode;
    }

    @Override
    public String toString() {
        return "AllegroOffertDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", primaryImage='" + primaryImage + '\'' +
                ", sellingMode=" + sellingMode +
                '}';
    }
}
