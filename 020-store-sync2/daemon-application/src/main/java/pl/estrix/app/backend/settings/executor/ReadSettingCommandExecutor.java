package pl.estrix.app.backend.settings.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.SettingSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SettingDto;

@Component
public class ReadSettingCommandExecutor extends BaseSettingCommandExecutor {

    public SettingDto findById(Long id) {
        return mapToDto(getOne(id));
    }
    public SettingDto findByName(String name) {
        return mapToDto(getOne(name));
    }
    public SettingDto findByCode(String code) {
        return mapToDto( repository.findByCode(code));
    }

    public ListResponseDto<SettingDto> find(SettingSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        return createListResponseDto(
                pagingCriteria,
                () -> repository.find(searchCriteria, pagingCriteria),
                () -> (int) repository.findCount(searchCriteria));
//        return null;
    }
}
