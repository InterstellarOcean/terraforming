/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reduced.separate;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.interstellarocean.terraforming.util.TestGroups.UNIT_EXAMPLE;
import static org.interstellarocean.terraforming.util.VarargUtil.$;
import static org.interstellarocean.terraforming.util.VarargUtil.$$;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Example of use of enum transforming to reduce number of instances.
 * In this example enums may be defined in separated tiers, only one-directional dependency is present.
 *
 * @see Reduced
 * @see Status
 *
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT_EXAMPLE)
public class SeparateReducedTest {

	@Test(description = "Required initialization test to guarantee safety")
	public void shouldReducedInitializationSucceed() {
		Reduced.values();
	}

	@Test(dataProvider = "staticDataProvider", dependsOnMethods = "shouldReducedInitializationSucceed")
	public void shouldAsReducedMapToStatic(Status status) {
		assertThat(Reduced.valueOf(status)).isSameAs(Reduced.STATIC);
	}

	@Test(dataProvider = "dynamicDataProvider", dependsOnMethods = "shouldReducedInitializationSucceed")
	public void shouldAsReducedMapToDynamic(Status status) {
		assertThat(Reduced.valueOf(status)).isSameAs(Reduced.DYNAMIC);
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
