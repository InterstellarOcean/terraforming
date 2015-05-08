/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.lenient.safety;

import static java.util.Optional.ofNullable;
import static org.interstellarocean.terraforming.util.EnumInitUtil.safeMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Dariusz Wakuliński
 */
public enum StatusExplodeForNone {

	NEW		("BEGIN", "START"),
	RUNNING	("IN PROGRESS",	"IN-PROGRESS", "IN_PROGRESS", "STARTED"),
	ADVANCED("ALMOST"),
	FINISHED("END",	"DONE"),
	X_NONE	(),
	;

	private static class Lenient {
		private static final Map<String, StatusExplodeForNone> names = new HashMap<>();
	}

	private StatusExplodeForNone(String... lenientNames) {
		safeMap(this).from(lenientNames).withStore(Lenient.names);
	}

	public static Optional<StatusExplodeForNone> lenientValueOf(String lenientName) {
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

}
