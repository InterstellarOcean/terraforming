/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.reduced.safety;

import static org.interstellarocean.terraforming.util.TestGroups.UNIT_EXAMPLE;

import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT_EXAMPLE)
public class ReducedExplodeTest {

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Null mapping for X_NULL")
	public void shouldStatusExplodeForNull() {
		StatusExplodeForNull.values();
	}

}
