/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
/**
 * Support for model transformers using reflection libraries.
 *
 * <h2>References</h2>
 * <ul>
 * <li><a href="http://www.javatronic.fr/articles/2014/05/21/java_bean_mapping_is_wrong_lets_fix_it.html">Java Bean mapping is wrong lets fix it @javatronic.fr</a>
 * <li><a href="http://blog.sokolenko.me/2013/05/dozer-vs-orika-vs-manual.html">Dozer vs. orika vs. manual @sokolenko.me</a>
 * <li><a href="http://www.javacodegeeks.com/2013/10/java-object-to-object-mapper.html">Java object-to-object mapper @javacodegeeks</a>
 * </ul>
 *
 * <h2>Description</h2>
 * Set of interfaces allowing clean separation of {@link org.interstellarocean.terraforming.ModelTransformer}s code
 * from external reflection transforming libraries.
 * <p>
 * {@link org.interstellarocean.terraforming.ModelTransformer} implementors should follow the rules:
 * </p>
 *
 * <ol>
 *
 * <li>
 * {@link org.interstellarocean.terraforming.ModelTransformer} should use
 * {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformerFactory}'s factory and strategy patterns
 * to obtain and use requested library provider.
 * <p>
 * {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformerProvider} interface provides a builder allowing
 * creation and configuration of {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformer} instances in a library-independent way.
 * The provider implementation should isolate the library allowing easy change of used library
 * in {@link org.interstellarocean.terraforming.ModelTransformer} implementations.
 * </p>
 * </li>
 *
 * <li>
 * {@link org.interstellarocean.terraforming.ModelTransformer} should use obtained
 * {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformerProvider#builder()} method to actually create and configure
 * {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformer} instance.
 * The builder method invocation should be parameterized with types of requested transformer.
 * Provider builder constructed instances must be thread-safe.
 * </li>
 *
 * <li>
 * {@link org.interstellarocean.terraforming.ModelTransformer} should use built
 * {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformer} instance to perform actual reflection transforming.
 * </li>
 *
 * </ol>
 *
 * <h3>Example</h3>
 *
 * <blockquote><pre>
 * public class FooToDtoModelTransformer implements ToDtoModelTransformer&lt;FooDomain, FooDto&gt; {
 *
 * 	private static final Class&lt;?&gt; LIBRARY = org.bar.Foo.class;
 *
 * 	private final ReflectionModelTransformerFactory reflectionModelTransformerFactory;
 *
 * 	private ReflectionModelTransformer&lt;FooDomain, FooDto&gt; toDtoReflectionModelTransformer;
 *
 * 	&#64;Inject // @Autowired, etc.
 * 	public FooModelTransformer(ReflectionModelTransformerFactory reflectionModelTransformerFactory) {
 * 		this.reflectionModelTransformerFactory = reflectionModelTransformerFactory;
 * 	}
 *
 * 	&#64;PostConstruct
 * 	void buildReflectionModelTransformer() {
 * 		ReflectionModelTransformerProvider provider = reflectionModelTransformerFactory.getProvider(LIBRARY);
 * 		ReflectionModelTransformerProvider.Builder&lt;FooDomain, FooDto&gt; builder = provider.builder();
 * 		toDtoReflectionModelTransformer = builder
 * 				.from(FooDomain.class)
 * 				.to(FooDto.class)
 * 				.excludeFields(emptyList())
 * 				.build();
 * 	}
 *
 * 	&#64;Override
 * 	public FooDto toDto(FooDomain domain) {
 * 		return toDtoReflectionModelTransformer.from(domain).to(new FooDto());
 * 	}
 *
 * }
 * </pre></blockquote>
 *
 * @see org.interstellarocean.terraforming.ModelTransformer
 * @see org.interstellarocean.terraforming.reflection.ReflectionModelTransformerFactory
 * @see org.interstellarocean.terraforming.reflection.ReflectionModelTransformerProvider
 * @see org.interstellarocean.terraforming.reflection.ReflectionModelTransformer
 *
 * @author Dariusz Wakuliński
 */
package org.interstellarocean.terraforming.reflection;
