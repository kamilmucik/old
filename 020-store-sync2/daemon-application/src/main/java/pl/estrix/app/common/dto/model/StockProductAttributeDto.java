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
public class StockProductAttributeDto extends BaseEntityDto<Long> {

    private String name;
    private String value;

    public StockProductAttributeDto(Long id, String name, String value) {
        this.setId(id);
        this.name = name;
        this.value = value;
    }
}
