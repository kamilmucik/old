package pl.estrix.app.backend.event.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.EventSearchCriteriaDto;
import pl.estrix.app.common.dto.model.EventDto;

@Component
public class ReadEventCommandExecutor extends BaseEventCommandExecutor {


    public EventDto findById(Long id) {
        return mapToDto(getOne(id));
    }

    public ListResponseDto<EventDto> find(EventSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        return createListResponseDto(
                pagingCriteria,
                () -> repository.find(searchCriteria, pagingCriteria),
                () -> (int) repository.findCount(searchCriteria));
    }
}
