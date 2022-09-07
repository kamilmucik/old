package pl.estrix.app.lib.allegro.model;

public class CategoryParamDictionaryDto {

    private String id;
    private String value;

    public CategoryParamDictionaryDto() {
    }

    public CategoryParamDictionaryDto(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CategoryParamDictionaryDto{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
