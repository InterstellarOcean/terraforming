/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.util;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.interstellarocean.terraforming.util.TestGroups.UNIT;

import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT)
public class VarargUtilTest {

	private static final String TEST_VALUE = "TEST_VALUE";

	private static final String TEST_OTHER_VALUE = "TEST_OTHER_VALUE";

	private static final String[] TEST_ARRAY = { TEST_VALUE };

	private static final String[] TEST_OTHER_ARRAY = { TEST_OTHER_VALUE };

	public void $() {
		// when
		Object[] result = VarargUtil.$(TEST_VALUE, TEST_OTHER_VALUE);

		// then
		assertThat(result).containsExactly(TEST_VALUE, TEST_OTHER_VALUE);
	}

	public void $$() {
		// when
		Object[][] result = VarargUtil.$$(TEST_ARRAY, TEST_OTHER_ARRAY);

		// then
		assertThat(result).containsExactly(TEST_ARRAY, TEST_OTHER_ARRAY);
	}

}
