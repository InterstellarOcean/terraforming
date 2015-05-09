/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.model.refl.selma;

/**
 * @author Dariusz Wakuliński
 */
public interface SelmaModelTransformer<SOURCE, DESTINATION> {

	DESTINATION transform(SOURCE source, DESTINATION destination);

}
