package pl.estrix.app.lib.allegro.model;

public class AllegroSaleTag {

    private String id;
    private String name;
    private Boolean hidden;

    public AllegroSaleTag() {
    }

    public AllegroSaleTag(String id, String name, Boolean hidden) {
        this.id = id;
        this.name = name;
        this.hidden = hidden;
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

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
