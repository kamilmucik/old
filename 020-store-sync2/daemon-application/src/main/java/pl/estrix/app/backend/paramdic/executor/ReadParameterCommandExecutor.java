package pl.estrix.app.backend.paramdic.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.ParameterSearchCriteriaDto;
import pl.estrix.app.common.dto.model.ParameterDto;

@Component
public class ReadParameterCommandExecutor extends BaseParameterCommandExecutor {

    public ParameterDto findById(Long id) {
        return mapToDto(getOne(id));
    }

    public ListResponseDto<ParameterDto> find(ParameterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        return createListResponseDto(
                pagingCriteria,
                () -> repository.find(searchCriteria, pagingCriteria),
                () -> (int) repository.findCount(searchCriteria));
    }
}
