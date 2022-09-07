package pl.estrix.app.lib.allegro.model;

import java.util.ArrayList;
import java.util.List;

public class AllegroOffertDescriptionSectionDto {

    private List<AllegroOffertDescriptionSectionItemDto> items;

    public AllegroOffertDescriptionSectionDto() {
    }

    public AllegroOffertDescriptionSectionDto(List<AllegroOffertDescriptionSectionItemDto> items) {
        this.items = items;
    }

    public List<AllegroOffertDescriptionSectionItemDto> getItems() {
        return items;
    }

    public void setItems(List<AllegroOffertDescriptionSectionItemDto> items) {
        this.items = items;
    }

    public void addItems(String type, String content, String url){
        if (items == null){
            items = new ArrayList<>();
        }

        AllegroOffertDescriptionSectionItemDto item = new AllegroOffertDescriptionSectionItemDto();
        item.setType(type);
        item.setType(type);

        items.add(item);

    }
    public void addContent(String type, String content){
        if (items == null){
            items = new ArrayList<>();
        }

        AllegroOffertDescriptionSectionItemDto item = new AllegroOffertDescriptionSectionItemDto();
        item.setType(type);
        item.setContent(content);

        items.add(item);
    }
    public void addImage(String type, String url){
        if (items == null){
            items = new ArrayList<>();
        }

        AllegroOffertDescriptionSectionItemDto item = new AllegroOffertDescriptionSectionItemDto();
        item.setType(type);
        item.setUrl(url);

        items.add(item);
    }
}
