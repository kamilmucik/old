package pl.estrix.app.lib.allegro.model;

public class AllegroOffertPublicationDto {

    private String duration;
    private String status;
    private String startingAt;
    private String endingAt;
    private String endedBy;

    public AllegroOffertPublicationDto() {
    }

    public AllegroOffertPublicationDto(String duration, String status) {
        this.duration = duration;
        this.status = status;
        this.startingAt = null;
        this.endingAt = null;
        this.endedBy = null;
    }
    public AllegroOffertPublicationDto(String duration, String status, String startingAt, String endingAt, String endedBy) {
        this.duration = duration;
        this.status = status;
        this.startingAt = startingAt;
        this.endingAt = endingAt;
        this.endedBy = endedBy;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartingAt() {
        return startingAt;
    }

    public void setStartingAt(String startingAt) {
        this.startingAt = startingAt;
    }

    public String getEndingAt() {
        return endingAt;
    }

    public void setEndingAt(String endingAt) {
        this.endingAt = endingAt;
    }

    public String getEndedBy() {
        return endedBy;
    }

    public void setEndedBy(String endedBy) {
        this.endedBy = endedBy;
    }
}
