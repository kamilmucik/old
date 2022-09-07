package pl.estrix.app.lib.allegro.model;

public class AllegroSearchOfferItemDto {

    private Long id;

    private String name;

    private AllegroStockDto stock;

    private AllegroCategoryDto category;

    private AllegroPublicationDto publication;


    public AllegroSearchOfferItemDto(Long id, String name, AllegroStockDto stock, AllegroCategoryDto category, AllegroPublicationDto publication) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.category = category;
        this.publication = publication;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AllegroStockDto getStock() {
        return stock;
    }

    public void setStock(AllegroStockDto stock) {
        this.stock = stock;
    }

    public AllegroCategoryDto getCategory() {
        return category;
    }

    public void setCategory(AllegroCategoryDto category) {
        this.category = category;
    }

    public AllegroPublicationDto getPublication() {
        return publication;
    }

    public void setPublication(AllegroPublicationDto publication) {
        this.publication = publication;
    }

    @Override
    public String toString() {
        return "AllegroSearchOfferItemDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", category=" + category +
                ", publication=" + publication +
                '}';
    }
}
