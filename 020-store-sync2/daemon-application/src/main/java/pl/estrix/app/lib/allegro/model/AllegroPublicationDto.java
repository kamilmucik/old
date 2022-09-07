package pl.estrix.app.lib.allegro.model;

public class AllegroPublicationDto {

    private String endingAt;

    public AllegroPublicationDto(String endingAt) {
        this.endingAt = endingAt;
    }

    public String getEndingAt() {
        return endingAt;
    }

    public void setEndingAt(String endingAt) {
        this.endingAt = endingAt;
    }
}
