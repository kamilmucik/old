package pl.estrix.app.lib.allegro.model;

public class AllegroAccessHelpDto {

    public AllegroAccessHelpDto() {
    }

    public AllegroAccessHelpDto(String jti, String client_id, String user_name) {
        this.jti = jti;
        this.client_id = client_id;
        this.user_name = user_name;
    }

    private String jti;
    private String client_id;
    private String user_name;

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
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
}
