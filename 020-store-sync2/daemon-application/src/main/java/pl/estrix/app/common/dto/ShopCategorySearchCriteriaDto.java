package pl.estrix.app.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopCategorySearchCriteriaDto {

    private Boolean leaf;

    private Integer allegroChildren;

    private String type;

    private Integer parentId;

    private Long shopId;

}
