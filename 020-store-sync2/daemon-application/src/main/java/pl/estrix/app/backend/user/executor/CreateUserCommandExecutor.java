package pl.estrix.app.backend.user.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.UserDto;

@Component
public class CreateUserCommandExecutor extends BaseUserCommandExecutor{

    public UserDto create(UserDto storeDto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(storeDto)
                ));
    }
}
