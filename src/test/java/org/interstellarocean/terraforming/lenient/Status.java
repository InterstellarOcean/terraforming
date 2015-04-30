/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.lenient;

import static java.util.Optional.ofNullable;
import static org.interstellarocean.terraforming.EnumInitUtils.safeMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Example of use of enum mapping (transforming from {@link String} to {@link Enum}) to provide alternate enum names.
 * {@link Status#lenientValueOf} may be used as an replacement of {@link Enum#valueOf}.
 *
 * <p><b>NOTE</b> An unit test ({@link LenientTest#shouldStatusInitializationSucceed()}) is required to guarantee safety, i.e. an error-free runtime.
 *
 * @see LenientTest
 *
 * @author Dariusz Wakuliński
 */
public enum Status {

	NEW		("BEGIN", "START"),
	RUNNING	("IN PROGRESS",	"IN-PROGRESS", "IN_PROGRESS", "STARTED"),
	ADVANCED("ALMOST"),
	FINISHED("END",	"DONE"),
	NONE	(), // allowed only because includeSelf() is used
	EMPTY	(""), // allowed, but note below implementation ignores empty strings: a way to skip mapping although it is required
	;

	// Workaround for "illegal reference to static field from initializer" compilation error in constructor
	private static class Lenient {
		private static final Map<String, Status> names = new HashMap<>();
	}

	private Status(String... lenientNames) {
		safeMap(this).from(lenientNames).includeSelf(Status::name).withStore(Lenient.names);
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

}
