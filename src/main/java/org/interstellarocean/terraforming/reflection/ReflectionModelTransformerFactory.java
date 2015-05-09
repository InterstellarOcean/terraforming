/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.reflection;

/**
 * Entry point for {@link org.interstellarocean.terraforming.ModelTransformer} to reflection model transformers.
 *
 * {@link ReflectionModelTransformerFactory} along with {@link ReflectionModelTransformerProvider} uses factory and strategy patterns
 * to allow obtaining and using specific library provider.
 *
 * <h3>Example</h3>
 *
 * <blockquote><pre>
 * 	public class FooReflectionModelTransformerFactory implements ReflectionModelTransformerFactory {
 *
 * 		private final Collection&lt;ReflectionModelTransformerProvider&gt; providers;
 *
 * 		&#64;Inject // @Autowired, etc.
 * 		public FooReflectionModelTransformerFactory(Collection&lt;ReflectionModelTransformerProvider&gt; providers) {
 * 			this.providers = providers;
 * 		}
 *
 * 		&#64;Override
 * 		public ReflectionModelTransformerProvider getProvider(Class&lt;?&gt; library) {
 * 			return providers.stream().filter(provider -&gt; provider.provides(library)).findFirst().get();
 * 		}
 *
 * 	}
 * </pre></blockquote>
 *
 * @see org.interstellarocean.terraforming.reflection
 * @see ReflectionModelTransformerProvider
 *
 * @author Dariusz Wakuliński
 */
public interface ReflectionModelTransformerFactory {

	ReflectionModelTransformerProvider getProvider(Class<?> library);

}
