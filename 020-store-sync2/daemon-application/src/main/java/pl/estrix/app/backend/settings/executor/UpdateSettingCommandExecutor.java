package pl.estrix.app.backend.settings.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.SettingDto;

@Component
public class UpdateSettingCommandExecutor extends BaseSettingCommandExecutor {

    public SettingDto update(SettingDto dto) {
        SettingDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        return result;
    }

}
