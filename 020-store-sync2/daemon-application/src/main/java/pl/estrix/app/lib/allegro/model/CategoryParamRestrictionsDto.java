package pl.estrix.app.lib.allegro.model;

public class CategoryParamRestrictionsDto {

    private Boolean multipleChoices;

    public CategoryParamRestrictionsDto() {
    }

    public CategoryParamRestrictionsDto(Boolean multipleChoices) {
        this.multipleChoices = multipleChoices;
    }

    public Boolean getMultipleChoices() {
        return multipleChoices;
    }

    public void setMultipleChoices(Boolean multipleChoices) {
        this.multipleChoices = multipleChoices;
    }

    @Override
    public String toString() {
        return "CategoryParamRestrictionsDto{" +
                "multipleChoices=" + multipleChoices +
                '}';
    }
}
