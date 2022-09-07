package pl.estrix.mock;

import org.springframework.context.annotation.Bean;
import pl.estrix.commons.mock.BaseMockService;
import pl.estrix.commons.mock.ReplacingMockComponent;
import pl.estrix.domain.user.model.UserDto;
import pl.estrix.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kamil on 30-07-2020.
 */
@ReplacingMockComponent( clazz = pl.estrix.service.impl.UserServiceImpl.class)
public class MockUserService extends BaseMockService {

	@Bean(name = "mockUserService")
	UserService userService(){
		return new UserService() {
			@Override
			public List<UserDto> getUsers() {
				return Arrays.asList(
						UserDto.builder().id(1L).name("Mock Ala").build(),
						UserDto.builder().id(2L).name("Mock Ola").build()
				);
			}

			@Override
			public UserDto getDetails() {
				return UserDto.builder().id(1L).name("Mock Ala Szczegoly").build();
			}
		};
	}

}
