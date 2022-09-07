package pl.estrix.app.backend.settings.service;


import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.SettingSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SettingDto;
import java.util.Optional;

public interface SettingService {

    Optional<SettingDto> get(String name);
    Optional<SettingDto> get(Long id);
    Optional<SettingDto> getByCode(String code);

    SettingDto create(SettingDto dto);
    SettingDto update(SettingDto dto);

    ListResponseDto<SettingDto> getItems(SettingSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

}
