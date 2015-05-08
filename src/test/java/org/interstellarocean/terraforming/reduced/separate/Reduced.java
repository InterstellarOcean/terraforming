/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reduced.separate;

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

/**
 * Example of use of enum transforming to reduce number of instances.
 * In this example enums may be defined in separated tiers, only one-directional dependency is present.
 *
 * <p><b>NOTE</b> An unit test ({@link SeparateReducedTest#shouldReducedInitializationSucceed()}) is required to guarantee safety, i.e. an error-free runtime.
 *
 * @see Status
 * @see SeparateReducedTest
 *
 * @author Dariusz Wakuliński
 */
public enum Reduced {

	STATIC	(of(NEW, FINISHED)),
	DYNAMIC	(range(RUNNING, ADVANCED)),
	;

	// Workaround for "illegal reference to static field from initializer" compilation error in constructor
	private static class ReducedMap {
		private static final EnumMap<Status, Reduced> values = new EnumMap<>(Status.class);
	}

	static {
		// Required to guarantee safety
		assertAllMapped(ReducedMap.values);
	}

	private Reduced(EnumSet<Status> statuses) {
		safeMap(this).from(statuses).withStore(ReducedMap.values);
	}

	public static Reduced valueOf(Status status) {
		return ReducedMap.values.get(status);
	}

}
