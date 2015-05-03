/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.reduced.separate.safety;

import static org.interstellarocean.terraforming.util.TestGroups.UNIT_EXAMPLE;

import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT_EXAMPLE)
public class SeparateReducedExplodeTest {

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Duplicate mapping for RUNNING, was: DYNAMIC")
	public void shouldReducedExplodeForDuplicate() {
		ReducedExplodeForDuplicate.values();
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Missing all mappings for X_EMPTY")
	public void shouldReducedExplodeForEmpty() {
		ReducedExplodeForEmpty.values();
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Missing all mappings for \\(see stack trace for type\\)")
	public void shouldReducedExplodeForMissingAll() {
		ReducedExplodeForMissingAll.values();
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Missing mapping\\(s\\) for \\[RUNNING, FINISHED]")
	public void shouldReducedExplodeForMissing() {
		ReducedExplodeForMissing.values();
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Null mapping for X_NULL")
	public void shouldReducedExplodeForNull() {
		ReducedExplodeForNull.values();
	}

}
