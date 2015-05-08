/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.util;

/**
 * Compiler error workaround.
 * Catch Throwable library doesn't compile with Fest Assertions.
 *
 * @author Dariusz Wakuliński
 */
class CatchThrowableWorkaround {

	/**
	 * Compiler error workaround.
	 * Catch Throwable library doesn't compile with Fest Assertions.
	 *
	 * @see com.googlecode.catchexception.throwable.CatchThrowable#caughtThrowable
	 */
	static Throwable caughtThrowable() {
		return com.googlecode.catchexception.throwable.CatchThrowable.caughtThrowable();
	}

}
