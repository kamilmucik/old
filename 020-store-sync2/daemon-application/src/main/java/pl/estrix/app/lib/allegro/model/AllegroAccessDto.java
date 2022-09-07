package pl.estrix.app.lib.allegro.model;

public class AllegroAccessDto {

    /**
     * token dostępowy, który pozwala wykonywać
     * operacje na zasobach dostępnych publicznie
     */
    private String access_token;
    /**
     * typ tokena (w naszym przypadku: bearer)
     */
    private String token_type;

    /**
     * Wartość refresh tokena, uzyskanego przy generowaniu poprzedniego tokena dostępowego
     */
    private String refresh_token;
    /**
     * czas ważności tokena dostępowego w sekundach (token jest ważny 12 godzin)
     */
    private int expires_in;
    /**
     * zasięg danych/funkcjonalności do których
     * użytkownik autoryzował aplikacje (obecnie
     * nieużywany)
     */
    private String scope;
    /**
     * identyfikator tokena JWT (brak bezpośredniego zastosowania)
     */
    private String jti;
    private String client_id;
    private String user_name;
    private String error;
    private String error_description;

    public AllegroAccessDto() {
    }

    public AllegroAccessDto(String access_token, String token_type, String refresh_token, int expires_in, String scope, String jti, String error, String error_description) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = scope;
        this.jti = jti;
        this.error = error;
        this.error_description = error_description;
    }


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "AllegroAccessDto{" +
                "access_token='" + access_token + '\'' +
                ", error='" + error + '\'' +
                ", error_description='" + error_description + '\'' +
                '}';
    }
}
