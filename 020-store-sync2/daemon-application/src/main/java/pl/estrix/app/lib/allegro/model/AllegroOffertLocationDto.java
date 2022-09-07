package pl.estrix.app.lib.allegro.model;

public class AllegroOffertLocationDto {

    private String countryCode;
    private String province;
    private String city;
    private String postCode;

    public AllegroOffertLocationDto() {
    }

    public AllegroOffertLocationDto(String countryCode, String province, String city, String postCode) {
        this.countryCode = countryCode;
        this.province = province;
        this.city = "Dobiecin";
        this.postCode = postCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
