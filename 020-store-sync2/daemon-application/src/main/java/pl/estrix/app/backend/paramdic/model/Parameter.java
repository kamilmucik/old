package pl.estrix.app.backend.paramdic.model;

import lombok.*;
import pl.estrix.app.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "parameter_dictionary",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Parameter extends AuditableEntity {

    @Column(name = "allegro_id", length = 250)
    private String allegroID;

    @Column(name = "allegro_name", length = 250)
    private String name;

    @Column(name = "allegro_type", length = 250)
    private String type;

    @Column(name = "allegro_required")
    private Boolean required;

    @Column(name = "allegro_unit", length = 250)
    private String unit;

    @Column(name = "allegro_cat_id", length = 250)
    private String allegroCatId;

    @Column(name = "category_id")
    private Long categoryId;

//    List<Dictionary> options
//    String restrictions

}
