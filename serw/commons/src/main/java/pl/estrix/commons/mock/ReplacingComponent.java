package pl.estrix.commons.mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for beans that may replace another bean (force Spring to not to
 * register them).
 * Please see {@link ReplaceableComponent}.
 * Created by Tomasz Sokół on 2015-11-24.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReplacingComponent {
	/**
	 * Class of bean that will be replaced.
	 *
	 * @return class of replaceable component
	 */
	Class clazz();
}
