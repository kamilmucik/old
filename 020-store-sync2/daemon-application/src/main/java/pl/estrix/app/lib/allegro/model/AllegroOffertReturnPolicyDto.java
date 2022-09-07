package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class AllegroOffertReturnPolicyDto {
    private String id;
    private String name;

    List<AllegroDictionaryDto> returnPolicies;

    public AllegroOffertReturnPolicyDto() {
    }

    public AllegroOffertReturnPolicyDto(String id) {
        this.id = id;
    }

    public AllegroOffertReturnPolicyDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AllegroDictionaryDto> getReturnPolicies() {
        return returnPolicies;
    }

    public void setReturnPolicies(List<AllegroDictionaryDto> returnPolicies) {
        this.returnPolicies = returnPolicies;
    }

    @Override
    public String toString() {
        return "AllegroOffertReturnPolicyDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", returnPolicies=" + returnPolicies +
                '}';
    }
}
