package pl.estrix.commons.mock;

import com.google.common.collect.Sets;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.MethodMetadataReadingVisitor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Condition for replacing beans when substitution candidate exists.
 * Replacing component and replacement candidate are defined by annotations
 * {@link ReplacingComponent} {@link ReplaceableComponent}
 * Created by Tomasz Sokół on 2015-11-23.
 */
@NoArgsConstructor
public class OnSubstituteMethodNotExistsCondition extends SpringBootCondition {

	private final Log logger = LogFactory.getLog(this.getClass());

	private static volatile Set<Method> replacingComponentAnnotatedMethods;

	@Override
	public ConditionOutcome getMatchOutcome(final ConditionContext conditionContext, final AnnotatedTypeMetadata annotatedTypeMetadata) {
		if (!(annotatedTypeMetadata instanceof MethodMetadataReadingVisitor)){
			return ConditionOutcome.match("No replacement found. Bean will be created");
		}
		synchronized (OnSubstituteNotExistsCondition.class) {
			if (replacingComponentAnnotatedMethods == null) {
				replacingComponentAnnotatedMethods = new HashSet<>();
				Reflections r = new Reflections(getPackage(annotatedTypeMetadata), new MethodAnnotationsScanner());
				replacingComponentAnnotatedMethods.addAll(r.getMethodsAnnotatedWith(ReplacingComponent.class));
			}
		}
		final Optional res = replacingComponentAnnotatedMethods.stream().filter(c -> {
			Annotation annotation = c.getAnnotation(ReplacingComponent.class);
			ReplacingComponent rc = (ReplacingComponent) annotation;
			Class rcClazz = rc.clazz();
			return methodName(annotatedTypeMetadata).equals(rcClazz.getName() + "#" + c.getName());
		}).findAny();
		if (res.isPresent()) {
			logger.info(String.format("Replacement found for %s. Bean will not be created", methodName(annotatedTypeMetadata)));
			return ConditionOutcome.noMatch("Replacement found. Bean will not be created");
		}
		logger.info(String.format("No replacement found for %s. Bean will be created", methodName(annotatedTypeMetadata)));
		return ConditionOutcome.match("No replacement found. Bean will be created");
	}

	private String methodName(final AnnotatedTypeMetadata metadata) {
		MethodMetadata methodMetadata = (MethodMetadata)metadata;
		return methodMetadata.getDeclaringClassName() + "#" + methodMetadata.getMethodName();
	}

	private String getPackage(final AnnotatedTypeMetadata metadata) {
		return (String) metadata.getAllAnnotationAttributes(ReplaceableComponent.class.getName()).get("packageName").get(0);
	}
}

