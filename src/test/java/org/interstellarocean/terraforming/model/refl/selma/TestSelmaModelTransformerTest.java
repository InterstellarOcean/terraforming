/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.model.refl.selma;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.interstellarocean.terraforming.util.TestGroups.INTEGRATION_EXAMPLE;

import java.lang.Thread.State;
import java.time.Instant;
import org.interstellarocean.terraforming.TestDomain;
import org.interstellarocean.terraforming.TestDto;
import org.interstellarocean.terraforming.TestReflectionModelTransformerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test(groups = INTEGRATION_EXAMPLE)
public class TestSelmaModelTransformerTest {

	private static final String TEST_NAME = "TEST_NAME";

	private static final Long TEST_COUNT = 3947L;

	private static final String TEST_TIMESTAMP_AS_STRING = "2015-05-03T20:03:28.675Z";

	private static final Instant TEST_TIMESTAMP = Instant.parse(TEST_TIMESTAMP_AS_STRING);

	private static final State TEST_STATE = State.WAITING;

	private static final String TEST_STATE_AS_STRING = TEST_STATE.name();

	private static final String TEST_IGNORE_ME = "TEST_IGNORE_ME";

	private TestSelmaModelTransformer objectUnderTest;

	@BeforeMethod
	public void setUp() {
		// CDI: @Autowired, @Inject, etc.
		objectUnderTest = new TestSelmaModelTransformer(
				new TestReflectionModelTransformerFactory(
						singleton(new TestSelmaModelTransformerProvider(
								asList(	TestToDomainSelmaModelTransformer.class,
										TestToDtoSelmaModelTransformer.class
										)).registerImplementations())));
		objectUnderTest.buildReflectionModelTransformers(); // CDI: @PostConstruct
	}

	public void shouldTransformToDto() {
		// given
		TestDomain domain = createFullDomain();
		TestDto expectedDto = createFullDto();
		expectedDto.setIgnoreMe(null);

		// when
		TestDto result = objectUnderTest.toDto(domain);

		// then
		assertThat(result).isEqualsToByComparingFields(expectedDto);
	}

	public void shouldTransformToDomain() {
		// given
		TestDto dto = createFullDto();
		TestDomain expectedDomain = createFullDomain();
		expectedDomain.setIgnoreMe(null);

		// when
		TestDomain result = objectUnderTest.toDomain(dto);

		// then
		assertThat(result).isEqualsToByComparingFields(expectedDomain);
	}

	private TestDto createFullDto() {
		TestDto dto = new TestDto();
		dto.setName(TEST_NAME);
		dto.setCount(TEST_COUNT);
		dto.setTimestamp(TEST_TIMESTAMP_AS_STRING);
		dto.setState(TEST_STATE_AS_STRING);
		dto.setIgnoreMe(TEST_IGNORE_ME);
		return dto;
	}

	private TestDomain createFullDomain() {
		TestDomain domain = new TestDomain();
		domain.setName(TEST_NAME);
		domain.setCount(TEST_COUNT);
		domain.setTimestamp(TEST_TIMESTAMP);
		domain.setState(TEST_STATE);
		domain.setIgnoreMe(TEST_IGNORE_ME);
		return domain;
	}

}
