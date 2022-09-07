package pl.estrix.commons.mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for mock beans that may replace another bean (force Spring to not to
 * register them).
 * Please see {@link ReplaceableComponent}.
 * Created by Paweł Kropidłowski on 2016-07-19.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReplacingMockComponent {
	/**
	 * Class of bean that will be replaced.
	 *
	 * @return class of replaceable component
	 */
	Class clazz();
}
