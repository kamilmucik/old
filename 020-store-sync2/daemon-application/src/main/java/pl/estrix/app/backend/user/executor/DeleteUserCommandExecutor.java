package pl.estrix.app.backend.user.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.UserDto;

@Component
public class DeleteUserCommandExecutor extends BaseUserCommandExecutor{

    public void delete(UserDto dto){
        repository.delete(dto.getId());
    }
}
