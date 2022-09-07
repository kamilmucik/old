package pl.estrix.domain.user.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.domain.user.model.UserDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kamil on 30-07-2020.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class GetUserResult implements Serializable {

	private List<UserDto> users;
}
