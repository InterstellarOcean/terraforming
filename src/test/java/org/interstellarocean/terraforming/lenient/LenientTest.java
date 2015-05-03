/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.lenient;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.interstellarocean.terraforming.util.TestGroups.UNIT_EXAMPLE;
import static org.interstellarocean.terraforming.util.VarargUtil.$;
import static org.interstellarocean.terraforming.util.VarargUtil.$$;

import java.util.Optional;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Example of use of enum mapping (transforming from {@link String} to {@link Enum}) to provide alternate enum names.
 * {@link Status#lenientValueOf} may be used as an replacement of {@link Enum#valueOf}.
 *
 * @see Status
 *
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT_EXAMPLE)
public class LenientTest {

	@Test(description = "Required initialization test to guarantee safety")
	public void shouldStatusInitializationSucceed() {
		Status.values();
	}

	@Test(dataProvider = "runningDataProvider")
	public void shouldLenientValueOfMapToRunning(String lenientName) {
		assertThat(Status.lenientValueOf(lenientName).get()).isSameAs(Status.RUNNING);
	}

	@Test(dataProvider = "absentDataProvider")
	public void shouldLenientValueOfReturnAbsent(String lenientName) {
		assertThat(Status.lenientValueOf(lenientName)).isSameAs(Optional.empty());
	}

	@DataProvider
	private Object[][] runningDataProvider() {
		return $$(
				$("RUNNING"),
				$("In Progress"),
				$("	in-progress "),
				$(" started		")
				);
	}

	@DataProvider
	private Object[][] absentDataProvider() {
		return $$(
				$("non-existing"),
				$(""),
				$(" 	 "),
				$((String) null)
				);
	}

}
