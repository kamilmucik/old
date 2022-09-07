package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class AllegroCategoryParamsDto {

    private List<CategoryParamDto> parameters;

    public AllegroCategoryParamsDto() {
    }

    public AllegroCategoryParamsDto(List<CategoryParamDto> parameters) {
        this.parameters = parameters;
    }

    public List<CategoryParamDto> getParameters() {
        return parameters;
    }

    public void setParameters(List<CategoryParamDto> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "AllegroCategoryParamsDto{" +
                "parameters=" + parameters +
                '}';
    }
}
