package pl.estrix.app.backend.event.service;


import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.EventSearchCriteriaDto;
import pl.estrix.app.common.dto.model.EventDto;

public interface EventService {

    EventDto get(Long id);

    EventDto create(EventDto dto);

    ListResponseDto<EventDto> getItems(EventSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);


}
