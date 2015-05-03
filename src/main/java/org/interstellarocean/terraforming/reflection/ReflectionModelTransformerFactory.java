/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reflection;

/**
 * @author Dariusz Wakuliński
 */
public interface ReflectionModelTransformerFactory {

	ReflectionModelTransformerProvider getProvider(Class<?> library);

}
