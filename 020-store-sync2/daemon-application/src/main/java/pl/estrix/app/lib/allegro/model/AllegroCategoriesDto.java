package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class AllegroCategoriesDto {

    List<AllegroCategoryDto> categories;

    public AllegroCategoriesDto() {
    }

    public AllegroCategoriesDto(List<AllegroCategoryDto> categories) {
        this.categories = categories;
    }

    public List<AllegroCategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<AllegroCategoryDto> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "AllegroCategoriesDto{" +
                "categories=" + categories +
                '}';
    }
}
