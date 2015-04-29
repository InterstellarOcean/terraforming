/**
 * <a href="https://www.gnu.org/licenses/gpl-3.0.html">https://www.gnu.org/licenses/gpl-3.0.html</a>
 */
package org.interstellarocean.terraforming.lenient;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.interstellarocean.terraforming.VarargUtils.$;
import static org.interstellarocean.terraforming.VarargUtils.$$;

import java.util.Optional;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:dariuswak@gmail.com">Dariusz Wakuliński</a>
 */
@Test
public class LenientTest {

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
