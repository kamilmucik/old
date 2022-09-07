package pl.estrix.app.common.dto.model;

import lombok.*;
import pl.estrix.app.common.base.BaseEntityDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StockProductDto extends BaseEntityDto<Long> implements Serializable {

    private String name;
    private String code;
    private String description;

    private BigDecimal price;
    private BigDecimal priceBrutto;
    private BigDecimal priceRetail;

    private Integer stock;
    private Integer stockExt;
    private Integer stockToSend;
    private Boolean avail;

    private String extId;
    private String extUrl;
    private String shippingTime;

    private String mainThumb;

    private List<StockProductImageDto> images;
    private List<StockProductAttributeDto> attributes;
    private LocalDateTime lastUpdate;

    private Long stockId;
    private Long shopId;

    public void addImage(StockProductImageDto image){
        if (images == null){
            images = new ArrayList<>();
        }
        images.add(image);
    }
    public void addAttributes(StockProductAttributeDto attribute){
        if (attributes == null){
            attributes = new ArrayList<>();
        }
        attributes.add(attribute);
    }
}
