package org.interstellarocean.terraforming.reduced;

import org.testng.annotations.Test;

@Test
public class ReducedExplodeTest {

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Null mapping for X_NULL")
	public void shouldStatusExplodeForNull() {
		StatusExplodeForNull.values();
	}

}
