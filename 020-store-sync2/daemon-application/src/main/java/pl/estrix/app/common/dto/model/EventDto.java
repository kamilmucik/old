package pl.estrix.app.common.dto.model;

import lombok.*;
import pl.estrix.app.common.base.BaseEntityDto;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventDto extends BaseEntityDto<Long> {

    private String name;//Cena/Magazyn/Sprzedaż

    private LocalDateTime lastUpdate;

    private Boolean active;

    private String oldValue;
    private String newValue;
    private String source;//sklep/magazyn/allegro

}
