/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.model.refl.selma;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

import fr.xebia.extras.selma.Selma;
import java.util.Collection;
import javax.annotation.PostConstruct;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformerBuilder;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformerProvider;

/**
 * @author Dariusz Wakuliński
 */
public class TestSelmaModelTransformerProvider implements ReflectionModelTransformerProvider {

	private final Collection<Class<? extends SelmaModelTransformer<?, ?>>> selmaModelTransformers;

	private final SelmaModelTransformersBag selmaModelTransformersBag = new SelmaModelTransformersBag();

	// CDI: @Autowired, @Inject, etc.
	public TestSelmaModelTransformerProvider(Collection<Class<? extends SelmaModelTransformer<?, ?>>> selmaModelTransformers) {
		this.selmaModelTransformers = selmaModelTransformers;
	}

	@PostConstruct
	public TestSelmaModelTransformerProvider registerImplementations() {
		selmaModelTransformers.forEach(type -> selmaModelTransformersBag.put(type));
		return this;
	}

	@Override
	public boolean provides(Class<?> library) {
		return fr.xebia.extras.selma.Selma.class.equals(library);
	}

	@Override
	public <SOURCE, DESTINATION> ReflectionModelTransformerBuilder<SOURCE, DESTINATION> builder() {
		return sourceType -> destinationType -> excludeFieldNames -> () -> {

			Class<SelmaModelTransformer<SOURCE, DESTINATION>> selmaModelTransformerType = selmaModelTransformersBag.get(sourceType, destinationType);
			assertRegistered(selmaModelTransformerType, sourceType, destinationType);
			// XXX can't assert exclusions as @Mapper annotation is not retained for runtime :O(
			//assertExclusions(excludeFieldNames, selmaModelTransformerType.getAnnotation(Mapper.class).withIgnoreFields());

			SelmaModelTransformer<SOURCE, DESTINATION> selmaModelTransformer = Selma.getMapper(selmaModelTransformerType);
			return source -> destination -> {
				return selmaModelTransformer.transform(source, destination);
			};

		};
	}

	private <SOURCE, DESTINATION> void assertRegistered(
			Class<SelmaModelTransformer<SOURCE, DESTINATION>> selmaModelTransformerType,
			Class<SOURCE> sourceType, Class<DESTINATION> destinationType) {

		ofNullable(selmaModelTransformerType)
			.orElseThrow(() -> new AssertionError(format("No transformer registered for %s -> %s", sourceType, destinationType)));
	}

}
