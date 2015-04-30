/**
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.interstellarocean.terraforming;

/**
 * Bidirectional model transformer between Domain and DTO types.
 *
 * @param <DOMAIN> Domain type
 * @param <DTO> DTO type
 *
 * @author Dariusz Wakuliński
 */
public interface ModelTransformer<DOMAIN, DTO> extends ToDomainModelTransformer<DTO, DOMAIN>, ToDtoModelTransformer<DOMAIN, DTO> {

}
