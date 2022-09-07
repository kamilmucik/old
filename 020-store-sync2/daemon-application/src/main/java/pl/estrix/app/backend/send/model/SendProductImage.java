package pl.estrix.app.backend.send.model;

import lombok.*;
import pl.estrix.app.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "send_product_image",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class SendProductImage extends AuditableEntity {

    @Column(name = "send_product_id")
    private Long sendProductId;

    @Column(name = "src")
    private String src;

    @Column(name = "position")
    private Long position;

}
