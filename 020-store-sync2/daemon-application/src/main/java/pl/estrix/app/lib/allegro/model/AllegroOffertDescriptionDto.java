package pl.estrix.app.lib.allegro.model;

import java.util.ArrayList;
import java.util.List;

public class AllegroOffertDescriptionDto {

    private List<AllegroOffertDescriptionSectionDto> sections;

    public AllegroOffertDescriptionDto() {
    }

    public AllegroOffertDescriptionDto(List<AllegroOffertDescriptionSectionDto> sections) {
        this.sections = sections;
    }

    public List<AllegroOffertDescriptionSectionDto> getSections() {
        return sections;
    }

    public void setSections(List<AllegroOffertDescriptionSectionDto> sections) {
        this.sections = sections;
    }

    public void addSection(AllegroOffertDescriptionSectionDto sectionDto){
        if (sections == null){
            sections = new ArrayList<>();
        }
        sections.add(sectionDto);

    }
}
