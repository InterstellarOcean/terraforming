/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reflection;

/**
 * Implementations are used to perform actual transforming.
 * Instances are created and configured using {@link ReflectionModelTransformerBuilder} and are thread-safe.
 * <p>
 * <i>Note</i> transforming is performed in-place, with side-effect of modification of the destination object.
 * </p>
 * <p>
 * <i>Note</i> destination instance need to be provided by the call site. That is considered a good practice, eliminating need for object factories
 * and eliminating problems with multiple (or no default) constructors.
 * </p>
 *
 * <h3>Example</h3>
 *
 * <blockquote><pre>
 * public class FooToDtoModelTransformer implements ToDtoModelTransformer&lt;FooDomain, FooDto&gt; {
 *
 * 	private ReflectionModelTransformer&lt;FooDomain, FooDto&gt; toDtoReflectionModelTransformer;
 *
 * 	...
 *
 * 	&#64;Override
 * 	public FooDto toDto(FooDomain domain) {
 * 		FooDto dto = toDtoReflectionModelTransformer
 * 				.from(domain)
 * 				.to(new FooDto());
 * 		return dto;
 * 	}
 *
 * }
 * </pre></blockquote>
 *
 * @param <SOURCE> Type of object to transform from.
 * @param <DESTINATION> Type of object to transform to.
 *
 * @see org.interstellarocean.terraforming.reflection
 * @see ReflectionModelTransformerFactory
 * @see ReflectionModelTransformerProvider
 *
 * @author Dariusz Wakuliński
 */
public interface ReflectionModelTransformer<SOURCE, DESTINATION> {

	/**
	 * Specifies source object to transform from.
	 *
	 * @see To
	 *
	 * @param source Object to transform from.
	 * @return Transformer's next step fluent interface.
	 */
	To<DESTINATION> from(SOURCE source);

	/**
	 * Transformer's transforming step fluent interface.
	 *
	 * @param <DESTINATION> Type of object to transform to.
	 */
	interface To<DESTINATION> {

		/**
		 * Specifies destination object to transform to and performs actual transformation.
		 *
		 * @param destination Object to transform to.
		 * @return Transformed destination object.
		 */
		DESTINATION to(DESTINATION destination);

	}

}
