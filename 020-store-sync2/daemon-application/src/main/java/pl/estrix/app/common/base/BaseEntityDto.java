package pl.estrix.app.common.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntityDto<T> extends BaseDto implements EntityDto<T> {

    private T id;

    private String label;
    private LocalDate dateCreate;
    private LocalTime timeCreate;
    private LocalDateTime lastUpdated;

    public String getLabel() {
        return label;
    }

    @Override
    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}