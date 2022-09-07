package pl.estrix.app.backend.event.model;

import lombok.*;
import pl.estrix.app.backend.base.AuditableEntity;
import pl.estrix.app.backend.converter.LocalDateTimeToStringConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_events",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Event extends AuditableEntity {

    @Column(name = "name", length = 250)
    private String name;

    @Column(name = "event_active")
    private Boolean active;

}
