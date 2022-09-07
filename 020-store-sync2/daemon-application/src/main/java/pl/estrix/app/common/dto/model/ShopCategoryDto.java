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
public class ShopCategoryDto extends BaseEntityDto<Long> {

        private String name;

        private Integer externalId;

        private Integer parentId;

        private Integer shopId;

        private String allegroId;

        private String allegroParentId;

        private Integer allegroChildren;

        private Boolean allegroLeaf;

        private String type;

        @Override
        public String toString() {
                return name;
        }
}
