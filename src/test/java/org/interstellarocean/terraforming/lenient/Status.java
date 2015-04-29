/**
 * <a href="https://www.gnu.org/licenses/gpl-3.0.html">https://www.gnu.org/licenses/gpl-3.0.html</a>
 */
package org.interstellarocean.terraforming.lenient;

import static java.util.Optional.ofNullable;
import static org.interstellarocean.terraforming.EnumInitUtils.safeMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:dariuswak@gmail.com">Dariusz Wakuliński</a>
 */
public enum Status {

	NEW		("BEGIN", "START"),
	RUNNING	("IN PROGRESS",	"IN-PROGRESS", "IN_PROGRESS", "STARTED"),
	ADVANCED("ALMOST"),
	FINISHED("END",	"DONE"),
	NONE	(),
	EMPTY	(""),
	;

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
