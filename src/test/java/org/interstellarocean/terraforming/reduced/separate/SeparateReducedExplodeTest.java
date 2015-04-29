/**
 * <a href="https://www.gnu.org/licenses/gpl-3.0.html">https://www.gnu.org/licenses/gpl-3.0.html</a>
 */
package org.interstellarocean.terraforming.reduced.separate;

import org.testng.annotations.Test;

/**
 * @author <a href="mailto:dariuswak@gmail.com">Dariusz Wakuliński</a>
 */
@Test
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
