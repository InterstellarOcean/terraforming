/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming;

import java.util.Collection;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformerBuilder;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformerFactory;
import org.interstellarocean.terraforming.reflection.ReflectionModelTransformerProvider;

/**
 * @author Dariusz Wakuliński
 */
public class TestReflectionModelTransformerFactory implements ReflectionModelTransformerFactory {

	private final Collection<ReflectionModelTransformerProvider> providers;

	// CDI: @Autowired, @Inject, etc.
	public TestReflectionModelTransformerFactory(Collection<ReflectionModelTransformerProvider> providers) {
		this.providers = providers;
	}

	@Override
	public <SOURCE, DESTINATION> ReflectionModelTransformerBuilder<SOURCE, DESTINATION> getBuilderFor(Class<?> library) {
		ReflectionModelTransformerProvider reflectionModelTransformerProvider = providers.stream()
				.filter(provider -> provider.provides(library))
				.findFirst()
				.get();
		return reflectionModelTransformerProvider.builder();
	}

}
