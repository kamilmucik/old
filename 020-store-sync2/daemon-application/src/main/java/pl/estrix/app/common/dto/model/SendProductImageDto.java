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
public class SendProductImageDto extends BaseEntityDto<Long> {

    private String src;

    private Long position;

    private Long sendProductId;

}
