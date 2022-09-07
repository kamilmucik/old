package pl.estrix.app.backend.settings.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.settings.executor.CreateSettingCommandExecutor;
import pl.estrix.app.backend.settings.executor.ReadSettingCommandExecutor;
import pl.estrix.app.backend.settings.executor.UpdateSettingCommandExecutor;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.SettingSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SettingDto;

import java.util.Optional;

@Service
@Primary
public class SettingServiceImpl implements SettingService {

    @Autowired
    private ReadSettingCommandExecutor readExecutor;
    @Autowired
    private CreateSettingCommandExecutor createExecutor;
    @Autowired
    private UpdateSettingCommandExecutor updateExecutor;

    @Override
    public Optional<SettingDto> get(String name) {
        return Optional.ofNullable(readExecutor.findByName(name));
    }
    @Override
    public Optional<SettingDto> get(Long id) {
        return Optional.ofNullable(readExecutor.findById(id));
    }
    @Override
    public Optional<SettingDto> getByCode(String code) {
        return Optional.ofNullable(readExecutor.findByCode(code));
    }

    @Override
    public SettingDto create(SettingDto dto) {
        return createExecutor.create(dto);
    }

    @Override
    public SettingDto update(SettingDto dto) {
        return updateExecutor.update(dto);
    }

    @Override
    public ListResponseDto<SettingDto> getItems(SettingSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        return readExecutor.find(searchCriteria,pagingCriteria);
    }
}
