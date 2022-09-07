package pl.estrix.commons.mock;

/**
 * Annotation for component that might be replaced during bean initialization.
 * When Spring is about to register bean of this class it will check first
 * whether in package defined in packageName exists class
 * annotated with {@link pl.eo.it4em.configuration.condition.ReplacingComponent}
 * with parameter clazz assiget with this class name.
 * It such bean will be found Spring will not create bean.
 * Created by Tomasz Sokół on 2015-11-23.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Conditional({ OnSubstituteNotExistsCondition.class, OnSubstituteMethodNotExistsCondition.class, OnForceApiMockCondition.class })
public @interface ReplaceableComponent {
	/**
	 * Package name for scanning for components
	 *
	 * @return package name
	 */
	String packageName() default "pl.estrix";
}
