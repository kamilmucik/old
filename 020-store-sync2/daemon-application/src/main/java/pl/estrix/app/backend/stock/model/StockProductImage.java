package pl.estrix.app.backend.stock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.app.backend.base.AuditableEntity;

import javax.persistence.*;

@Entity
@Table(name = "stock_product_image",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockProductImage extends AuditableEntity {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "url", length = 255)
    private String url;

    @Column(name = "content_base64", length = 160000)
    private String base64Content;

    @Column(name = "stored_file")
    private String storedFile;

}
