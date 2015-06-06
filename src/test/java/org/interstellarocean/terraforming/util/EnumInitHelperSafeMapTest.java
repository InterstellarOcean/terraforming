/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.util;

import static com.googlecode.catchexception.throwable.CatchThrowable.catchThrowable;
import static java.util.Collections.singleton;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.data.MapEntry.entry;
import static org.interstellarocean.terraforming.util.CatchThrowableWorkaround.caughtThrowable;
import static org.interstellarocean.terraforming.util.TestGroups.UNIT;

import java.lang.Thread.State;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.interstellarocean.terraforming.util.EnumInitUtil.SafeMapFrom;
import org.interstellarocean.terraforming.util.EnumInitUtil.SafeMapStore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT)
public class EnumInitHelperSafeMapTest {

	private enum WaivedEnum {
		@Unmapped WAIVED_ENUM;
	}

	private final static State TEST_ENUM = State.TIMED_WAITING;

	private final static State TEST_OTHER_ENUM = State.TERMINATED;

	private final static State NULL_ENUM = null;

	private final static String TEST_MAPPING = "TEST_MAPPING";

	private final static String TEST_OTHER_MAPPING = "TEST_OTHER_MAPPING";

	private final static String NULL_MAPPING = null;

	private EnumInitHelper objectUnderTest;

	@BeforeMethod
	public void setUp() {
		objectUnderTest = new EnumInitHelper();
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
		Function<State, String> transformation = element -> TEST_OTHER_MAPPING;
		store.put(TEST_OTHER_MAPPING, TEST_OTHER_ENUM);

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM).from(TEST_MAPPING).includeSelf(transformation)).withStore(store);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class)
				.hasMessage("Duplicate mapping for " + TEST_OTHER_MAPPING + "->" + TEST_ENUM + ", was: " + TEST_OTHER_ENUM);
	}

	public void shouldSafeMapFromVarargWithStoreExplodeForEmptyMapping() {
		// given
		Map<Object, State> store = new HashMap<>();

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM).from()).withStore(store);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Missing all mappings for " + TEST_ENUM);
	}

	public void shouldSafeMapFromVarargWithStorePassForEmptyMappingWhenWaived() {
		// given
		Map<Object, WaivedEnum> store = new HashMap<>();

		// when
		catchThrowable(objectUnderTest.safeMap(WaivedEnum.WAIVED_ENUM).from()).withStore(store);

		// then
		assertThat(caughtThrowable()).isNull();
		assertThat(store).isEmpty();
	}

	public void shouldSafeMapFromVarargWithStoreExplodeForNonEmptyMappingWhenWaived() {
		// given
		Map<String, WaivedEnum> store = new HashMap<>();

		// when
		catchThrowable(objectUnderTest.safeMap(WaivedEnum.WAIVED_ENUM).from(TEST_MAPPING)).withStore(store);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Found non-empty mappings " + singleton(TEST_MAPPING)
				+ " for @Unmapped " + WaivedEnum.WAIVED_ENUM);
	}

	public void shouldSafeMapFromVarargWithStoreExplodeForNullMapping() {
		// given
		Map<String, State> store = new HashMap<>();

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM).from(NULL_MAPPING)).withStore(store);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class).hasMessage("Null mapping for " + TEST_ENUM);
	}

	public void shouldSafeMapFromVarargWithStoreExplodeForDuplicate() {
		// given
		Map<String, State> store = new HashMap<>();
		store.put(TEST_MAPPING, TEST_OTHER_ENUM);

		// when
		catchThrowable(objectUnderTest.safeMap(TEST_ENUM).from(TEST_MAPPING)).withStore(store);

		// then
		assertThat(caughtThrowable()).isExactlyInstanceOf(AssertionError.class)
				.hasMessage("Duplicate mapping for " + TEST_MAPPING + "->" + TEST_ENUM + ", was: " + TEST_OTHER_ENUM);
	}

}
