package pl.estrix.app.backend.stock.model;

import lombok.Data;
import pl.estrix.app.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Entity
@Table(name = "stock_product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
public class StockProduct extends AuditableEntity {

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "name", length = 255)
    private String name;
    @Column(name = "product_code", length = 255)
    private String code;

    @Column(name = "price", length = 255)
    private BigDecimal price;
    @Column(name = "price_brutto", length = 255)
    private BigDecimal priceBrutto;
    @Column(name = "price_retail", length = 255)
    private BigDecimal priceRetail;

    @Column(name = "stock")
    private Integer stock;
    @Column(name = "stock_ext")
    private Integer stockExt;

    @Column(name = "_description", length = 160000)
    private String description;
    @Column(name = "active", length = 255)
    private Boolean active;
    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "ext_id", length = 255)
    private String extId;
    @Column(name = "ext_url", length = 255)
    private String extUrl;
    @Column(name = "shipping_time", length = 255)
    private String shippingTime;

    @Column(name = "image_base64", length = 160000)
    private String mainThumb;
//    @Column(name = "last_update")
//    @Convert(converter = LocalDateTimeToStringConverter.class)
//    private LocalDateTime lastUpdate;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private List<StockProductImage> images = new ArrayList<>();
}
