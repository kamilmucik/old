package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class AllegroSaleTags {

    private List<AllegroSaleTag> tags;

    public AllegroSaleTags() {
    }

    public AllegroSaleTags(List<AllegroSaleTag> tags) {
        this.tags = tags;
    }

    public List<AllegroSaleTag> getTags() {
        return tags;
    }

    public void setTags(List<AllegroSaleTag> tags) {
        this.tags = tags;
    }
}
