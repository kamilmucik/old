package pl.estrix.app.common.dto.model;

import lombok.*;
import pl.estrix.app.common.base.BaseEntityDto;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StockDto extends BaseEntityDto<Long> {

    public StockDto(Long id){
        this.setId(id);
    }
    private String name;

    private String updateHour;

    private String url;

    private String apiUrl;

    private Integer productCounter;

    private LocalDateTime lastUpdate;

    private String status;

    private Boolean siteScratch;

    public LocalDateTime getLastUpdated() {
        return super.getLastUpdated();
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        super.setLastUpdated(lastUpdated);
    }

}
