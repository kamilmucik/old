package pl.estrix.commons.annotation;

import org.codehaus.jackson.annotate.JacksonAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Kamil on 30-07-2020.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@JacksonAnnotation
public @interface RemapId {

	String AUTHORIZATION_ORDER_ID_REMAP_NAME = "authOrderId";

	int MAX_LENGHT = 800;

	String value();

	String keyValue() default "";

	boolean useSalt() default true;

	String[] supportedValues() default {};

}
