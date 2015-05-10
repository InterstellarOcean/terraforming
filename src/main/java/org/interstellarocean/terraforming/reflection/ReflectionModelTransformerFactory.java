/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reflection;

/**
 * Entry point for {@link org.interstellarocean.terraforming.ModelTransformer} to reflection model transformers.
 *
 * {@link ReflectionModelTransformerFactory} along with {@link ReflectionModelTransformerProvider} implementations uses factory and strategy patterns
 * to allow creating and configuring {@link ReflectionModelTransformer} of specific library provider.
 *
 * <h3>Example</h3>
 *
 * <blockquote><pre>
 * public class FooReflectionModelTransformerFactory implements ReflectionModelTransformerFactory {
 *
 * 	private final Collection&lt;ReflectionModelTransformerProvider&gt; providers;
 *
 * 	&#64;Inject // @Autowired, etc.
 * 	public FooReflectionModelTransformerFactory(Collection&lt;ReflectionModelTransformerProvider&gt; providers) {
 * 		this.providers = providers;
 * 	}
 *
 * 	&#64;Override
 * 	public &lt;SOURCE, DESTINATION&gt; ReflectionModelTransformerBuilder&lt;SOURCE, DESTINATION&gt; getBuilderFor(Class&lt;?&gt; library) {
 * 		ReflectionModelTransformerProvider reflectionModelTransformerProvider = providers.stream()
 * 				.filter(provider -&gt; provider.provides(library))
 * 				.findFirst()
 * 				.get();
 * 		return reflectionModelTransformerProvider.builder();
 * 	}
 *
 * }
 * </pre></blockquote>
 *
 * @see org.interstellarocean.terraforming.reflection
 * @see ReflectionModelTransformerProvider
 *
 * @author Dariusz Wakuliński
 */
public interface ReflectionModelTransformerFactory {

	/**
	 * Retrieves {@link ReflectionModelTransformerBuilder} allowing creation and configuration of a {@link ReflectionModelTransformer} instance
	 * that uses specific library.
	 *
	 * @param <SOURCE> Type to transform from. Used to parameterize {@link ReflectionModelTransformerBuilder}.
	 * @param <DESTINATION> Type to transform to. Used to parameterize {@link ReflectionModelTransformerBuilder}.
	 * @param library A class marking the strategy that use requested reflection transforming library.
	 * 			See {@link ReflectionModelTransformerProvider#provides(Class)} for details.
	 * @return {@link ReflectionModelTransformerBuilder} allowing creation and configuration of a {@link ReflectionModelTransformer} instance
	 * 			that uses requested library.
	 */
	<SOURCE, DESTINATION> ReflectionModelTransformerBuilder<SOURCE, DESTINATION> getBuilderFor(Class<?> library);

}
