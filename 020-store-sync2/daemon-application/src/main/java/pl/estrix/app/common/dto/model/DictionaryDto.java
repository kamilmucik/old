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
public class DictionaryDto extends BaseEntityDto<Long> {

    private Long id;

    private Long parameterId;

    private String allegroID;

    private String allegroName;

    private String userId;

    private String dictionaryName;
}
