package pl.estrix.app.common.dto.model;

import lombok.*;
import pl.estrix.app.common.base.BaseEntityDto;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class ParameterDto extends BaseEntityDto<Long> {

    private String allegroID;

    private String name;

    private String type;

    private Boolean required;

    private String unit;

    private Long categoryId;
    private String allegroCatId;


}
