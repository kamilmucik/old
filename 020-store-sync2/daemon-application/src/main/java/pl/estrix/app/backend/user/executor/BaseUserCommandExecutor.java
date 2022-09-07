package pl.estrix.app.backend.user.executor;


import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.app.backend.base.BaseCommandExecutor;
import pl.estrix.app.backend.user.dao.UserRepository;
import pl.estrix.app.backend.user.model.User;
import pl.estrix.app.common.dto.model.UserDto;

public class BaseUserCommandExecutor extends BaseCommandExecutor<User, UserDto> {

    @Autowired
    protected UserRepository repository;

    @Override
    protected Class<UserDto> getDtoClass() {
        return UserDto.class;
    }

    public User mapDtoToEntity(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setEnabled(dto.isEnabled());
        entity.setLocked(dto.isLocked());
        entity.setFirstName(dto.getUserName());
        entity.setLastName(dto.getUserLastname());
//        entity.setRole(dto.getRole().getCode() );
        entity.setRole(dto.getRole() );
        return entity;
    }

    public UserDto mapEntityToDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setEnabled(entity.isEnabled());
        dto.setLocked(entity.isLocked());
        dto.setUserName(entity.getFirstName());
        dto.setUserLastname(entity.getLastName());
//        dto.setRole(Role.getByCode(entity.getRole()));
        dto.setRole(entity.getRole());
        return dto;
    }
}
