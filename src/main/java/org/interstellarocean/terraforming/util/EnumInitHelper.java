/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.util;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.EnumSet.allOf;
import static java.util.EnumSet.complementOf;
import static java.util.EnumSet.copyOf;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import org.interstellarocean.terraforming.util.EnumInitUtil.SafeMapFrom;
import org.interstellarocean.terraforming.util.EnumInitUtil.SafeMapStore;

/**
 * @see EnumInitUtil
 *
 * @author Dariusz Wakuliński
 */
class EnumInitHelper {

	Supplier<AssertionError> nullMappingError(Enum<?> element) {
		return () -> {
			throw new AssertionError(format("Null mapping for %s", element));
		};
	}

	<E extends Enum<E>, M> EnumMap<E, M> assertAllMapped(EnumMap<E, M> mappings) {
		ofNullable(mappings)
				.orElseThrow(nullArgument("mappings"));
		Set<E> keySet = mappings.keySet();
		assertNonEmpty(keySet, "(see stack trace for type)");
		Class<E> keyType = keySet.iterator().next().getDeclaringClass();
		if (keySet.containsAll(allOf(keyType))) {
			return mappings;
		}
		throw new AssertionError(format("Missing mapping(s) for %s", complementOf(copyOf(keySet))));
	}

	<E extends Enum<E>> SafeMapFrom<E> safeMap(E element) {
		ofNullable(element)
				.orElseThrow(nullArgument("element"));
		return new SafeMapFrom<E>() {

			@Override
			public <M> SafeMapStore<E, M> from(Collection<M> mappings) {
				ofNullable(mappings)
						.orElseThrow(nullMappingError(element));
				return new SafeMapStore<E, M>() {

					@Override
					public E withStore(Map<M, E> map) {
						ofNullable(map)
								.orElseThrow(nullMappingError(element));
						return safeMap(element, mappings, map);
					}

					@Override
					public SafeMapStore<E, M> includeSelf(Function<E, M> transformation) {
						ofNullable(transformation)
								.orElseThrow(nullArgument("transformation"));
						M self = transformation.apply(element);
						ofNullable(self)
								.orElseThrow(nullMappingError(element));
						return from(join(self, mappings));
					}

					private Collection<M> join(M self, Collection<M> mappings) {
						return concat(of(self), mappings.stream()).collect(toList());
					}

				};
			}

			@Override
			@SuppressWarnings("unchecked") // Safe, read only. @SafeVarargs requires final that causes mocking problems for tests
			public <M> SafeMapStore<E, M> from(M... mappings) {
				ofNullable(mappings)
						.orElseThrow(nullMappingError(element)); // only for explicit null[], wicked!
				return from(asList(mappings));
			}

		};
	}

	private Supplier<AssertionError> nullArgument(String argumentName) {
		return () -> {
			throw new AssertionError(format("Invalid use: null %s argument", argumentName));
		};
	}

	private <E extends Enum<E>, M> E safeMap(E element, Collection<M> mappings, Map<M, E> map) {
		assertNonEmpty(mappings, element);
		mappings.forEach(mapping -> {
			E former = map.put(
					ofNullable(mapping)
							.orElseThrow(nullMappingError(element)),
					element);
			assertUniqueMapped(mapping, former);
		});
		return element;
	}

	private void assertNonEmpty(Collection<?> mappings, Object element) {
		if (!mappings.isEmpty()) {
			assertNonEmptyRequirementNotWaived(element, mappings);
			return;
		}
		if (getNonEmptyRequirementWaiver(element).isPresent()) {
			return;
		}
		throw new AssertionError(format("Missing all mappings for %s", element));
	}

	private void assertNonEmptyRequirementNotWaived(Object element, Collection<?> mappings) {
		if (!getNonEmptyRequirementWaiver(element).isPresent()) {
			return;
		}
		throw new AssertionError(format("Found non-empty mappings %2$s for @Unmapped %s", element, mappings));
	}

	private Optional<Unmapped> getNonEmptyRequirementWaiver(Object element) {
		if (!(element instanceof Enum)) {
			return empty();
		}
		Unmapped waiverAnnotation = tryToGetField((Enum<?>) element).getAnnotation(Unmapped.class);
		return ofNullable(waiverAnnotation);
	}

	private Field tryToGetField(Enum<?> element) {
		try {
			return element.getDeclaringClass().getDeclaredField(element.name());
		} catch (NoSuchFieldException | SecurityException exception) {
			throw new AssertionError(format("Error examining element: %s", element), exception); // impossible
		}
	}

	private void assertUniqueMapped(Object element, Enum<?> former) {
		if (former == null) {
			return;
		}
		throw new AssertionError(format("Duplicate mapping for %s, was: %s", element, former));
	}

}
