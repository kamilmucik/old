package pl.estrix.app.common.dto.model;

import lombok.*;
import pl.estrix.app.common.base.BaseEntityDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShopDto extends BaseEntityDto<Long> {

    public ShopDto(Long id){
        this.setId(id);
    }

    private String name;

    private String url;

    private String apiUrl;

    private String consumerKey;

    private String consumerSecret;

    private String status;

    private List<String> stockIds = new ArrayList<>();

    public LocalDateTime getLastUpdated() {
        return super.getLastUpdated();
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        super.setLastUpdated(lastUpdated);
    }


    @Override
    public String toString() {
        return "ShopDto{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", consumerKey='" + consumerKey + '\'' +
                ", consumerSecret='" + consumerSecret + '\'' +
                '}';
    }

}
