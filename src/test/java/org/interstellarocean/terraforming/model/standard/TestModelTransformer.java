/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.model.standard;

import static java.util.Optional.ofNullable;

import java.lang.Thread.State;
import java.time.Instant;
import org.interstellarocean.terraforming.ModelTransformer;
import org.interstellarocean.terraforming.TestDomain;
import org.interstellarocean.terraforming.TestDto;

/**
 * This model transformer doesn't use reflection.
 * This is actually preferred way to implement model transformers, however use of reflection or code generation
 * for transforming of larger data structures of the same field names is justified.
 *
 * @author Dariusz Wakuliński
 */
public class TestModelTransformer implements ModelTransformer<TestDomain, TestDto> {

	@Override
	public TestDto toDto(TestDomain domain) {
		TestDto dto = new TestDto();
		dto.setName(domain.getName());
		dto.setCount(domain.getCount());
		dto.setTimestamp(ofNullable(domain.getTimestamp()).map(Instant::toString).orElse(null));
		dto.setState(ofNullable(domain.getState()).map(State::name).orElse(null));
		//ofNullable(domain.getState()).ifPresent(state -> dto.setState(state.name())); // alternative implementation
		// ignored dto.setIgnoreMe(domain.getIgnoreMe());
		return dto;
	}

	@Override
	public TestDomain toDomain(TestDto dto) {
		TestDomain domain = new TestDomain();
		domain.setName(dto.getName());
		domain.setCount(dto.getCount());
		domain.setTimestamp(ofNullable(dto.getTimestamp()).map(Instant::parse).orElse(null));
		domain.setState(ofNullable(dto.getState()).map(State::valueOf).orElse(null));
		// ignored domain.setIgnoreMe(dto.getIgnoreMe());
		return domain;
	}

}
