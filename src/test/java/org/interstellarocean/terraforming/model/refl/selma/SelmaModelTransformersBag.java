/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.model.refl.selma;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Uses THC and STT patterns
 *
 * @see http://gafter.blogspot.com/2006/12/super-type-tokens.html
 *
 * @author Dariusz Wakuliński
 */
public class SelmaModelTransformersBag {

	private final Table<Class<?>, Class<?>, Class<? extends SelmaModelTransformer<?, ?>>> bag = HashBasedTable.create();

	public void put(Class<? extends SelmaModelTransformer<?, ?>> type) {
		List<Class<?>> typeArguments = getTypeArguments(type);
		bag.put(typeArguments.get(0), typeArguments.get(1), type);
	}

	@SuppressWarnings("unchecked") // Safe, the THC pattern
	public <SOURCE, DESTINATION> Class<SelmaModelTransformer<SOURCE, DESTINATION>> get(Class<SOURCE> sourceType, Class<DESTINATION> destinationType) {
		return (Class<SelmaModelTransformer<SOURCE, DESTINATION>>) bag.get(sourceType, destinationType);
	}

	private List<Class<?>> getTypeArguments(Class<? extends SelmaModelTransformer<?, ?>> type) {
		ParameterizedType reflectionModelTransformerType =
				stream(type.getGenericInterfaces())
				.filter(interfaceType -> interfaceType instanceof ParameterizedType)
				.map(interfaceType -> (ParameterizedType) interfaceType)
				.filter(interfaceType -> SelmaModelTransformer.class.isAssignableFrom(toClass(interfaceType.getRawType())))
				.findAny().get(); // interface can be implemented only once, anyway
		return
				stream(reflectionModelTransformerType.getActualTypeArguments())
				.map(argType -> toClass(argType))
				.collect(toList());
	}

	private Class<?> toClass(Type type) {
		try {
			return Class.forName(type.getTypeName());
		} catch (ClassNotFoundException e) {
			throw new AssertionError(e); // never happens
		}
	}

}
