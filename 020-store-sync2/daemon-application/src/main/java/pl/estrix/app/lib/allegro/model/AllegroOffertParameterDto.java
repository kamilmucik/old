package pl.estrix.app.lib.allegro.model;

import java.util.ArrayList;
import java.util.List;

public class AllegroOffertParameterDto {

    private String id;
    private List<String> valuesIds;
    private List<String> values;
    private Boolean rangeValue;

    public AllegroOffertParameterDto() {
    }

    public AllegroOffertParameterDto(String id, List<String> valuesIds, List<String> values, Boolean rangeValue) {
        this.id = id;
        this.valuesIds = valuesIds;
        this.values = values;
        this.rangeValue = rangeValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addValuesId(String value){
        if (valuesIds == null){
            valuesIds = new ArrayList<>();
        }
        valuesIds.add(value);
    }

    public List<String> getValuesIds() {
        return valuesIds;
    }

    public void setValuesIds(List<String> valuesIds) {
        this.valuesIds = valuesIds;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public Boolean getRangeValue() {
        return rangeValue;
    }

    public void setRangeValue(Boolean rangeValue) {
        this.rangeValue = rangeValue;
    }
}
