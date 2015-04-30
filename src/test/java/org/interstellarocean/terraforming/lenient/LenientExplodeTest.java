/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.lenient;

import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test
public class LenientExplodeTest {

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Duplicate mapping for ALMOST, was: ADVANCED")
	public void shouldStatusExplodeForDuplicate() {
		StatusExplodeForDuplicate.values();
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Null mapping for X_NULL")
	public void shouldStatusExplodeForNull() {
		StatusExplodeForNull.values();
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Null mapping for X_NULL_A")
	public void shouldStatusExplodeForNullArray() {
		StatusExplodeForNullArray.values();
	}

}
