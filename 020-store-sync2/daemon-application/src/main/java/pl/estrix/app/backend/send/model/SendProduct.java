package pl.estrix.app.backend.send.model;

import lombok.*;
import pl.estrix.app.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Entity
@Table(name = "send_product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class SendProduct extends AuditableEntity {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "shop_product_id")
    private Long shopProductId;

    @Column(name = "shop_categories")
    private String shopCategories;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "margin")
    private BigDecimal margin;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "code", length = 250, nullable = false)
    private String code;
    @Column(name = "message", length = 250, nullable = false)
    private String message;

    @Column(name = "permalink", length = 250, nullable = false)
    private String permalink;

    @Column(name = "image_base64", length = 160000)
    private String image;

}
