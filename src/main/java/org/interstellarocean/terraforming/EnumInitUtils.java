/**
 * <a href="https://www.gnu.org/licenses/gpl-3.0.html">https://www.gnu.org/licenses/gpl-3.0.html</a>
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
 * @author <a href="mailto:dariuswak@gmail.com">Dariusz Wakuliński</a>
 */
public class EnumInitUtils {

	// static utility pattern - instantiation and extension is forbidden
	private EnumInitUtils() {
	}

	/**
	 * Note this metod's type parameter is not constrained to allow mappings also from other types, e.g. from {@code String}.
	 * @param element
	 * @return
	 */
	// Note method can't be parameterized with the usual <T>, as 1.8.0_45 javac complains about 'unreported exception'
	public static Supplier<AssertionError> nullMapped(Enum<?> element) {
		return () -> {
			throw new AssertionError(format("Null mapping for %s", element));
		};
	}

	/**
	 *
	 * @param mappings
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
	 *
	 * @param element
	 * @return
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
	 *
	 * @param <E>
	 */
	public interface SafeMapFrom<E extends Enum<E>> {

		/**
		 *
		 * @param mappings
		 * @return
		 */
		<M> SafeMapStore<E, M> from(Collection<M> mappings);

		/**
		 *
		 * @param mappings
		 * @return
		 */
		// Note the compiler warning; 1.8.0_45 javac don't see the annotation
		<M> SafeMapStore<E, M> from(@SuppressWarnings("unchecked") M... mappings);

	}

	/**
	 *
	 * @param <E>
	 * @param <M>
	 */
	public interface SafeMapStore<E extends Enum<E>, M> {

		/**
		 *
		 * @param map
		 * @return
		 */
		E withStore(Map<M, E> map);

		/**
		 *
		 * @param transformation
		 * @return
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
