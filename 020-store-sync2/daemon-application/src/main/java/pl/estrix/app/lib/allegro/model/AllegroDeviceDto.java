package pl.estrix.app.lib.allegro.model;

public class AllegroDeviceDto {

    /**
     * kod użytkownika - zalecamy
     * przedstawić te dane użytkownikowi
     * w postaci XXX XXX XXX. Taka forma
     * będzie dla niego czytelniejsza
     * przy przepisywaniu.
     */
    private String user_code;

    /**
     * kod aplikacji - niezbędny
     * do uzyskania tokenu dostępowego
     */
    private String device_code;

    /**
     * adres do weryfikacji użytkownika
     */
    private String verification_uri;

    /**
     * adres do weryfikacji dla użytkownika z
     * wypełnionym kodem użytkownika
     */
    private String verification_uri_complete;
    /**
     * liczba sekund, przez które ważne są oba kody
     */
    private Integer expires_in;
    /**
     * wymagany odstęp (w sekundach)
     * pomiędzy kolejnymi zapytaniami o
     * status autoryzacji. Jeśli będziesz
     * odpytywać częściej otrzymasz
     * odpowiedź o statusie HTTP 400 z
     * kodem: "slow_down".
     */
    private Integer interval;

    public AllegroDeviceDto() {
    }

    public AllegroDeviceDto(String device_code, String user_code, String verification_uri, String verification_uri_complete, Integer expires_in, Integer interval) {
        this.device_code = device_code;
        this.user_code = user_code;
        this.verification_uri = verification_uri;
        this.verification_uri_complete = verification_uri_complete;
        this.expires_in = expires_in;
        this.interval = interval;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getVerification_uri() {
        return verification_uri;
    }

    public void setVerification_uri(String verification_uri) {
        this.verification_uri = verification_uri;
    }

    public String getVerification_uri_complete() {
        return verification_uri_complete;
    }

    public void setVerification_uri_complete(String verification_uri_complete) {
        this.verification_uri_complete = verification_uri_complete;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "AllegroDeviceDto{" +
                "device_code='" + device_code + '\'' +
                ", user_code='" + user_code + '\'' +
                ", verification_uri='" + verification_uri + '\'' +
                ", verification_uri_complete='" + verification_uri_complete + '\'' +
                ", expires_in=" + expires_in +
                ", interval=" + interval +
                '}';
    }
}
