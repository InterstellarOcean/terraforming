/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reflection;

import java.util.Collection;

/**
 * @author Dariusz Wakuliński
 */
public interface ReflectionModelTransformerProvider {

	boolean provides(Class<?> library);

	<SOURCE, DESTINATION> Builder<SOURCE, DESTINATION> builder();

	interface Builder<SOURCE, DESTINATION> {

		To<SOURCE, DESTINATION> from(Class<SOURCE> sourceType);

		interface To<SOURCE, DESTINATION> {

			ExcludeFields<SOURCE, DESTINATION> to(Class<DESTINATION> destinationType);

		}

		interface ExcludeFields<SOURCE, DESTINATION> {

			Build<SOURCE, DESTINATION> excludeFields(Collection<String> excludeFieldNames);

		}

		interface Build<SOURCE, DESTINATION> {

			ReflectionModelTransformer<SOURCE, DESTINATION> build();

		}

	}

}
