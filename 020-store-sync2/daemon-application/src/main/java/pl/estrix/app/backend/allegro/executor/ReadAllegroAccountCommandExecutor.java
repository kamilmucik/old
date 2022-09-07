package pl.estrix.app.backend.allegro.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.AllegroAccountSearchCriteriaDto;
import pl.estrix.app.common.dto.model.AllegroAccountDto;

@Component
public class ReadAllegroAccountCommandExecutor extends BaseAllegroAccountCommandExecutor {

    public AllegroAccountDto findById(Long id) {
        return mapToDto(getOne(id));
    }

    public ListResponseDto<AllegroAccountDto> find(AllegroAccountSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        return createListResponseDto(
                pagingCriteria,
                () -> repository.find(searchCriteria, pagingCriteria),
                () -> (int) repository.findCount(searchCriteria));
    }
}
