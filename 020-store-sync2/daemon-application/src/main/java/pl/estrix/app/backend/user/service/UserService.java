package pl.estrix.app.backend.user.service;

import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.UserSearchCriteriaDto;
import pl.estrix.app.common.dto.model.UserDto;

import java.security.NoSuchAlgorithmException;


public interface UserService {

    ListResponseDto<UserDto> getItems(UserSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    UserDto saveOrUpdate(UserDto userDto) throws NoSuchAlgorithmException;

    UserDto getItem(Long id);

    UserDto getItem(String login);

    void delete (UserDto storeDto);

    UserDto changePassword(UserDto userDto) ;
}
