/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.reduced.separate.safety;

import static java.util.EnumSet.noneOf;
import static java.util.EnumSet.of;
import static java.util.EnumSet.range;
import static org.interstellarocean.terraforming.reduced.separate.Status.ADVANCED;
import static org.interstellarocean.terraforming.reduced.separate.Status.FINISHED;
import static org.interstellarocean.terraforming.reduced.separate.Status.NEW;
import static org.interstellarocean.terraforming.reduced.separate.Status.RUNNING;
import static org.interstellarocean.terraforming.util.EnumInitUtil.assertAllMapped;
import static org.interstellarocean.terraforming.util.EnumInitUtil.safeMap;

import java.util.EnumMap;
import java.util.EnumSet;
import org.interstellarocean.terraforming.reduced.separate.Status;

/**
 * @author Dariusz Wakuliński
 */
public enum ReducedExplodeForEmpty {

	STATIC	(of(NEW, FINISHED)),
	DYNAMIC	(range(RUNNING, ADVANCED)),
	X_EMPTY	(noneOf(Status.class)),
	;

	private static class ReducedMap {
		private static final EnumMap<Status, ReducedExplodeForEmpty> values = new EnumMap<>(Status.class);
	}

	static {
		assertAllMapped(ReducedMap.values);
	}

	private ReducedExplodeForEmpty(EnumSet<Status> statuses) {
		safeMap(this).from(statuses).withStore(ReducedMap.values);
	}

	public static ReducedExplodeForEmpty valueOf(Status status) {
		return ReducedMap.values.get(status);
	}

}
