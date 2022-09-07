package pl.estrix.app.backend.base;

import lombok.*;
import pl.estrix.app.backend.converter.LocalDateTimeToStringConverter;

import javax.persistence.*;
import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@ToString
public abstract class AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "last_update")
    @Convert(converter = LocalDateTimeToStringConverter.class)
    private LocalDateTime lastUpdate;
}
