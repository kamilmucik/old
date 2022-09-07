package pl.estrix.app.backend.event.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.EventDto;

@Component
public class CreateEventCommandExecutor extends BaseEventCommandExecutor {

    public EventDto create(EventDto dto) {
        EventDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        return result;
    }
}
