/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reflection;

/**
 * Provides builder allowing creation and configuration of {@link ReflectionModelTransformer} instances in a library-independent way.
 * The provider implementation should isolate the library allowing easy change of used library
 * in {@link org.interstellarocean.terraforming.ModelTransformer} implementations.
 *
 * <p>
 * <i>Note</i> that {@link ReflectionModelTransformer} instances built by the provider implementation must be thread-safe.
 * </p>
 *
 * <p>
 * Suitable to implement the strategy pattern and for use with factory.
 * </p>
 *
 * <h3>Example</h3>
 *
 * <blockquote><pre>
 * public class FooModelTransformerProvider implements ReflectionModelTransformerProvider {
 *
 * 	private final FooFactory fooFactory;
 *
 * 	&#64;Inject // @Autowired, etc.
 * 	public FooModelTransformerProvider(FooFactory fooFactory) {
 * 		this.fooFactory = fooFactory;
 * 	}
 *
 * 	&#64;Override
 * 	public boolean provides(Class&lt;?&gt; library) {
 * 		return org.bar.Foo.class.equals(library);
 * 	}
 *
 * 	&#64;Override
 * 	public &lt;SOURCE, DESTINATION&gt; ReflectionModelTransformerBuilder&lt;SOURCE, DESTINATION&gt; builder() {
 * 		return sourceType -&gt; destinationType -&gt; excludeFieldNames -&gt; () -&gt; {
 *
 * 			FooConfigurator&lt;SOURCE, DESTINATION&gt; fooConfigurator = fooFactory.classTransformerConfigurator(sourceType, destinationType);
 * 			excludeFieldNames.forEach(fieldName -&gt; fooConfigurator.exclude(fieldName));
 * 			fooConfigurator.register();
 *
 * 			TransformerFacade&lt;SOURCE, DESTINATION&gt; reflectionModelTransformer = fooFactory.getTransformerFacade(sourceType, destinationType);
 * 			return source -&gt; destination -&gt; {
 * 				reflectionModelTransformer.transform(source, destination);
 * 				return destination;
 * 			};
 *
 * 		};
 * 	}
 *
 * }
 * </pre></blockquote>
 *
 * @see org.interstellarocean.terraforming.reflection
 * @see ReflectionModelTransformer
 * @see ReflectionModelTransformerBuilder
 *
 * @author Dariusz Wakuliński
 */
public interface ReflectionModelTransformerProvider {

	/**
	 * Tests if the library class is supported by the provider.
	 * Allows selecting requested strategy by {@link ReflectionModelTransformerFactory}.
	 *
	 * @param library A class marking the implementation of a strategy that use specific reflection transforming library.
	 * 		Suitable for use by factory to select requested strategy.
	 * 		Actually may be any type (class, interface, etc.), but recommended is a type from the implemented library.
	 * 		Should be unique across implementations (strategies).
	 * @return True if the provider supports the library class.
	 */
	boolean provides(Class<?> library);

	/**
	 * Creates {@link ReflectionModelTransformerBuilder} allowing creation and configuration of a {@link ReflectionModelTransformer} instance
	 * that uses provided library.
	 *
	 * @see ReflectionModelTransformerBuilder
	 *
	 * @param <SOURCE> Type to transform from. Used to parameterize {@link ReflectionModelTransformerBuilder}.
	 * @param <DESTINATION> Type to transform to. Used to parameterize {@link ReflectionModelTransformerBuilder}.
	 * @return {@link ReflectionModelTransformerBuilder} allowing creation and configuration of a {@link ReflectionModelTransformer} instance
	 * 			that uses provided library.
	 */
	<SOURCE, DESTINATION> ReflectionModelTransformerBuilder<SOURCE, DESTINATION> builder();

}
