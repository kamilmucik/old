package pl.estrix.app.lib.allegro.model;

public class AllegroCategoryDto {

    private String id;
    private String name;

    private AllegroCategoryDto parent;

    private Boolean leaf;

    public AllegroCategoryDto() {
    }

    public AllegroCategoryDto(String id) {
        this.id = id;
    }
    public AllegroCategoryDto(String id, String name, AllegroCategoryDto parent, Boolean leaf) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.leaf = leaf;
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

    public AllegroCategoryDto getParent() {
        return parent;
    }

    public void setParent(AllegroCategoryDto parent) {
        this.parent = parent;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    @Override
    public String toString() {
        return "AllegroCategoryDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", leaf=" + leaf +
                '}';
    }
}
