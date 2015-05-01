/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

/**
 * Unidirectional model transformer from Domain to DTO types.
 *
 * @param <DOMAIN> Domain type
 * @param <DTO> DTO type
 *
 * @author Dariusz Wakuliński
 */
public interface ToDtoModelTransformer<DOMAIN, DTO> {

	/**
	 * Transform instance of Domain type to DTO type.
	 *
	 * @param domain Instance to transform
	 * @return Transformed instance
	 */
	DTO toDto(DOMAIN domain);

	/**
	 * Transform collection of instances of Domain type to {@link List} of DTO type.
	 * Default implementation uses {@link #toDto} method to transform the collection.
	 *
	 * @param domains Instances to transform
	 * @return Transformed instances
	 */
	default List<DTO> toDto(Collection<DOMAIN> domains) {
		return domains.stream().map(domain -> toDto(domain)).collect(toList());
	}

}
