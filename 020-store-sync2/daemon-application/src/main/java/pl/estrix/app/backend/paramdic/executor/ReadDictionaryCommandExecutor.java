package pl.estrix.app.backend.paramdic.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.DictionarySearchCriteriaDto;
import pl.estrix.app.common.dto.model.DictionaryDto;

@Component
public class ReadDictionaryCommandExecutor extends BaseDictionaryCommandExecutor {

    public DictionaryDto findById(Long id) {
        return mapToDto(getOne(id));
    }

    public ListResponseDto<DictionaryDto> find(DictionarySearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        return createListResponseDto(
                pagingCriteria,
                () -> repository.find(searchCriteria, pagingCriteria),
                () -> (int) repository.findCount(searchCriteria));
    }
}
