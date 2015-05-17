/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.lenient.safety;

import static java.util.Optional.ofNullable;
import static org.interstellarocean.terraforming.util.EnumInitUtil.safeMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Dariusz Wakuliński
 */
public enum StatusExplodeForTransformToDuplicate {

	NEW		(123),
	RUNNING	(234),
	ADVANCED(345),
	FINISHED(456),
	X_TRSF_DUPLCT((int) 'A'),
	;

	private static class Lenient {
		private static final Map<Integer, StatusExplodeForTransformToDuplicate> names = new HashMap<>();
	}

	private StatusExplodeForTransformToDuplicate(Integer... numericalForm) {
		safeMap(this).from(numericalForm).includeSelf(e -> (int) e.name().charAt(0)).withStore(Lenient.names);
	}

	public static Optional<StatusExplodeForTransformToDuplicate> lenientValueOf(Integer numericalForm) {
		return ofNullable(Lenient.names.get(numericalForm));
	}

}
