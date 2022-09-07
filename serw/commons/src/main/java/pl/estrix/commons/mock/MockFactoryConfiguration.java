package pl.estrix.commons.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Created by Kamil on 30-07-2020.
 */
@Configuration
@ComponentScan
public class MockFactoryConfiguration {

	@Bean
	public PodamFactory mockFactory(ExtendableRandomDataStrategy strategy){
		return new PodamFactoryImpl(strategy);
	}
}
