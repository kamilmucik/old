package pl.estrix.app.backend.allegro.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.AllegroAccountDto;

@Component
public class CreateAllegroAccountCommandExecutor extends BaseAllegroAccountCommandExecutor {

    public AllegroAccountDto create(AllegroAccountDto dto) {
        AllegroAccountDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        return result;
    }
}
