package pl.estrix.app.backend.settings.model;

import lombok.*;
import pl.estrix.app.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "app_setting",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AppSetting extends AuditableEntity {

    @Column(name = "setting_name", length = 250, nullable = false)
    private String name;

    @Column(name = "setting_code", length = 250, nullable = false)
    private String code;

    @Column(name = "setting_value", length = 250, nullable = false)
    private String value;

    @Column(name = "setting_type", length = 250, nullable = false)
    private String type;

}
