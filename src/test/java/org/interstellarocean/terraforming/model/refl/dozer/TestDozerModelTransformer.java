/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.model.refl.dozer;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;

import java.time.Instant;
import javax.annotation.PostConstruct;
import org.interstellarocean.terraforming.ModelTransformer;
import org.interstellarocean.terraforming.TestDomain;
import org.interstellarocean.terraforming.TestDto;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformer;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformerFactory;

/**
 * @see http://dozer.sourceforge.net/
 *
 * @author Dariusz Wakuliński
 */
public class TestDozerModelTransformer implements ModelTransformer<TestDomain, TestDto> {

	private static final Class<?> DOZER_LIBRARY = org.dozer.Mapper.class;

	private final ReflectionModelTransformerFactory reflectionModelTransformerFactory;

	private ReflectionModelTransformer<TestDomain, TestDto> toDtoReflectionModelTransformer;

	private ReflectionModelTransformer<TestDto, TestDomain> toDomainReflectionModelTransformer;

	// CDI: @Autowired, @Inject, etc.
	public TestDozerModelTransformer(ReflectionModelTransformerFactory reflectionModelTransformerFactory) {
		this.reflectionModelTransformerFactory = reflectionModelTransformerFactory;
	}

	@PostConstruct
	void buildReflectionModelTransformers() {
		toDtoReflectionModelTransformer = reflectionModelTransformerFactory
				.<TestDomain, TestDto>getBuilderFor(DOZER_LIBRARY)
				.from(TestDomain.class)
				.to(TestDto.class)
				.excludeFields(asList("timestamp", "ignoreMe"))
				.build();
		toDomainReflectionModelTransformer = reflectionModelTransformerFactory
				.<TestDto, TestDomain>getBuilderFor(DOZER_LIBRARY)
				.from(TestDto.class)
				.to(TestDomain.class)
				.excludeFields(asList("timestamp", "ignoreMe"))
				.build();
	}

	@Override
	public TestDto toDto(TestDomain domain) {
		TestDto dto = toDtoReflectionModelTransformer.from(domain).to(new TestDto());
		dto.setTimestamp(ofNullable(domain.getTimestamp()).map(Instant::toString).orElse(null));
		return dto;
	}

	@Override
	public TestDomain toDomain(TestDto dto) {
		TestDomain domain = toDomainReflectionModelTransformer.from(dto).to(new TestDomain());
		domain.setTimestamp(ofNullable(dto.getTimestamp()).map(Instant::parse).orElse(null));
		return domain;
	}

}
