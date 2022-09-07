package pl.estrix.service;

import pl.estrix.domain.user.model.UserDto;

import java.util.List;

/**
 * Created by Kamil on 30-07-2020.
 */
public interface UserService {

	List<UserDto> getUsers();

	UserDto getDetails();


}
