/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.interstellarocean.terraforming.util.TestGroups.UNIT;

import java.util.List;
import org.testng.annotations.Test;

/**
 * @author Dariusz Wakuliński
 */
@Test(groups = UNIT)
public class ToDomainModelTransformerDefaultTest {

	private static final Long TEST_DTO = 239847L;

	private static final Long TEST_OTHER_DTO = 91287L;

	private static final String TEST_DOMAIN = TEST_DTO.toString();

	private static final String TEST_OTHER_DOMAIN = TEST_OTHER_DTO.toString();

	private final ToDomainModelTransformer<Long, String> objectUnderTest = (dto) -> dto.toString();

	public void shouldToDomainTransform() {
		// given
		List<Long> dtos = asList(TEST_DTO, TEST_OTHER_DTO);

		// when
		List<String> result = objectUnderTest.toDomain(dtos);

		// then
		assertThat(result).containsExactly(TEST_DOMAIN, TEST_OTHER_DOMAIN);
	}

}
