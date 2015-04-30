/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.EnumSet.allOf;
import static java.util.EnumSet.complementOf;
import static java.util.EnumSet.copyOf;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * {@link Enum}s safe transforming utility.
 *
 * @author Dariusz Wakuliński
 */
public class EnumInitUtils {

	// static utility pattern - instantiation and extension is forbidden
	private EnumInitUtils() {
	}

	/**
	 * {@link AssertionError} supplier supporting mapping null attempts.
	 *
	 * @param element The enum instance that is attempted to map with null
	 * @return {@link Supplier} of {@link AssertionError} that throws when called
	 */
	public static Supplier<AssertionError> nullMapped(Enum<?> element) {
		return () -> {
			throw new AssertionError(format("Null mapping for %s", element));
		};
	}

	/**
	 * Asserts that all enum instances has been mapped, and none has been missed.
	 * Verifies provided map and throws {@link AssertionError}s when failed.
	 *
	 * @param mappings {@link EnumMap} to verify
	 * @param <E> Enum type
	 */
	public static <E extends Enum<E>> void assertAllMapped(EnumMap<E, ?> mappings) {
		ofNullable(mappings)
				.orElseThrow(nullArgument("mappings"));
		Set<E> keySet = mappings.keySet();
		assertNonEmpty(keySet, "(see stack trace for type)");
		Class<E> keyType = keySet.iterator().next().getDeclaringClass();
		if (keySet.containsAll(allOf(keyType))) {
			return;
		}
		throw new AssertionError(format("Missing mapping(s) for %s", complementOf(copyOf(keySet))));
	}

	/**
	 * Allows defining mapping for an enum instance in a safe way.
	 *
	 * @param element Enum instance to map
	 * @param <E> Enum type
	 * @return Functional interface {@link SafeMapFrom} with mapping operations
	 */
	public static <E extends Enum<E>> SafeMapFrom<E> safeMap(E element) {
		ofNullable(element)
				.orElseThrow(nullArgument("element"));
		return new SafeMapFrom<E>() {

			@Override
			public <M> SafeMapStore<E, M> from(Collection<M> mappings) {
				ofNullable(mappings)
						.orElseThrow(nullMapped(element));
				return new SafeMapStore<E, M>() {

					@Override
					public E withStore(Map<M, E> map) {
						ofNullable(map)
								.orElseThrow(nullMapped(element));
						return safeMap(element, mappings, map);
					}

					@Override
					public SafeMapStore<E, M> includeSelf(Function<E, M> transformation) {
						ofNullable(transformation)
								.orElseThrow(nullArgument("transformation"));
						M self = transformation.apply(element);
						ofNullable(self)
								.orElseThrow(nullMapped(element));
						return safeMap(element).from(join(self, mappings));
					}

					private Collection<M> join(M self, Collection<M> mappings) {
						return concat(of(self), mappings.stream()).collect(toList());
					}

				};
			}

			@Override
			@SafeVarargs
			public final <M> SafeMapStore<E, M> from(M... mappings) {
				ofNullable(mappings)
						.orElseThrow(nullMapped(element)); // only for explicit null[], wicked!
				return from(asList(mappings));
			}

		};
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
		 * @param mappings Elements to be mapped to the enum.
		 * @param <M> Mapping elements type
		 * @return Functional interface {@link SafeMapStore} with mappings storing operations
		 */
		// Note the compiler warning; 1.8.0_45 javac don't see the annotation
		<M> SafeMapStore<E, M> from(@SuppressWarnings("unchecked") M... mappings);

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

	private static Supplier<AssertionError> nullArgument(String argumentName) {
		return () -> {
			throw new AssertionError(format("Invalid use: null %s argument", argumentName));
		};
	}

	private static <E extends Enum<E>, M> E safeMap(E element, Collection<M> mappings, Map<M, E> map) {
		assertNonEmpty(mappings, element);
		mappings.forEach(mapping -> {
			E former = map.put(
					ofNullable(mapping)
							.orElseThrow(nullMapped(element)),
					element);
			assertUniqueMapped(mapping, former);
		});
		return element;
	}

	private static void assertNonEmpty(Collection<?> mappings, Object element) {
		if (!mappings.isEmpty()) {
			return;
		}
		throw new AssertionError(format("Missing all mappings for %s", element));
	}

	private static void assertUniqueMapped(Object element, Enum<?> former) {
		if (former == null) {
			return;
		}
		throw new AssertionError(format("Duplicate mapping for %s, was: %s", element, former));
	}

}
