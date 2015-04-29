/**
 * <a href="https://www.gnu.org/licenses/gpl-3.0.html">https://www.gnu.org/licenses/gpl-3.0.html</a>
 */
package org.interstellarocean.terraforming.reduced.separate;

import static org.interstellarocean.terraforming.VarargUtils.$;
import static org.interstellarocean.terraforming.VarargUtils.$$;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:dariuswak@gmail.com">Dariusz Wakuliński</a>
 */
@Test
public class SeparateReducedTest {

	@Test(dataProvider = "staticDataProvider")
	public void shouldAsReducedMapToStatic(Status status) {
		assertEquals(Reduced.valueOf(status), Reduced.STATIC);
	}

	@Test(dataProvider = "dynamicDataProvider")
	public void shouldAsReducedMapToDynamic(Status status) {
		assertEquals(Reduced.valueOf(status), Reduced.DYNAMIC);
	}

	@DataProvider
	private Object[][] staticDataProvider() {
		return $$(
				$(Status.NEW),
				$(Status.FINISHED)
		);
	}

	@DataProvider
	private Object[][] dynamicDataProvider() {
		return $$(
				$(Status.RUNNING),
				$(Status.ADVANCED)
		);
	}

}
