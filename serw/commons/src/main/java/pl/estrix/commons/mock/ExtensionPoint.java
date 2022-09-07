package pl.estrix.commons.mock;

import uk.co.jemos.podam.api.AttributeMetadata;

import javax.annotation.concurrent.Immutable;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Builder
@EqualsAndHashCode
@Immutable
public class ExtensionPoint {

	/**
	 * Convert attribute name.
	 *
	 * @param metadata
	 *        - metadata
	 * @return - extension point
	 */
	public static ExtensionPoint convertFrom(@NonNull AttributeMetadata metadata) {
		// @formatter:off
		return ExtensionPoint.builder().pojoClass(metadata.getPojoClass()).attributeName(metadata.getAttributeName()).build();
		// @formatter:on
	}

	private final Class<?> pojoClass;

	private final String attributeName;

}
