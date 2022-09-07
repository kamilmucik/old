package pl.estrix.app.backend.paramdic.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.DictionaryDto;

@Component
public class CreateDictionaryCommandExecutor extends BaseDictionaryCommandExecutor{

    public DictionaryDto create(DictionaryDto dto) {
        DictionaryDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        return result;
    }
}
