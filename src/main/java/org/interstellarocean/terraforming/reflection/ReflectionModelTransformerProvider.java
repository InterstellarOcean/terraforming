/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reflection;

import java.util.Collection;

/**
 * Provides builder allowing creation and configuration of {@link ReflectionModelTransformer} instances in a library-independent way.
 * The provider implementation should isolate the library allowing easy change of used library
 * in {@link org.interstellarocean.terraforming.ModelTransformer} implementations.
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
 * 	public &lt;SOURCE, DESTINATION&gt; Builder&lt;SOURCE, DESTINATION&gt; builder() {
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
 * @see Builder
 *
 * @author Dariusz Wakuliński
 */
public interface ReflectionModelTransformerProvider {

	/**
	 * Tests if the library class is supported by the provider.
	 *
	 * Allows selecting requested strategy by {@link ReflectionModelTransformerFactory}.
	 *
	 * @param library Class marking implementation of a strategy that use specific reflection transforming library.
	 * 		Suitable for use by factory to select requested strategy.
	 * 		Actually may be any type (class, interface, etc.), but recommended is a type from the implemented library.
	 * 		Should be unique across implementations (strategies).
	 * @return True if the provider supports the library class.
	 */
	boolean provides(Class<?> library);

	/**
	 * Creates builder allowing creation and configuration of {@link ReflectionModelTransformer} instances.
	 *
	 * @see Builder
	 *
	 * @param <SOURCE> Type to transform from. Used to configure {@link ReflectionModelTransformer}.
	 * @param <DESTINATION> Type to transform to. Used to configure {@link ReflectionModelTransformer}.
	 * @return {@link Builder} allowing creation and configuration of a {@link ReflectionModelTransformer}.
	 */
	<SOURCE, DESTINATION> Builder<SOURCE, DESTINATION> builder();

	/**
	 * Builder allowing creation and configuration of {@link ReflectionModelTransformer} instances.
	 *
	 * @param <SOURCE> Type to transform from. Used to configure {@link ReflectionModelTransformer}.
	 * @param <DESTINATION> Type to transform to. Used to configure {@link ReflectionModelTransformer}.
	 */
	interface Builder<SOURCE, DESTINATION> {

		/**
		 * Configures type to transform from.
		 *
		 * @see To
		 *
		 * @param sourceType Type to transform from. Used to configure {@link ReflectionModelTransformer}.
		 * @return Builder's next step fluent interface.
		 */
		To<SOURCE, DESTINATION> from(Class<SOURCE> sourceType);

		/**
		 * Builder allowing creation and configuration of {@link ReflectionModelTransformer} instances.
		 *
		 * @param <SOURCE> Type to transform from. Used to configure {@link ReflectionModelTransformer}.
		 * @param <DESTINATION> Type to transform to. Used to configure {@link ReflectionModelTransformer}.
		 */
		interface To<SOURCE, DESTINATION> {

			/**
			 * Configures type to transform to.
			 *
			 * @see ExcludeFields
			 *
			 * @param destinationType Type to transform to. Used to configure {@link ReflectionModelTransformer}.
			 * @return Builder's next step fluent interface.
			 */
			ExcludeFields<SOURCE, DESTINATION> to(Class<DESTINATION> destinationType);

		}

		/**
		 * Builder allowing creation and configuration of {@link ReflectionModelTransformer} instances.
		 *
		 * @param <SOURCE> Type to transform from. Used to configure {@link ReflectionModelTransformer}.
		 * @param <DESTINATION> Type to transform to. Used to configure {@link ReflectionModelTransformer}.
		 */
		interface ExcludeFields<SOURCE, DESTINATION> {

			/**
			 * Configures fields to be excluded from transformation.
			 *
			 * That should include all fields that are not supported by the given library. Excluded field transforming should be instead performed
			 * by the {@link org.interstellarocean.terraforming.ModelTransformer} calling the {@link ReflectionModelTransformer}.
			 *
			 * <p>
			 * <i>Note:</i> Never pass null if there are no fields to exclude, pass your favorite empty collection instead,
			 * e.g. {@link java.util.Collections#emptySet()}.
			 * </p>
			 *
			 * @see Build
			 *
			 * @param excludeFieldNames Names of the fields to exclude.
			 * @return Builder's next step fluent interface.
			 */
			Build<SOURCE, DESTINATION> excludeFields(Collection<String> excludeFieldNames);

		}

		/**
		 * Builder allowing creation and configuration of {@link ReflectionModelTransformer} instances.
		 *
		 * @param <SOURCE> Type to transform from. Used to configure {@link ReflectionModelTransformer}.
		 * @param <DESTINATION> Type to transform to. Used to configure {@link ReflectionModelTransformer}.
		 */
		interface Build<SOURCE, DESTINATION> {

			/**
			 * Builds the configured {@link ReflectionModelTransformer}.
			 *
			 * @return Thread-safe instance of {@link ReflectionModelTransformer} ready to be used for transforming.
			 */
			ReflectionModelTransformer<SOURCE, DESTINATION> build();

		}

	}

}
