/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.model.refl.dozer;

import static org.dozer.loader.api.TypeMappingOptions.oneWay;
import static org.dozer.loader.api.TypeMappingOptions.stopOnErrors;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingBuilder;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformerBuilder;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformerProvider;

/**
 * @author Dariusz Wakuliński
 */
public class TestDozerModelTransformerProvider implements ReflectionModelTransformerProvider {

	private final DozerBeanMapper reflectionModelTransformer;

	// CDI: @Autowired, @Inject, etc.
	public TestDozerModelTransformerProvider(DozerBeanMapper reflectionModelTransformer) {
		this.reflectionModelTransformer = reflectionModelTransformer;
	}

	@Override
	public boolean provides(Class<?> library) {
		return org.dozer.Mapper.class.equals(library);
	}

	@Override
	public <SOURCE, DESTINATION> ReflectionModelTransformerBuilder<SOURCE, DESTINATION> builder() {
		return sourceType -> destinationType -> excludeFieldNames -> () -> {

			reflectionModelTransformer.addMapping(new BeanMappingBuilder() {

				@Override
				protected void configure() {
					TypeMappingBuilder typeMappingBuilder = mapping(sourceType, destinationType,
							oneWay(),
							stopOnErrors());
					excludeFieldNames.forEach(fieldName -> typeMappingBuilder.exclude(fieldName));
				}

			});

			return source -> destination -> {
				reflectionModelTransformer.map(source, destination);
				return destination;
			};

		};
	}

}
