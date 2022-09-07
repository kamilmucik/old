package pl.estrix.app.lib.allegro.model;

public class CategoryParamOptionsDto {

    private Boolean variantsAllowed;
    private Boolean variantsEqual;

    public CategoryParamOptionsDto() {
    }

    public CategoryParamOptionsDto(Boolean variantsAllowed, Boolean variantsEqual) {
        this.variantsAllowed = variantsAllowed;
        this.variantsEqual = variantsEqual;
    }

    public Boolean getVariantsAllowed() {
        return variantsAllowed;
    }

    public void setVariantsAllowed(Boolean variantsAllowed) {
        this.variantsAllowed = variantsAllowed;
    }

    public Boolean getVariantsEqual() {
        return variantsEqual;
    }

    public void setVariantsEqual(Boolean variantsEqual) {
        this.variantsEqual = variantsEqual;
    }

    @Override
    public String toString() {
        return "CategoryParamOptionsDto{" +
                "variantsAllowed=" + variantsAllowed +
                ", variantsEqual=" + variantsEqual +
                '}';
    }
}
