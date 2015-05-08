/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.interstellarocean.terraforming.util.TestGroups.UNIT;

import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT)
public class ToDtoModelTransformerDefaultTest {

	private static final Long TEST_DOMAIN = 239847L;

	private static final Long TEST_OTHER_DOMAIN = 91287L;

	private static final String TEST_DTO = TEST_DOMAIN.toString();

	private static final String TEST_OTHER_DTO = TEST_OTHER_DOMAIN.toString();

	private ToDtoModelTransformer<Long, String> objectUnderTest;

	@BeforeMethod
	public void setUp() {
		objectUnderTest = domain -> domain.toString();
	}

	public void shouldToDomainTransform() {
		// given
		List<Long> domains = asList(TEST_DOMAIN, TEST_OTHER_DOMAIN);

		// when
		List<String> result = objectUnderTest.toDto(domains);

		// then
		assertThat(result).containsExactly(TEST_DTO, TEST_OTHER_DTO);
	}

}
