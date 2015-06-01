/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.util;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.interstellarocean.terraforming.util.TestGroups.UNIT;

import java.lang.Thread.State;
import java.util.function.Supplier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT)
public class EnumInitHelperNullMappingErrorTest {

	private final static State TEST_ENUM = State.TIMED_WAITING;

	private EnumInitHelper objectUnderTest;

	@BeforeMethod
	public void setUp() {
		objectUnderTest = new EnumInitHelper();
	}

	public void shouldNullMappingErrorReturnCorrectResult() {
		// when
		Supplier<AssertionError> result = objectUnderTest.nullMappingError(TEST_ENUM);

		// then
		assertThat(result).isNotNull();
		assertThat(result.get()).isExactlyInstanceOf(AssertionError.class).hasMessage("Null mapping for " + TEST_ENUM);
	}

}
