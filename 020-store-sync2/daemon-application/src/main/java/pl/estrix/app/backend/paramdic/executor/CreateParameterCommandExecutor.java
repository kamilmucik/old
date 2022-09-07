package pl.estrix.app.backend.paramdic.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.ParameterDto;

@Component
public class CreateParameterCommandExecutor extends BaseParameterCommandExecutor{

    public ParameterDto create(ParameterDto dto) {
        ParameterDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        return result;
    }
}
