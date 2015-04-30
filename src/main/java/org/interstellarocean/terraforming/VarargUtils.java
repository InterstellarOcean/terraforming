/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming;

/**
 * An utility to fluently transform varargs to arrays.
 * Allows initializing arrays with a concise expression.
 *
 * @author Dariusz Wakuliński
 */
public class VarargUtils {

	// static utility pattern - instantiation and extension is forbidden
	private VarargUtils() {
	}

	/**
	 * Transforms vararg to an array.
	 * Allows initializing arrays with a concise expression.
	 *
	 * @param arguments A vararg to transform
	 * @return Array containing vararg elements
	 */
	public static Object[] $(Object... arguments) {
		return arguments;
	}

	/**
	 * Transforms vararg or arrays to a 2d array.
	 * Allows initializing 2d arrays with a concise expression.
	 *
	 * @param arguments A vararg to transform
	 * @return 2d array containing vararg elements
	 */
	public static Object[][] $$(Object[]... arguments) {
		return arguments;
	}

}
