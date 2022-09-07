package pl.estrix.app.backend.event.dao;


import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.dto.EventSearchCriteriaDto;
import pl.estrix.app.common.dto.model.EventDto;

import java.util.List;

public interface EventRepositoryCustom {

    List<EventDto> find(EventSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(EventSearchCriteriaDto searchCriteria);
}
