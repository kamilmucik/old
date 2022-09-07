package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class AllegroOffertResponse {

    private String id;
    private String name;
    private AllegroCategoryDto category;

    private AllegroValidationDto validation;

    private String createdAt;
    private String updatedAt;

    public AllegroOffertResponse() {
    }

    public AllegroOffertResponse(String id, String name, AllegroCategoryDto category, AllegroValidationDto validation) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.validation = validation;
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

    public AllegroCategoryDto getCategory() {
        return category;
    }

    public void setCategory(AllegroCategoryDto category) {
        this.category = category;
    }

    public AllegroValidationDto getValidation() {
        return validation;
    }

    public void setValidation(AllegroValidationDto validation) {
        this.validation = validation;
    }

    @Override
    public String toString() {

//        validation.getErrors().stream().forEach(System.out::println);
StringBuilder msg = new StringBuilder("AllegroOffertResponse{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", category=" + category +
        ", validation=" + validation +
        '}');

if (validation.getErrors() != null){
    for (AllegroValidationErrorDto error : validation.getErrors()){
        msg.append("\n").append(error.getCode()).append(" : ").append(error.getMessage());
    }
}

        return msg.toString();
    }
}
