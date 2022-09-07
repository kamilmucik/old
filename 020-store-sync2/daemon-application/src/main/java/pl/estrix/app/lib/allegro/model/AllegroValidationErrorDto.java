package pl.estrix.app.lib.allegro.model;

public class AllegroValidationErrorDto {

    private String code;
    private String message;
    private String details;
    private String path;
    private String userMessage;

    public AllegroValidationErrorDto() {
    }

    public AllegroValidationErrorDto(String code, String message, String details, String path, String userMessage) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.path = path;
        this.userMessage = userMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public String toString() {
        return "AllegroValidationErrorDto{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                ", path='" + path + '\'' +
                ", userMessage='" + userMessage + '\'' +
                '}';
    }
}
