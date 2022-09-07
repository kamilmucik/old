package pl.estrix.app.backend.paramdic.model;

import lombok.*;
import pl.estrix.app.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "dictionary",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Dictionary extends AuditableEntity {

    @Column(name = "parameter_id")
    private Long parameterId;

    @Column(name = "allegro_id", length = 250)
    private String allegroID;

    @Column(name = "allegro_val", length = 250)
    private String allegroName;

    @Column(name = "user_id", length = 250)
    private String userId;

    @Column(name = "dictionary_name", length = 250)
    private String dictionaryName;

}
