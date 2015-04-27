package org.interstellarocean.terraforming.lenient;

import static org.interstellarocean.terraforming.VarargUtils.$;
import static org.interstellarocean.terraforming.VarargUtils.$$;
import static org.testng.Assert.assertEquals;

import java.util.Optional;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class LenientTest {

	@Test(dataProvider = "runningDataProvider")
	public void shouldLenientValueOfMapToRunning(String lenientName) {
		assertEquals(Status.lenientValueOf(lenientName).get(), Status.RUNNING);
	}

	@Test(dataProvider = "absentDataProvider")
	public void shouldLenientValueOfReturnAbsent(String lenientName) {
		assertEquals(Status.lenientValueOf(lenientName), Optional.empty());
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
