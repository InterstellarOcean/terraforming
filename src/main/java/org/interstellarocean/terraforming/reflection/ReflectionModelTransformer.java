/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reflection;

/**
 * @author Dariusz Wakuliński
 */
public interface ReflectionModelTransformer<SOURCE, DESTINATION> {

	To<DESTINATION> from(SOURCE source);

	interface To<DESTINATION> {

		DESTINATION to(DESTINATION destination);

	}

}
