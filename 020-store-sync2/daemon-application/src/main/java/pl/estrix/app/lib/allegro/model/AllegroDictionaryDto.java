package pl.estrix.app.lib.allegro.model;

public class AllegroDictionaryDto {

    private String id;
    private String name;

    public AllegroDictionaryDto() {
    }

    public AllegroDictionaryDto(String id) {
        this.id = id;
    }

    public AllegroDictionaryDto(String id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "AllegroDictionaryDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
