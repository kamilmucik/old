package pl.estrix.commons.mock;

import org.springframework.beans.factory.annotation.Autowired;
import uk.co.jemos.podam.api.PodamFactory;

public abstract class BaseMockService {

	@Autowired
	protected PodamFactory mockFactory;

}
