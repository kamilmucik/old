package pl.estrix.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.estrix.commons.CommonTest;
import pl.estrix.commons.mock.ReplaceableComponent;
import pl.estrix.domain.user.model.UserDto;
import pl.estrix.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kamil on 30-07-2020.
 */
@Service
@Primary
@ReplaceableComponent
public class UserServiceImpl implements UserService{

	CommonTest commonTest;

	@Override
	public List<UserDto> getUsers() {

		commonTest = new CommonTest();

		return Arrays.asList(
				UserDto.builder().id(1L).name("Ala" + commonTest.invokeTest()).build(),
				UserDto.builder().id(2L).name("Ola" + commonTest.invokeTest()).build()
		);
	}

	@Override public
	UserDto getDetails() {
		return UserDto.builder().id(1L).name("Ala Szczegoly").build();
	}
}
