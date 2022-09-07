package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class CategoryParamsDto {

    private List<CategoryParamDto> parameters;

    public CategoryParamsDto() {
    }

    public CategoryParamsDto(List<CategoryParamDto> parameters) {
        this.parameters = parameters;
    }

    public List<CategoryParamDto> getParameters() {
        return parameters;
    }

    public void setParameters(List<CategoryParamDto> parameters) {
        this.parameters = parameters;
    }
}
