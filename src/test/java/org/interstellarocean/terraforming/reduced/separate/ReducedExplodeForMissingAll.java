package org.interstellarocean.terraforming.reduced.separate;

import static org.interstellarocean.terraforming.EnumInitUtils.assertAllMapped;
import static org.interstellarocean.terraforming.EnumInitUtils.safeMap;

import java.util.EnumMap;
import java.util.EnumSet;

public enum ReducedExplodeForMissingAll {

	// X_MISSING_ALL(),
	;

	private static class ReducedMap {
		private static final EnumMap<Status, ReducedExplodeForMissingAll> values = new EnumMap<>(Status.class);
	}

	static {
		assertAllMapped(ReducedMap.values);
	}

	private ReducedExplodeForMissingAll(EnumSet<Status> statuses) {
		safeMap(this).from(statuses).withStore(ReducedMap.values);
	}

	public static ReducedExplodeForMissingAll valueOf(Status status) {
		return ReducedMap.values.get(status);
	}

}
