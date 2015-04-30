/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming.reduced;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.interstellarocean.terraforming.VarargUtils.$;
import static org.interstellarocean.terraforming.VarargUtils.$$;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test
public class ReducedTest {

	@Test(dataProvider = "staticDataProvider")
	public void shouldAsReducedMapToStatic(Status status) {
		assertThat(status.asReduced()).isSameAs(Status.Reduced.STATIC);
	}

	@Test(dataProvider = "dynamicDataProvider")
	public void shouldAsReducedMapToDynamic(Status status) {
		assertThat(status.asReduced()).isSameAs(Status.Reduced.DYNAMIC);
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
