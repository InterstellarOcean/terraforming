/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.model.refl.selma;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import java.lang.Thread.State;
import java.time.Instant;
import javax.annotation.PostConstruct;
import org.interstellarocean.terraforming.ModelTransformer;
import org.interstellarocean.terraforming.TestDomain;
import org.interstellarocean.terraforming.TestDto;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformer;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformerFactory;

/**
 * @see http://www.selma-java.org/
 *
 * @author Dariusz Wakuliński
 */
public class TestSelmaModelTransformer implements ModelTransformer<TestDomain, TestDto> {

	private static final Class<?> SELMA_LIBRARY = fr.xebia.extras.selma.Selma.class;

	private final ReflectionModelTransformerFactory reflectionModelTransformerFactory;

	private ReflectionModelTransformer<TestDomain, TestDto> toDtoReflectionModelTransformer;

	private ReflectionModelTransformer<TestDto, TestDomain> toDomainReflectionModelTransformer;

	// CDI: @Autowired, @Inject, etc.
	public TestSelmaModelTransformer(ReflectionModelTransformerFactory reflectionModelTransformerFactory) {
		this.reflectionModelTransformerFactory = reflectionModelTransformerFactory;
	}

	@PostConstruct
	void buildReflectionModelTransformers() {
		toDtoReflectionModelTransformer = reflectionModelTransformerFactory
				.<TestDomain, TestDto>getBuilderFor(SELMA_LIBRARY)
				.from(TestDomain.class)
				.to(TestDto.class)
				.excludeFields(emptyList()) // XXX not supported, see the interface's annotation
				.build();
		toDomainReflectionModelTransformer = reflectionModelTransformerFactory
				.<TestDto, TestDomain>getBuilderFor(SELMA_LIBRARY)
				.from(TestDto.class)
				.to(TestDomain.class)
				.excludeFields(emptyList()) // XXX not supported, see the interface's annotation
				.build();
	}

	@Override
	public TestDto toDto(TestDomain domain) {
		TestDto dto = toDtoReflectionModelTransformer.from(domain).to(new TestDto());
		dto.setTimestamp(ofNullable(domain.getTimestamp()).map(Instant::toString).orElse(null));
		dto.setState(ofNullable(domain.getState()).map(State::name).orElse(null));
		return dto;
	}

	@Override
	public TestDomain toDomain(TestDto dto) {
		TestDomain domain = toDomainReflectionModelTransformer.from(dto).to(new TestDomain());
		domain.setTimestamp(ofNullable(dto.getTimestamp()).map(Instant::parse).orElse(null));
		domain.setState(ofNullable(dto.getState()).map(State::valueOf).orElse(null));
		return domain;
	}

}
