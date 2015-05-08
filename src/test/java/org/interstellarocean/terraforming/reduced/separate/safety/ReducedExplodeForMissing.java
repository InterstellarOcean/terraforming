/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reduced.separate.safety;

import static java.util.EnumSet.complementOf;
import static java.util.EnumSet.of;
import static org.interstellarocean.terraforming.reduced.separate.Status.FINISHED;
import static org.interstellarocean.terraforming.reduced.separate.Status.RUNNING;
import static org.interstellarocean.terraforming.util.EnumInitUtil.assertAllMapped;
import static org.interstellarocean.terraforming.util.EnumInitUtil.safeMap;

import java.util.EnumMap;
import java.util.EnumSet;
import org.interstellarocean.terraforming.reduced.separate.Status;

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
