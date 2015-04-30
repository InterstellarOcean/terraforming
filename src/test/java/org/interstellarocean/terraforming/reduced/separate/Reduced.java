/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.reduced.separate;

import static java.util.EnumSet.of;
import static java.util.EnumSet.range;
import static org.interstellarocean.terraforming.EnumInitUtils.assertAllMapped;
import static org.interstellarocean.terraforming.EnumInitUtils.safeMap;
import static org.interstellarocean.terraforming.reduced.separate.Status.ADVANCED;
import static org.interstellarocean.terraforming.reduced.separate.Status.FINISHED;
import static org.interstellarocean.terraforming.reduced.separate.Status.NEW;
import static org.interstellarocean.terraforming.reduced.separate.Status.RUNNING;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 * @author Dariusz Wakuliński
 */
public enum Reduced {

	STATIC	(of(NEW, FINISHED)),
	DYNAMIC	(range(RUNNING, ADVANCED)),
	;

	private static class ReducedMap {
		private static final EnumMap<Status, Reduced> values = new EnumMap<>(Status.class);
	}

	static {
		assertAllMapped(ReducedMap.values);
	}

	private Reduced(EnumSet<Status> statuses) {
		safeMap(this).from(statuses).withStore(ReducedMap.values);
	}

	public static Reduced valueOf(Status status) {
		return ReducedMap.values.get(status);
	}

}
