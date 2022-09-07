package pl.estrix.app.backend.shop.model;

import lombok.*;
import pl.estrix.app.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "shop_category",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ShopCategory extends AuditableEntity {

    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @Column(name = "cat_parent_id")
    private Integer parentId;

    @Column(name = "external_id")
    private Integer externalId;

    @Column(name = "shop_id")
    private Integer shopId;


    @Column(name = "allegro_id")
    private String allegroId;
    @Column(name = "allegro_parent_id")
    private String allegroParentId;
    @Column(name = "allegro_leaf")
    private Boolean allegroLeaf;

    @Column(name = "allegro_children")
    private Integer allegroChildren;

    @Column(name = "shop_type")
    private String type;

}
