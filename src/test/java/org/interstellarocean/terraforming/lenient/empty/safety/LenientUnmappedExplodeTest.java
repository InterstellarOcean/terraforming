/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.lenient.empty.safety;

import static org.interstellarocean.terraforming.util.TestGroups.UNIT_EXAMPLE;

import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT_EXAMPLE)
public class LenientUnmappedExplodeTest {

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp =
			"Found non-empty mappings \\[MAPPING_FOR_UNMAPPED\\] for @Unmapped X_UNMAPPED")
	public void shouldStatusExplodeForNone() {
		StatusExplodeForMappedUnmapped.values();
	}

}
