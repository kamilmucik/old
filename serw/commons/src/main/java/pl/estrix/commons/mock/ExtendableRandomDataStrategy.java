package pl.estrix.commons.mock;


import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.AttributeMetadata;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.RandomDataProviderStrategyImpl;

import com.google.common.collect.Maps;

@Component
public class ExtendableRandomDataStrategy extends AbstractRandomDataProviderStrategy {

	public static final int DEFAULT_COLLECTION_SIZE = 30;

	public ExtendableRandomDataStrategy() {
		super();
		setDefaultNumberOfCollectionElements(DEFAULT_COLLECTION_SIZE);
		setMemoization(true);
	}

	private Map<ExtensionPoint, Function<AttributeMetadata, Boolean>> getBooleanExtensions = Maps.newConcurrentMap();

	private Map<ExtensionPoint, Function<AttributeMetadata, Byte>> getByteExtensions = Maps.newConcurrentMap();

	private Map<ExtensionPoint, Function<AttributeMetadata, Character>> getCharacterExtensions = Maps.newConcurrentMap();

	private Map<ExtensionPoint, Function<AttributeMetadata, Double>> getDoubleExtensions = Maps.newConcurrentMap();

	private Map<ExtensionPoint, Function<AttributeMetadata, Float>> getFloatExtensions = Maps.newConcurrentMap();

	private Map<ExtensionPoint, Function<AttributeMetadata, Integer>> getIntegerExtensions = Maps.newConcurrentMap();

	private Map<ExtensionPoint, Function<AttributeMetadata, Long>> getLongExtensions = Maps.newConcurrentMap();

	private Map<ExtensionPoint, Function<AttributeMetadata, Short>> getShortExtensions = Maps.newConcurrentMap();

	private Map<ExtensionPoint, Function<AttributeMetadata, String>> getStringValueExtensions = Maps.newConcurrentMap();



	private DataProviderStrategy fallbackStrategy = new RandomDataProviderStrategyImpl();

	private Boolean getBooleanFallback(AttributeMetadata metadata) {
		return fallbackStrategy.getBoolean(metadata);
	}

	public void registerGetBooleanExtension(ExtensionPoint strategyExtension, Function<AttributeMetadata, Boolean> extension) {
		this.getBooleanExtensions.put(strategyExtension, extension);
	}

	public void removeGetBooleanExtension(ExtensionPoint strategyExtension) {
		this.getBooleanExtensions.remove(strategyExtension);
	}

	@Override
	public Boolean getBoolean(AttributeMetadata metadata) {
		return getBooleanExtensions.getOrDefault(ExtensionPoint.convertFrom(metadata), this::getBooleanFallback).apply(metadata);
	}

	private Byte getByteFallback(AttributeMetadata metadata) {
		return fallbackStrategy.getByte(metadata);
	}

	public void registerGetByteExtension(ExtensionPoint strategyExtension, Function<AttributeMetadata, Byte> extension) {
		this.getByteExtensions.put(strategyExtension, extension);
	}

	public void removeGetByteExtension(ExtensionPoint strategyExtension) {
		this.getByteExtensions.remove(strategyExtension);
	}

	@Override
	public Byte getByte(AttributeMetadata metadata) {
		return getByteExtensions.getOrDefault(ExtensionPoint.convertFrom(metadata), this::getByteFallback).apply(metadata);
	}

	private Character getCharacterFallback(AttributeMetadata metadata) {
		return fallbackStrategy.getCharacter(metadata);
	}

	public void registerGetCharacterExtension(ExtensionPoint strategyExtension, Function<AttributeMetadata, Character> extension) {
		this.getCharacterExtensions.put(strategyExtension, extension);
	}

	public void removeGetCharacterExtension(ExtensionPoint strategyExtension) {
		this.getCharacterExtensions.remove(strategyExtension);
	}

	@Override
	public Character getCharacter(AttributeMetadata metadata) {
		return getCharacterExtensions.getOrDefault(ExtensionPoint.convertFrom(metadata), this::getCharacterFallback).apply(metadata);
	}

	private Double getDoubleFallback(AttributeMetadata metadata) {
		return fallbackStrategy.getDouble(metadata);
	}

	public void registerGetDoubleExtension(ExtensionPoint strategyExtension, Function<AttributeMetadata, Double> extension) {
		this.getDoubleExtensions.put(strategyExtension, extension);
	}

	public void removeGetDoubleExtension(ExtensionPoint strategyExtension) {
		this.getDoubleExtensions.remove(strategyExtension);
	}

	@Override
	public Double getDouble(AttributeMetadata metadata) {
		return getDoubleExtensions.getOrDefault(ExtensionPoint.convertFrom(metadata), this::getDoubleFallback).apply(metadata);
	}

	private Float getFloatFallback(AttributeMetadata metadata) {
		return fallbackStrategy.getFloat(metadata);
	}

	public void registerGetFloatExtension(ExtensionPoint strategyExtension, Function<AttributeMetadata, Float> extension) {
		this.getFloatExtensions.put(strategyExtension, extension);
	}

	public void removeGetFloatExtension(ExtensionPoint strategyExtension) {
		this.getFloatExtensions.remove(strategyExtension);
	}

	@Override
	public Float getFloat(AttributeMetadata metadata) {
		return getFloatExtensions.getOrDefault(ExtensionPoint.convertFrom(metadata), this::getFloatFallback).apply(metadata);
	}

	private Integer getIntegerFallback(AttributeMetadata metadata) {
		return fallbackStrategy.getInteger(metadata);
	}

	public void registerGetIntegerExtension(ExtensionPoint strategyExtension, Function<AttributeMetadata, Integer> extension) {
		this.getIntegerExtensions.put(strategyExtension, extension);
	}

	public void removeGetIntegerExtension(ExtensionPoint strategyExtension) {
		this.getIntegerExtensions.remove(strategyExtension);
	}

	@Override
	public Integer getInteger(AttributeMetadata metadata) {
		return getIntegerExtensions.getOrDefault(ExtensionPoint.convertFrom(metadata), this::getIntegerFallback).apply(metadata);
	}

	private Long getLongFallback(AttributeMetadata metadata) {
		return fallbackStrategy.getLong(metadata);
	}

	public void registerGetLongExtension(ExtensionPoint strategyExtension, Function<AttributeMetadata, Long> extension) {
		this.getLongExtensions.put(strategyExtension, extension);
	}

	public void removeGetLongExtension(ExtensionPoint strategyExtension) {
		this.getLongExtensions.remove(strategyExtension);
	}

	@Override
	public Long getLong(AttributeMetadata metadata) {
		return getLongExtensions.getOrDefault(ExtensionPoint.convertFrom(metadata), this::getLongFallback).apply(metadata);
	}

	private Short getShortFallback(AttributeMetadata metadata) {
		return fallbackStrategy.getShort(metadata);
	}

	public void registerGetShortExtension(ExtensionPoint strategyExtension, Function<AttributeMetadata, Short> extension) {
		this.getShortExtensions.put(strategyExtension, extension);
	}

	public void removeGetShortExtension(ExtensionPoint strategyExtension) {
		this.getShortExtensions.remove(strategyExtension);
	}

	@Override
	public Short getShort(AttributeMetadata metadata) {
		return getShortExtensions.getOrDefault(ExtensionPoint.convertFrom(metadata), this::getShortFallback).apply(metadata);
	}

	private String getStringValueFallback(AttributeMetadata metadata) {
		if(metadata.getAttributeName().equals("startDate") || metadata.getAttributeName().equals("endDate")) {
			return "" + randDateInt(1990, 2016) + "" + randDateInt(1, 12) + "" + randDateInt(1, 27);
		}
		return fallbackStrategy.getStringValue(metadata);
	}

	private String randDateInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		if(randomNum < 10) {
			return "0" + randomNum;
		}
		return "" + randomNum;
	}

	public void registerGetStringValueExtension(ExtensionPoint strategyExtension, Function<AttributeMetadata, String> extension) {
		this.getStringValueExtensions.put(strategyExtension, extension);
	}

	public void removeGetStringValueExtension(ExtensionPoint strategyExtension) {
		this.getStringValueExtensions.remove(strategyExtension);
	}

	@Override
	public String getStringValue(AttributeMetadata metadata) {
		return getStringValueExtensions.getOrDefault(ExtensionPoint.convertFrom(metadata), this::getStringValueFallback).apply(metadata);
	}

}
