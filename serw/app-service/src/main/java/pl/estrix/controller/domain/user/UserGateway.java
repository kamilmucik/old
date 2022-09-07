package pl.estrix.controller.domain.user;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.estrix.commons.Result;
import pl.estrix.domain.user.request.GetUserDetailsRequest;
import pl.estrix.domain.user.response.GetUserDetailsResult;
import pl.estrix.domain.user.response.GetUserResult;

/**
 * Created by Kamil on 30-07-2020.
 */
@RequestMapping(value = "/api/v1/secure/user")
public interface UserGateway {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	Result<GetUserResult> getUsers();

	@RequestMapping(value = "/details", method = RequestMethod.POST)
	Result<GetUserDetailsResult> getUserDetails(@RequestBody GetUserDetailsRequest request);
}
