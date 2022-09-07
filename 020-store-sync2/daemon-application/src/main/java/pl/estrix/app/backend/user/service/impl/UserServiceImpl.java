package pl.estrix.app.backend.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.user.executor.CreateUserCommandExecutor;
import pl.estrix.app.backend.user.executor.DeleteUserCommandExecutor;
import pl.estrix.app.backend.user.executor.ReadUserCommandExecutor;
import pl.estrix.app.backend.user.executor.UpdateUserCommandExecutor;
import pl.estrix.app.backend.user.service.UserService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.UserSearchCriteriaDto;
import pl.estrix.app.common.dto.model.UserDto;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;

@Service
@Primary
public class UserServiceImpl implements UserService {

    @Autowired
    private ReadUserCommandExecutor readExecutor;
    @Autowired
    private CreateUserCommandExecutor createExecutor;
    @Autowired
    private UpdateUserCommandExecutor updateExecutor;
    @Autowired
    private DeleteUserCommandExecutor deleteExecutor;

    @Autowired
    private PasswordEncoder standardPasswordEncoder;

    @Override
    @Transactional
    public ListResponseDto<UserDto> getItems(UserSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        return readExecutor.find(searchCriteria,pagingCriteria);
    }

    @Override
    @Transactional
    public UserDto saveOrUpdate(UserDto userDto) throws NoSuchAlgorithmException{
        UserDto temp = readExecutor.findById(userDto.getId());

        if (userDto.getId() != null){
            userDto.setPassword(temp.getPassword());
            temp = updateExecutor.update(userDto);
        } else {
            temp = createExecutor.create(userDto);
        }
        return temp;
    }

    @Override
    @Transactional
    public UserDto getItem(Long id){
        return readExecutor.findById(id);
    }

    @Override
    @Transactional
    public UserDto getItem(String login){
        return readExecutor.findByLogin(login);
    }

//    public List<UserDto> getItems(){
//        return readExecutor.
//                .findAll()
//                .stream()
//                .map(readExecutor::mapEntityToDto)
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public void delete (UserDto storeDto){
        deleteExecutor.delete(storeDto);
    }

    @Override
    public UserDto changePassword(UserDto userDto) {
        UserDto temp = readExecutor.findById(userDto.getId());
        temp.setPassword(standardPasswordEncoder.encode(userDto.getPassword()));

        if (temp.getId() != null){
            temp = updateExecutor.update(temp);
        }
        return temp;
    }
}
