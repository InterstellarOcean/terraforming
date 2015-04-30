/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming;

import static java.util.Collections.singleton;
import static java.util.EnumSet.allOf;
import static java.util.EnumSet.complementOf;
import static java.util.EnumSet.of;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.data.MapEntry.entry;

import java.lang.Thread.State;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.interstellarocean.terraforming.EnumInitUtils.SafeMapFrom;
import org.interstellarocean.terraforming.EnumInitUtils.SafeMapStore;
import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test
public class EnumInitUtilsTest {

	private final static State ENUM_WAITING = State.WAITING;

	private final static State NULL_ENUM = null;

	private final static String TEST_MAPPING = "TEST_MAPPING";

	private final static String NULL_MAPPING = null;

	public void shouldNullMappedPass() {
		// when
		Enum<State> result = ofNullable(ENUM_WAITING).orElseThrow(
				EnumInitUtils.nullMapped(ENUM_WAITING));

		// then
		assertThat(result).isSameAs(ENUM_WAITING);
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Null mapping for WAITING")
	public void shouldNullMappedExplodeForNullArgument() {
		// when
		ofNullable(NULL_MAPPING).orElseThrow(
				EnumInitUtils.nullMapped(ENUM_WAITING));

		// then expect explode
	}

	public void shouldAssertAllMappedPass() {
		// given
		EnumMap<State, ?> fullMap = new EnumMap<>(allOf(State.class).stream().collect(toMap(identity(), State::name)));

		// when
		EnumInitUtils.assertAllMapped(fullMap);

		// then expect pass
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Invalid use: null mappings argument")
	public void shouldAssertAllMappedExplodeForNullArgument() {
		// given
		EnumMap<State, ?> nullMap = null;

		// when
		EnumInitUtils.assertAllMapped(nullMap);

		// then expect explode
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Missing all mappings for \\(see stack trace for type\\)")
	public void shouldAssertAllMappedExplodeForEmptyMap() {
		// given
		EnumMap<State, ?> emptyMap = new EnumMap<>(State.class);

		// when
		EnumInitUtils.assertAllMapped(emptyMap);

		// then expect explode
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Missing mapping\\(s\\) for \\[WAITING\\]")
	public void shouldAssertAllMappedExplodeForMissingMaping() {
		// given
		EnumMap<State, ?> missingMap = new EnumMap<>(complementOf(of(ENUM_WAITING)).stream().collect(toMap(identity(), State::name)));

		// when
		EnumInitUtils.assertAllMapped(missingMap);

		// then expect explode
	}

	public void shouldSafeMapPass() {
		// when
		SafeMapFrom<State> result = EnumInitUtils.safeMap(ENUM_WAITING);

		// then
		assertThat(result).isNotNull();
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Invalid use: null element argument")
	public void shouldSafeMapExplodeForNullArgument() {
		// when
		EnumInitUtils.safeMap(NULL_ENUM);

		// then expect explode
	}

	public void shouldSafeMapFromCollectionPass() {
		// when
		SafeMapStore<State, String> result = EnumInitUtils.safeMap(ENUM_WAITING).from(singleton(TEST_MAPPING));

		// then
		assertThat(result).isNotNull();
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Null mapping for WAITING")
	public void shouldSafeMapFromCollectionExplodeForNullArgument() {
		// given
		Collection<String> nullCollection = null;

		// when
		EnumInitUtils.safeMap(ENUM_WAITING).from(nullCollection);

		// then expect explode
	}

	public void shouldSafeMapFromVarargPass() {
		// when
		SafeMapStore<State, String> result = EnumInitUtils.safeMap(ENUM_WAITING).from(TEST_MAPPING);

		// then
		assertThat(result).isNotNull();
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Null mapping for WAITING")
	public void shouldSafeMapFromVarargExplodeForNullArgument() {
		// given
		String[] nullVararg = null;

		// when
		EnumInitUtils.safeMap(ENUM_WAITING).from(nullVararg);

		// then expect explode
	}

	public void shouldSafeMapFromVarargWithStorePass() {
		// given
		Map<String, State> store = new HashMap<>();

		// when
		State result = EnumInitUtils.safeMap(ENUM_WAITING).from(TEST_MAPPING).withStore(store);

		// then
		assertThat(result).isSameAs(ENUM_WAITING);
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Null mapping for WAITING")
	public void shouldSafeMapFromVarargWithStoreExplodeForNullArgument() {
		// given
		Map<String, State> nullStore = null;

		// when
		EnumInitUtils.safeMap(ENUM_WAITING).from(TEST_MAPPING).withStore(nullStore);

		// then expect explode
	}

	public void shouldSafeMapFromVarargIncludeSelfPass() {
		// given
		Function<State, String> transformation = State::name;

		// when
		SafeMapStore<State, String> result = EnumInitUtils.safeMap(ENUM_WAITING).from(TEST_MAPPING).includeSelf(transformation);

		// then
		assertThat(result).isNotNull();
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Invalid use: null transformation argument")
	public void shouldSafeMapFromVarargIncludeSelfExplodeForNullArgument() {
		// given
		Function<State, String> nullTransformation = null;

		// when
		EnumInitUtils.safeMap(ENUM_WAITING).from(TEST_MAPPING).includeSelf(nullTransformation);

		// then expect explode
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Null mapping for WAITING")
	public void shouldSafeMapFromVarargIncludeSelfExplodeForNullTransformationResult() {
		// given
		Function<State, String> toNullTransformation = (element) -> null;

		// when
		EnumInitUtils.safeMap(ENUM_WAITING).from(TEST_MAPPING).includeSelf(toNullTransformation);

		// then expect explode
	}

	public void shouldSafeMapFromVarargWithStoreMap() {
		// given
		Map<String, State> store = new HashMap<>();

		// when
		EnumInitUtils.safeMap(ENUM_WAITING).from(TEST_MAPPING).withStore(store);

		// then
		assertThat(store).hasSize(1).contains(entry(TEST_MAPPING, ENUM_WAITING));
	}

	public void shouldSafeMapFromVarargWithStoreAndIncludeSelfMap() {
		// given
		Map<Object, State> store = new HashMap<>();
		Function<State, Object> transformation = (element) -> TEST_MAPPING;

		// when
		EnumInitUtils.safeMap(ENUM_WAITING).from().includeSelf(transformation).withStore(store);

		// then
		assertThat(store).hasSize(1).contains(entry(TEST_MAPPING, ENUM_WAITING));
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Duplicate mapping for TEST_MAPPING, was: WAITING")
	public void shouldSafeMapFromVarargWithStoreAndIncludeSelfExplodeForDuplicate() {
		// given
		Map<String, State> store = new HashMap<>();
		Function<State, String> transformation = (element) -> TEST_MAPPING;

		// when
		EnumInitUtils.safeMap(ENUM_WAITING).from(TEST_MAPPING).includeSelf(transformation).withStore(store);

		// then expect explode
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Missing all mappings for WAITING")
	public void shouldSafeMapFromVarargWithStoreExplodeForEmpyMapping() {
		// given
		Map<Object, State> store = new HashMap<>();

		// when
		EnumInitUtils.safeMap(ENUM_WAITING).from().withStore(store);

		// then expect explode
	}

	@Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "Null mapping for WAITING")
	public void shouldSafeMapFromVarargWithStoreExplodeForNullMapping() {
		// given
		Map<String, State> store = new HashMap<>();

		// when
		EnumInitUtils.safeMap(ENUM_WAITING).from(NULL_MAPPING).withStore(store);

		// then expect explode
	}

}
