package pl.estrix.app.common.dto.model;

import lombok.*;
import pl.estrix.app.common.base.BaseEntityDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SendProductDto extends BaseEntityDto<Long> {

    private Long productId;
    private Long shopId;
    private Long shopProductId;
    private String shopCategories;
    private BigDecimal price;
    private BigDecimal margin;
    private Integer stock;
    private Boolean processed;
    private String permalink;
    private String image;
    private List<SendProductImageDto> images = new ArrayList<>();

    private String code;
    private String message;

    public void addImages(SendProductImageDto image){
        if (images == null){
            images = new ArrayList<>();
        }
        images.add(image);
    }

}
