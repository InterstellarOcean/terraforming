/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.lenient.empty;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.interstellarocean.terraforming.util.TestGroups.UNIT_EXAMPLE;
import static org.interstellarocean.terraforming.util.VarargUtil.$;

import java.util.Iterator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @see Status
 *
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT_EXAMPLE)
public class LenientUnmappedTest {

	@Test(description = "Required initialization test to guarantee safety")
	public void shouldStatusInitializationSucceed() {
		Status.values();
	}

	@Test(dataProvider = "allKeysDataProvider", dependsOnMethods = "shouldStatusInitializationSucceed")
	public void shouldLenientValueOfNeverMapToUnmapped(String lenientName) {
		assertThat(Status.lenientValueOf(lenientName).get()).isNotSameAs(Status.NONE);
	}

	@DataProvider
	private Iterator<Object[]> allKeysDataProvider() {
		return Status.peekMapingKeys().stream().map(key -> $(key)).iterator();
	}

}
