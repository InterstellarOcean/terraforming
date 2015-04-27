package org.interstellarocean.terraforming.lenient;

import static java.util.Optional.ofNullable;
import static org.interstellarocean.terraforming.EnumInitUtils.safeMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum StatusExplodeForDuplicate {

	NEW		("BEGIN", "START"),
	RUNNING	("IN PROGRESS",	"IN-PROGRESS", "IN_PROGRESS", "STARTED"),
	ADVANCED("ALMOST"),
	FINISHED("END",	"DONE"),
	X_DUPLCT("ALMOST"),
	;

	private static class Lenient {
		private static final Map<String, StatusExplodeForDuplicate> names = new HashMap<>();
	}

	private StatusExplodeForDuplicate(String... lenientNames) {
		safeMap(this).from(lenientNames).withStore(Lenient.names);
	}

	public static Optional<StatusExplodeForDuplicate> lenientValueOf(String lenientName) {
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
