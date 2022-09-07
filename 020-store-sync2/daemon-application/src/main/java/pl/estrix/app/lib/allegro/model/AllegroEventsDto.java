package pl.estrix.app.lib.allegro.model;

public class AllegroEventsDto extends AllegroBaseDto {

    private String latestEvent;

    public AllegroEventsDto() {
    }
    public AllegroEventsDto(String latestEvent) {
        this.latestEvent = latestEvent;
    }

    public String getLatestEvent() {
        return latestEvent;
    }

    public void setLatestEvent(String latestEvent) {
        this.latestEvent = latestEvent;
    }


    public String getError() {
        return super.error;
    }

    public void setError(String error) {
        super.error = error;
    }

    public String getError_description() {
        return super.error_description;
    }

    public void setError_description(String error_description) {
        super.error_description = error_description;
    }

    @Override
    public String toString() {
        return "AllegroEventsDto{" +
                "latestEvent='" + latestEvent + '\'' +
                '}';
    }
}
