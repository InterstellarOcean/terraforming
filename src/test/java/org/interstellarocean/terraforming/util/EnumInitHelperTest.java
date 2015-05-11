/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.util;

import static com.googlecode.catchexception.throwable.CatchThrowable.catchThrowable;
import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableSet;
import static java.util.EnumSet.allOf;
import static java.util.EnumSet.complementOf;
import static java.util.EnumSet.noneOf;
import static java.util.EnumSet.of;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.data.MapEntry.entry;
import static org.interstellarocean.terraforming.util.CatchThrowableWorkaround.caughtThrowable;
import static org.interstellarocean.terraforming.util.TestGroups.UNIT;

import java.lang.Thread.State;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import org.interstellarocean.terraforming.util.EnumInitUtil.SafeMapFrom;
import org.interstellarocean.terraforming.util.EnumInitUtil.SafeMapStore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT)
public class EnumInitHelperTest {

	private final static State TEST_ENUM = State.TIMED_WAITING;

	private final static State NULL_ENUM = null;

	private final static String TEST_MAPPING = "TEST_MAPPING";

	private final static String NULL_MAPPING = null;

	private EnumInitHelper objectUnderTest;

	@BeforeMethod
	public void setUp() {
		objectUnderTest = new EnumInitHelper();
	}

	public void shouldNullMappingErrorPass() {
		// when
		Supplier<AssertionError> result = objectUnderTest.nullMappingError(TEST_ENUM);

		// then
		assertThat(result).isNotNull();
	}

	public void shouldNullMappingErrorExplodeWhenGetCalled() {
		// when
		catchThrowable(objectUnderTest.nullMappingError(TEST_ENUM)).get();

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Null mapping for " + TEST_ENUM);
	}

	public void shouldAssertAllMappedPass() {
		// given
		Set<State> fullSet = unmodifiableSet(allOf(State.class));

		// when
		Set<State> result = objectUnderTest.assertAllMapped(fullSet);

		// then
		assertThat(result).isSameAs(fullSet);
	}

	public void shouldAssertAllMappedExplodeForNullArgument() {
		// given
		Collection<State> nullSet = null;

		// when
		catchThrowable(objectUnderTest).assertAllMapped(nullSet);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Invalid use: null mappedEnums argument");
	}

	public void shouldAssertAllMappedExplodeForEmptySet() {
		// given
		EnumSet<State> emptySet = noneOf(State.class);

		// when
		catchThrowable(objectUnderTest).assertAllMapped(emptySet);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Missing all mappings for (see stack trace for type)");
	}

	public void shouldAssertAllMappedExplodeForMissingMaping() {
		// given
		EnumSet<State> missingSet = complementOf(of(TEST_ENUM));

		// when
		catchThrowable(objectUnderTest).assertAllMapped(missingSet);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Missing mapping(s) for " + singleton(TEST_ENUM));
	}

	public void shouldSafeMapPass() {
		// when
		SafeMapFrom<State> result = objectUnderTest.safeMap(TEST_ENUM);

		// then
		assertThat(result).isNotNull();
	}

	public void shouldSafeMapExplodeForNullArgument() {
		// when
		catchThrowable(objectUnderTest).safeMap(NULL_ENUM);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Invalid use: null element argument");
	}

	public void shouldSafeMapFromCollectionPass() {
		// when
		SafeMapStore<State, String> result = objectUnderTest.safeMap(TEST_ENUM).from(singleton(TEST_MAPPING));

		// then
		assertThat(result).isNotNull();
	}

	public void shouldSafeMapFromCollectionExplodeForNullArgument() {
		// given
		Collection<String> nullCollection = null;

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM)).from(nullCollection);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Null mapping for " + TEST_ENUM);
	}

	public void shouldSafeMapFromVarargPass() {
		// when
		SafeMapStore<State, String> result = objectUnderTest.safeMap(TEST_ENUM).from(TEST_MAPPING);

		// then
		assertThat(result).isNotNull();
	}

	public void shouldSafeMapFromVarargExplodeForNullArgument() {
		// given
		String[] nullVararg = null;

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM)).from(nullVararg);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Null mapping for " + TEST_ENUM);
	}

	public void shouldSafeMapFromVarargWithStorePass() {
		// given
		Map<String, State> store = new HashMap<>();

		// when
		State result = objectUnderTest.safeMap(TEST_ENUM).from(TEST_MAPPING).withStore(store);

		// then
		assertThat(result).isSameAs(TEST_ENUM);
	}

	public void shouldSafeMapFromVarargWithStoreExplodeForNullArgument() {
		// given
		Map<String, State> nullStore = null;

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM).from(TEST_MAPPING)).withStore(nullStore);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Null mapping for " + TEST_ENUM);
	}

	public void shouldSafeMapFromVarargIncludeSelfPass() {
		// given
		Function<State, String> transformation = State::name;

		// when
		SafeMapStore<State, String> result = objectUnderTest.safeMap(TEST_ENUM).from(TEST_MAPPING).includeSelf(transformation);

		// then
		assertThat(result).isNotNull();
	}

	public void shouldSafeMapFromVarargIncludeSelfExplodeForNullArgument() {
		// given
		Function<State, String> nullTransformation = null;

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM).from(TEST_MAPPING)).includeSelf(nullTransformation);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Invalid use: null transformation argument");
	}

	public void shouldSafeMapFromVarargIncludeSelfExplodeForNullTransformationResult() {
		// given
		Function<State, String> toNullTransformation = element -> null;

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM).from(TEST_MAPPING)).includeSelf(toNullTransformation);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Null mapping for " + TEST_ENUM);
	}

	public void shouldSafeMapFromVarargWithStoreMap() {
		// given
		Map<String, State> store = new HashMap<>();

		// when
		objectUnderTest.safeMap(TEST_ENUM).from(TEST_MAPPING).withStore(store);

		// then
		assertThat(store).hasSize(1).contains(entry(TEST_MAPPING, TEST_ENUM));
	}

	public void shouldSafeMapFromVarargWithStoreAndIncludeSelfMap() {
		// given
		Map<Object, State> store = new HashMap<>();
		Function<State, Object> transformation = element -> TEST_MAPPING;

		// when
		objectUnderTest.safeMap(TEST_ENUM).from().includeSelf(transformation).withStore(store);

		// then
		assertThat(store).hasSize(1).contains(entry(TEST_MAPPING, TEST_ENUM));
	}

	public void shouldSafeMapFromVarargWithStoreAndIncludeSelfExplodeForDuplicate() {
		// given
		Map<String, State> store = new HashMap<>();
		Function<State, String> transformation = element -> TEST_MAPPING;

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM).from(TEST_MAPPING).includeSelf(transformation)).withStore(store);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Duplicate mapping for TEST_MAPPING, was: " + TEST_ENUM);
	}

	public void shouldSafeMapFromVarargWithStoreExplodeForEmpyMapping() {
		// given
		Map<Object, State> store = new HashMap<>();

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM).from()).withStore(store);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Missing all mappings for " + TEST_ENUM);
	}

	public void shouldSafeMapFromVarargWithStoreExplodeForNullMapping() {
		// given
		Map<String, State> store = new HashMap<>();

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM).from(NULL_MAPPING)).withStore(store);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Null mapping for " + TEST_ENUM);
	}

}
