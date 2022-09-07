package pl.estrix.controller.domain.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.estrix.controller.domain.user.UserGateway;
import pl.estrix.commons.Result;
import pl.estrix.commons.SuccessResult;
import pl.estrix.domain.user.request.GetUserDetailsRequest;
import pl.estrix.domain.user.response.GetUserDetailsResult;
import pl.estrix.domain.user.response.GetUserResult;
import pl.estrix.service.UserService;

/**
 * Created by Kamil on 30-07-2020.
 */
@RestController
public class UserController implements UserGateway {

	@Autowired
	private UserService userService;

	@Override
	public Result<GetUserResult> getUsers() {
		GetUserResult result = new GetUserResult();
		result.setUsers(userService.getUsers());

		return new SuccessResult(result);
	}

	@Override
	public Result<GetUserDetailsResult> getUserDetails(@RequestBody GetUserDetailsRequest request) {
		return new SuccessResult(GetUserDetailsResult
				.builder()
				.user(userService.getDetails())
				.build());
	}
}
