/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.lenient.empty;

import static java.util.Optional.ofNullable;
import static org.interstellarocean.terraforming.util.EnumInitUtil.safeMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.fest.util.VisibleForTesting;
import org.interstellarocean.terraforming.util.Unmapped;

/**
 * Example of use of the empty mapping waiver.
 *
 * @see LenientUnmappedTest
 * @see Unmapped
 *
 * @author Dariusz Wakuliński
 */
public enum Status {

	NEW		("BEGIN", "START"),
	RUNNING	("IN PROGRESS",	"IN-PROGRESS", "IN_PROGRESS", "STARTED"),
	ADVANCED("ALMOST"),
	FINISHED("END",	"DONE"),
	@Unmapped NONE,
	;

	// Workaround for "illegal reference to static field from initializer" compilation error in constructor
	private static class Lenient {
		private static final Map<String, Status> names = new HashMap<>();
	}

	private Status(String... lenientNames) {
		safeMap(this).from(lenientNames).withStore(Lenient.names);
	}

	public static Optional<Status> lenientValueOf(String lenientName) {
		return ofNullable(Lenient.names.get(normalize(lenientName)));
	}

	private static String normalize(String lenientName) {
		if (lenientName == null) {
			return null;
		}
		lenientName = lenientName.trim().toUpperCase();
		if (lenientName.isEmpty()) {
			return null;
		}
		return lenientName;
	}

	@VisibleForTesting
	static Collection<String> peekMapingKeys() {
		return Lenient.names.keySet();
	}

}
