package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class CategoryParamDto {

    private String id;
    private String name;
    private String type;
    private Boolean required;
    private String unit;
    private CategoryParamOptionsDto options;
    private List<CategoryParamDictionaryDto> dictionary;
    private CategoryParamRestrictionsDto restrictions;

    public CategoryParamDto() {
    }

    public CategoryParamDto(String id, String name, String type, Boolean required, String unit, CategoryParamOptionsDto options, List<CategoryParamDictionaryDto> dictionary, CategoryParamRestrictionsDto restrictions) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.required = required;
        this.unit = unit;
        this.options = options;
        this.dictionary = dictionary;
        this.restrictions = restrictions;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public CategoryParamOptionsDto getOptions() {
        return options;
    }

    public void setOptions(CategoryParamOptionsDto options) {
        this.options = options;
    }

    public List<CategoryParamDictionaryDto> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<CategoryParamDictionaryDto> dictionary) {
        this.dictionary = dictionary;
    }

    public CategoryParamRestrictionsDto getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(CategoryParamRestrictionsDto restrictions) {
        this.restrictions = restrictions;
    }

    @Override
    public String toString() {
        return "CategoryParamDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", required=" + required +
                ", unit='" + unit + '\'' +
                '}';
    }
}
