package pl.estrix.app.backend.settings.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.SettingDto;

@Component
public class CreateSettingCommandExecutor extends BaseSettingCommandExecutor {

    public SettingDto create(SettingDto dto) {
        SettingDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        return result;
    }

}
