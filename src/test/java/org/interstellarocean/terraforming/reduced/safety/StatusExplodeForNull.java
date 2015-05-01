/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.reduced.safety;

import static java.util.Optional.ofNullable;
import static org.interstellarocean.terraforming.reduced.safety.StatusExplodeForNull.Reduced.DYNAMIC;
import static org.interstellarocean.terraforming.reduced.safety.StatusExplodeForNull.Reduced.STATIC;
import static org.interstellarocean.terraforming.util.EnumInitUtil.nullMappingError;

/**
 * @author Dariusz Wakuliński
 */
public enum StatusExplodeForNull {

	NEW		(STATIC),
	RUNNING	(DYNAMIC),
	ADVANCED(DYNAMIC),
	FINISHED(STATIC),
	X_NULL	(null),
	;

	public enum Reduced {

		STATIC,
		DYNAMIC;

	}

	private final Reduced reduced;

	private StatusExplodeForNull(Reduced reduced) {
		this.reduced = ofNullable(reduced)
				.orElseThrow(nullMappingError(this));
	}

	public Reduced asReduced() {
		return reduced;
	}

}
