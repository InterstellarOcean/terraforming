package org.interstellarocean.terraforming.reduced;

import static java.util.Optional.ofNullable;
import static org.interstellarocean.terraforming.EnumInitUtils.nullMapped;
import static org.interstellarocean.terraforming.reduced.StatusExplodeForNull.Reduced.DYNAMIC;
import static org.interstellarocean.terraforming.reduced.StatusExplodeForNull.Reduced.STATIC;

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
				.orElseThrow(nullMapped(this));
	}

	public Reduced asReduced() {
		return reduced;
	}

}
