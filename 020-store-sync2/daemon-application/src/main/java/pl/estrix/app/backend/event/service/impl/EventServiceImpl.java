package pl.estrix.app.backend.event.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.event.executor.CreateEventCommandExecutor;
import pl.estrix.app.backend.event.executor.ReadEventCommandExecutor;
import pl.estrix.app.backend.event.service.EventService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.EventSearchCriteriaDto;
import pl.estrix.app.common.dto.model.EventDto;

@Service
@Primary
public class EventServiceImpl implements EventService {

    @Autowired
    private ReadEventCommandExecutor readExecutor;
    @Autowired
    private CreateEventCommandExecutor createExecutor;

    @Override
    public EventDto get(Long id) {
        return readExecutor.findById(id);
    }

    @Override
    public EventDto create(EventDto dto) {
        return createExecutor.create(dto);
    }

    @Override
    public ListResponseDto<EventDto> getItems(EventSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        ListResponseDto<EventDto> listResponseDto = readExecutor.find(searchCriteria,pagingCriteria);

        return listResponseDto;
    }
}
