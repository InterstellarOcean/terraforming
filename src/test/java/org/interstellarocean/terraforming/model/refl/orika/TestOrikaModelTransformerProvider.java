/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.model.refl.orika;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformerBuilder;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformerProvider;

/**
 * @author Dariusz Wakuliński
 */
public class TestOrikaModelTransformerProvider implements ReflectionModelTransformerProvider {

	private final MapperFactory mapperFactory;

	// CDI: @Autowired, @Inject, etc.
	public TestOrikaModelTransformerProvider(MapperFactory mapperFactory) {
		this.mapperFactory = mapperFactory;
	}

	@Override
	public boolean provides(Class<?> library) {
		return ma.glasnost.orika.MapperFacade.class.equals(library);
	}

	@Override
	public <SOURCE, DESTINATION> ReflectionModelTransformerBuilder<SOURCE, DESTINATION> builder() {
		return sourceType -> destinationType -> excludeFieldNames -> () -> {

			ClassMapBuilder<SOURCE, DESTINATION> builder = mapperFactory.classMap(sourceType, destinationType);
			excludeFieldNames.forEach(fieldName -> builder.exclude(fieldName));
			builder.byDefault().register();

			BoundMapperFacade<SOURCE, DESTINATION> reflectionModelTransformer = mapperFactory.getMapperFacade(sourceType, destinationType);
			return source -> destination -> {
				reflectionModelTransformer.map(source, destination);
				return destination;
			};

		};
	}

}
