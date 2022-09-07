package pl.estrix.domain.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import pl.estrix.commons.annotation.RemapId;

/**
 * Created by Kamil on 30-07-2020.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDetailsRequest {

	@RemapId(value = "productId", useSalt = false)
	@NotEmpty
	@Size(max = RemapId.MAX_LENGHT)
	private String userId;
}
