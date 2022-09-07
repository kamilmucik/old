package pl.estrix.app.backend.event.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.app.backend.base.BaseCommandExecutor;
import pl.estrix.app.backend.event.dao.EventRepository;
import pl.estrix.app.backend.event.model.Event;
import pl.estrix.app.common.dto.model.EventDto;

import java.time.LocalDateTime;

public class BaseEventCommandExecutor extends BaseCommandExecutor<Event, EventDto> {

    @Autowired
    protected EventRepository repository;

    @Override
    protected Class<EventDto> getDtoClass() {
        return EventDto.class;
    }

    protected EventDto mapToDto(Event entity) {
        if (entity == null){
            return null;
        }
        EventDto dto = new EventDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setActive(entity.getActive());

        return dto;
    }

    protected Event mapToEntity(EventDto dto){
        if (dto == null){
            return null;
        }
        Event entity = new Event();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setLastUpdate(LocalDateTime.now());
        entity.setActive(dto.getActive());

        return entity;
    }

    protected Event getOne(Long id) {
        return repository.findById(id);
    }

}
