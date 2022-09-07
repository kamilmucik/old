package pl.estrix.app.lib.woocommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.app.common.base.BaseEntityDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWooProductDto extends BaseEntityDto<Integer> {

    private String price;
    private String regular_price;
    private Integer stock_quantity;
    private List<WooCategoryDto> categories;

    private String code;
    private String message;



}
