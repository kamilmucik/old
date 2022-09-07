package pl.estrix.app.backend.user.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.user.model.User;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.UserSearchCriteriaDto;
import pl.estrix.app.common.dto.model.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadUserCommandExecutor extends BaseUserCommandExecutor{

    public UserDto getByEmail(UserDto userDto){
        return mapEntityToDto(repository.findOneByEmail(userDto.getEmail()));
    }

    public UserDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }

    public UserDto findByLogin(String login) {
        return mapEntityToDto(repository.findOneByEmail(login) );
    }

    public ListResponseDto<UserDto> find(UserSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<User> result = repository.find(searchCriteria, pagingCriteria);
        List<UserDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) repository.findCount(searchCriteria));
    }
}
