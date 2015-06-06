/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reflection;

import java.util.Collection;

/**
 * Builder allowing creation and configuration of a {@link ReflectionModelTransformer} instance.
 *
 * @param <SOURCE> Type to transform from. Used to configure {@link ReflectionModelTransformer}.
 * @param <DESTINATION> Type to transform to. Used to configure {@link ReflectionModelTransformer}.
 *
 * @author Dariusz Wakuliński
 */
public interface ReflectionModelTransformerBuilder<SOURCE, DESTINATION> {

	/**
	 * Configures type to transform from.
	 *
	 * @see To
	 *
	 * @param sourceType Type to transform from. Used to configure {@link ReflectionModelTransformer}.
	 * @return Builder's next step fluent interface.
	 */
	ReflectionModelTransformerBuilder.To<SOURCE, DESTINATION> from(Class<SOURCE> sourceType);

	/**
	 * Builder allowing creation and configuration of a {@link ReflectionModelTransformer} instance.
	 *
	 * @param <SOURCE> Type to transform from. Used to configure {@link ReflectionModelTransformer}.
	 * @param <DESTINATION> Type to transform to. Used to configure {@link ReflectionModelTransformer}.
	 */
	interface To<SOURCE, DESTINATION> {

		/**
		 * Configures type to transform to.
		 *
		 * @see ExcludeFields
		 *
		 * @param destinationType Type to transform to. Used to configure {@link ReflectionModelTransformer}.
		 * @return Builder's next step fluent interface.
		 */
		ReflectionModelTransformerBuilder.ExcludeFields<SOURCE, DESTINATION> to(Class<DESTINATION> destinationType);

	}

	/**
	 * Builder allowing creation and configuration of a {@link ReflectionModelTransformer} instance.
	 *
	 * @param <SOURCE> Type to transform from. Used to configure {@link ReflectionModelTransformer}.
	 * @param <DESTINATION> Type to transform to. Used to configure {@link ReflectionModelTransformer}.
	 */
	interface ExcludeFields<SOURCE, DESTINATION> {

		/**
		 * Configures fields to be excluded from transformation.
		 * That should include all fields that are not supported by the given library. Excluded field transforming should be instead performed
		 * by the {@link org.interstellarocean.terraforming.ModelTransformer} calling the {@link ReflectionModelTransformer}.
		 *
		 * <p>
		 * <i>Note:</i> Never pass <code>null</code> if there are no fields to exclude, pass your favorite empty collection instead,
		 * e.g. {@link java.util.Collections#emptySet()}.
		 * </p>
		 *
		 * @see Build
		 *
		 * @param excludeFieldNames Names of the fields to exclude.
		 * @return Builder's next step fluent interface.
		 */
		ReflectionModelTransformerBuilder.Build<SOURCE, DESTINATION> excludeFields(Collection<String> excludeFieldNames);

	}

	/**
	 * Builder allowing creation and configuration of a {@link ReflectionModelTransformer} instance.
	 *
	 * @param <SOURCE> Type to transform from. Used to configure {@link ReflectionModelTransformer}.
	 * @param <DESTINATION> Type to transform to. Used to configure {@link ReflectionModelTransformer}.
	 */
	interface Build<SOURCE, DESTINATION> {

		/**
		 * Builds the configured {@link ReflectionModelTransformer}.
		 *
		 * @return Thread-safe instance of {@link ReflectionModelTransformer} ready to be used for transforming.
		 */
		ReflectionModelTransformer<SOURCE, DESTINATION> build();

	}

}
