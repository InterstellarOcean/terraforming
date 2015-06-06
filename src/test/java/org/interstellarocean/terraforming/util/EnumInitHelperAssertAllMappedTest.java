/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.util;

import static com.googlecode.catchexception.throwable.CatchThrowable.catchThrowable;
import static java.util.Collections.singleton;
import static java.util.EnumSet.allOf;
import static java.util.EnumSet.complementOf;
import static java.util.EnumSet.of;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.interstellarocean.terraforming.util.CatchThrowableWorkaround.caughtThrowable;
import static org.interstellarocean.terraforming.util.TestGroups.UNIT;

import java.lang.Thread.State;
import java.util.EnumMap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT)
public class EnumInitHelperAssertAllMappedTest {

	private static final State TEST_ENUM = State.TIMED_WAITING;

	private EnumInitHelper objectUnderTest;

	@BeforeMethod
	public void setUp() {
		objectUnderTest = new EnumInitHelper();
	}

	public void shouldAssertAllMappedPass() {
		// given
		EnumMap<State, String> fullMap = new EnumMap<>(allOf(State.class).stream().collect(toMap(identity(), State::name)));
		EnumMap<State, String> fullMapCopy = fullMap.clone();

		// when
		EnumMap<State, String> result = objectUnderTest.assertAllMapped(fullMap);

		// then
		assertThat(result).isSameAs(fullMap).isEqualTo(fullMapCopy);
	}

	public void shouldAssertAllMappedExplodeForNullArgument() {
		// given
		EnumMap<State, ?> nullMap = null;

		// when
		catchThrowable(objectUnderTest).assertAllMapped(nullMap);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Invalid use: null mappings argument");
	}

	public void shouldAssertAllMappedExplodeForEmptyMap() {
		// given
		EnumMap<State, ?> emptyMap = new EnumMap<>(State.class);

		// when
		catchThrowable(objectUnderTest).assertAllMapped(emptyMap);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Missing all mappings for (see stack trace for type)");
	}

	public void shouldAssertAllMappedExplodeForMissingMaping() {
		// given
		EnumMap<State, ?> missingMap = new EnumMap<>(complementOf(of(TEST_ENUM)).stream().collect(toMap(identity(), State::name)));

		// when
		catchThrowable(objectUnderTest).assertAllMapped(missingMap);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Missing mapping(s) for " + singleton(TEST_ENUM));
	}

}
