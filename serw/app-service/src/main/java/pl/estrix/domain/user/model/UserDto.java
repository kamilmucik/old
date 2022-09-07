package pl.estrix.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Kamil on 30-07-2020.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {

	private Long id;
	private String name;

	private String userName;

	private String userLastname;

	private String email;

	private String password;

	private String role;

	private boolean enabled;

	private boolean locked;
}
