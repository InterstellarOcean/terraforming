/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

/**
 * Unidirectional model transformer from DTO to Domain types.
 *
 * @param <DTO> DTO type
 * @param <DOMAIN> Domain type
 *
 * @author Dariusz Wakuliński
 */
public interface ToDomainModelTransformer<DTO, DOMAIN> {

	/**
	 * Transform instance of DTO type to Domain type.
	 *
	 * @param dto Instance to transform
	 * @return Transformed instance
	 */
	DOMAIN toDomain(DTO dto);

	/**
	 * Transform collection of instances of DTO type to {@link List} of Domain type.
	 * Default implementation uses {@link #toDomain} method to transform the collection.
	 *
	 * @param dtos Instances to transform
	 * @return Transformed instances
	 */
	default List<DOMAIN> toDomain(Collection<DTO> dtos) {
		return dtos.stream().map(dto -> toDomain(dto)).collect(toList());
	}

}
