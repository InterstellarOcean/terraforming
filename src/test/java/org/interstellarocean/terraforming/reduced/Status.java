/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.reduced;

import static java.util.Optional.ofNullable;
import static org.interstellarocean.terraforming.EnumInitUtils.nullMapped;
import static org.interstellarocean.terraforming.reduced.Status.Reduced.DYNAMIC;
import static org.interstellarocean.terraforming.reduced.Status.Reduced.STATIC;

/**
 * @author Dariusz Wakuliński
 */
public enum Status {

	NEW		(STATIC),
	RUNNING	(DYNAMIC),
	ADVANCED(DYNAMIC),
	FINISHED(STATIC),
	;

	public enum Reduced {

		STATIC,
		DYNAMIC;

	}

	private final Reduced reduced;

	private Status(Reduced reduced) {
		this.reduced = ofNullable(reduced)
				.orElseThrow(nullMapped(this));
	}

	public Reduced asReduced() {
		return reduced;
	}

}
