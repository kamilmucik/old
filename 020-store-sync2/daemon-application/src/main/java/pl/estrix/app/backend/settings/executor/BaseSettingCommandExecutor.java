package pl.estrix.app.backend.settings.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.app.backend.base.BaseCommandExecutor;
import pl.estrix.app.backend.settings.dao.SettingRepository;
import pl.estrix.app.backend.settings.model.AppSetting;
import pl.estrix.app.common.dto.model.SettingDto;

public class BaseSettingCommandExecutor  extends BaseCommandExecutor<AppSetting, SettingDto> {

    @Autowired
    protected SettingRepository repository;

    @Override
    protected Class<SettingDto> getDtoClass() {
        return SettingDto.class;
    }

    protected SettingDto mapToDto(AppSetting entity) {
        if (entity == null){
            return null;
        }
        SettingDto dto = new SettingDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setValue(entity.getValue());
        dto.setType(entity.getType());
        dto.setCode(entity.getCode());

        return dto;
    }

    protected AppSetting mapToEntity(SettingDto dto){
        if (dto == null){
            return null;
        }
        AppSetting entity = new AppSetting();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        entity.setType(dto.getType());
        entity.setCode(dto.getCode());

        return entity;
    }

    protected AppSetting getOne(Long id) {
        return repository.findById(id);
    }

    protected AppSetting getOne(String name) {
        return repository.findByName(name);
    }
}
