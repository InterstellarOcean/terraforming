/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.reduced.separate;

import static java.util.EnumSet.complementOf;
import static java.util.EnumSet.of;
import static org.interstellarocean.terraforming.EnumInitUtils.assertAllMapped;
import static org.interstellarocean.terraforming.EnumInitUtils.safeMap;
import static org.interstellarocean.terraforming.reduced.separate.Status.FINISHED;
import static org.interstellarocean.terraforming.reduced.separate.Status.RUNNING;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 * @author Dariusz Wakuliński
 */
public enum ReducedExplodeForMissing {

	X_MISSING(complementOf(of(RUNNING, FINISHED))),
	;

	private static class ReducedMap {
		private static final EnumMap<Status, ReducedExplodeForMissing> values = new EnumMap<>(Status.class);
	}

	static {
		assertAllMapped(ReducedMap.values);
	}

	private ReducedExplodeForMissing(EnumSet<Status> statuses) {
		safeMap(this).from(statuses).withStore(ReducedMap.values);
	}

	public static ReducedExplodeForMissing valueOf(Status status) {
		return ReducedMap.values.get(status);
	}

}
