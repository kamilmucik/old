package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class AllegroValidationDto {

    private List<AllegroValidationErrorDto> errors;

    public AllegroValidationDto() {
    }

    public AllegroValidationDto(List<AllegroValidationErrorDto> errors) {
        this.errors = errors;
    }

    public List<AllegroValidationErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<AllegroValidationErrorDto> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "AllegroValidationDto{" +
                "errors=" + errors +
                '}';
    }
}
