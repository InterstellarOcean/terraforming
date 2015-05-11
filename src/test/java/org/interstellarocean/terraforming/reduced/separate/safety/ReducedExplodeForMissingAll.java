/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reduced.separate.safety;

import static org.interstellarocean.terraforming.util.EnumInitUtil.assertAllMapped;
import static org.interstellarocean.terraforming.util.EnumInitUtil.safeMap;

import java.util.EnumMap;
import java.util.EnumSet;
import org.interstellarocean.terraforming.reduced.separate.Status;

/**
 * @author Dariusz Wakuliński
 */
public enum ReducedExplodeForMissingAll {

	// X_MISSING_ALL(),
	;

	private static class ReducedMap {
		private static final EnumMap<Status, ReducedExplodeForMissingAll> values = new EnumMap<>(Status.class);
	}

	static {
		assertAllMapped(ReducedMap.values.keySet());
	}

	private ReducedExplodeForMissingAll(EnumSet<Status> statuses) {
		safeMap(this).from(statuses).withStore(ReducedMap.values);
	}

	public static ReducedExplodeForMissingAll valueOf(Status status) {
		return ReducedMap.values.get(status);
	}

}
