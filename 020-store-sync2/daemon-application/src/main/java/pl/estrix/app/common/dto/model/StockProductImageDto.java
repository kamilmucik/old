package pl.estrix.app.common.dto.model;

import lombok.*;
import pl.estrix.app.common.base.BaseEntityDto;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StockProductImageDto extends BaseEntityDto<Long> {

    private String url;
    private String thumb;
    private String stored;

    public StockProductImageDto(Long id,String url) {
        this.setId(id);
        this.url = url;
    }
}
