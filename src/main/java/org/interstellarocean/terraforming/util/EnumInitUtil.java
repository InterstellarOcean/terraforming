/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.util;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * {@link Enum}s safe transforming utility.
 *
 * <p><b>NOTE</b> All methods throws {@link AssertionError} when unsafe operation is detected.
 *
 * @author Dariusz Wakuliński
 */
public class EnumInitUtil {

	private static final EnumInitHelper IMPLEMENTATION = new EnumInitHelper();

	// static utility pattern - instantiation and extension is forbidden
	private EnumInitUtil() {
	}

	/**
	 * {@link AssertionError} supplier supporting mapping null attempts.
	 *
	 * @param element The enum instance that is attempted to map with null
	 * @return {@link Supplier} of {@link AssertionError} that throws when called
	 */
	public static Supplier<AssertionError> nullMappingError(Enum<?> element) {
		return IMPLEMENTATION.nullMappingError(element);
	}

	/**
	 * Asserts that all enum instances has been mapped, and none has been missed.
	 * Verifies provided map and throws {@link AssertionError}s when failed.
	 *
	 * @param mappings {@link EnumMap} to verify
	 * @param <E> Enum type
	 * @param <M> Mappings type
	 * @return Passed argument, unmodified
	 */
	public static <E extends Enum<E>, M> EnumMap<E, M> assertAllMapped(EnumMap<E, M> mappings) {
		return IMPLEMENTATION.assertAllMapped(mappings);
	}

	/**
	 * Allows defining mapping for an enum instance in a safe way.
	 *
	 * @param element Enum instance to map
	 * @param <E> Enum type
	 * @return Functional interface {@link SafeMapFrom} with mapping operations
	 */
	public static <E extends Enum<E>> SafeMapFrom<E> safeMap(E element) {
		return IMPLEMENTATION.safeMap(element);
	}

	/**
	 * Enum mapping operations.
	 *
	 * @param <E> Enum type to map
	 */
	public interface SafeMapFrom<E extends Enum<E>> {

		/**
		 * Maps all provided elements to the enum under mapping.
		 *
		 * @param mappings Elements to be mapped to the enum
		 * @param <M> Mapping elements type
		 * @return Functional interface {@link SafeMapStore} with mappings storing operations
		 */
		<M> SafeMapStore<E, M> from(Collection<M> mappings);

		/**
		 * Maps all provided elements to the enum under mapping.
		 *
		 * <p><b>NOTE</b> Unchecked warning suppression is required on the interface, {@link SafeVarargs} can be declared on implementation only.
		 *
		 * @param mappings Elements to be mapped to the enum.
		 * @param <M> Mapping elements type
		 * @return Functional interface {@link SafeMapStore} with mappings storing operations
		 */
		@SuppressWarnings("unchecked") // see javadoc above
		<M> SafeMapStore<E, M> from(M... mappings);

	}

	/**
	 * Mappings storing operations.
	 *
	 * @param <E> Enum type mapped
	 * @param <M> Mapping elements type
	 */
	public interface SafeMapStore<E extends Enum<E>, M> {

		/**
		 * Stores enum instance mappings to provided map.
		 *
		 * @param map Map to store mapping to
		 * @return Enum instance that has been mapped
		 */
		E withStore(Map<M, E> map);

		/**
		 * Allows to include mapped enum's self in the mappings.
		 * Provided enum transformation will be used to produce enum instance's form that will be added to the mappings.
		 *
		 * @param transformation Enum transformation to mappings type
		 * @return Functional interface {@link SafeMapStore} with mappings storing operations
		 */
		SafeMapStore<E, M> includeSelf(Function<E, M> transformation);

	}

}
