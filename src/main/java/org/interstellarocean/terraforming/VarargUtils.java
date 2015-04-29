/**
 * <a href="https://www.gnu.org/licenses/gpl-3.0.html">https://www.gnu.org/licenses/gpl-3.0.html</a>
 */
package org.interstellarocean.terraforming;

/**
 * @author <a href="mailto:dariuswak@gmail.com">Dariusz Wakuliński</a>
 */
public class VarargUtils {

	// static utility pattern - instantiation and extension is forbidden
	private VarargUtils() {
	}

	public static Object[] $(Object... arguments) {
		return arguments;
	}

	public static Object[][] $$(Object[]... arguments) {
		return arguments;
	}

}
