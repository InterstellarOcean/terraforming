/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.util;

/**
 * An utility to fluently transform varargs to arrays.
 * Allows initializing arrays with a concise expression.
 *
 * @author Dariusz Wakuliński
 */
public class VarargUtil {

	// static utility pattern - instantiation and extension is forbidden
	private VarargUtil() {
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
