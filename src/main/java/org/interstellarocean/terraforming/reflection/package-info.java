/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
/**
 * Support for model transformers using reflection libraries.
 * Model transforming using reflection shall be considered a supplementary technology, where the usual use of data structures' accessors and mutators
 * (or fields access) is most robust and is the preferred way to implement model transforming.
 * Implementors are discouraged from using reflection transforming library's advanced configurations and techniques as these are frequently not robust,
 * not portable between libraries, and may lead to bugs that are hard to track.
 * All fields that cannot be transformed by simple reflection shall be just excluded from reflection transforming and transformed
 * by the base model transformer instead.
 * And don't forget to write tests for your model transformers regardless it uses reflection or not.
 *
 * <h2>References</h2>
 * <ul>
 * <li><a href="http://www.javatronic.fr/articles/2014/05/21/java_bean_mapping_is_wrong_lets_fix_it.html">Java Bean Mapping is wrong,
 * 		let's fix it! @javatronic.fr</a>
 * <li><a href="http://blog.sokolenko.me/2013/05/dozer-vs-orika-vs-manual.html">Dozer vs Orika vs Manual @sokolenko.me</a>
 * <li><a href="http://www.javacodegeeks.com/2013/10/java-object-to-object-mapper.html">Java Object to Object Mapper @javacodegeeks</a>
 * </ul>
 *
 * <h2>Description</h2>
 * Set of interfaces allowing clean separation of {@link org.interstellarocean.terraforming.ModelTransformer}s code
 * from external reflection transforming libraries.
 * <p>
 * {@link org.interstellarocean.terraforming.ModelTransformer} implementors should follow the guidelines:
 * </p>
 *
 * <ol>
 *
 * <li>
 * {@link org.interstellarocean.terraforming.ModelTransformer} should use
 * {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformerFactory}'s factory and strategy patterns
 * to obtain and use requested library provider's {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformer} builder.
 * The factory's builder method invocation should be parameterized with requested types of transformer to be build.
 * <p>
 * {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformerProvider} interface provides a builder allowing
 * creation and configuration of {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformer} instances in a library-independent way.
 * The provider implementation should isolate the library allowing easy change of used library
 * in {@link org.interstellarocean.terraforming.ModelTransformer} implementations.
 * {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformer} instances built by the provider implementation must be thread-safe.
 * </p>
 * </li>
 *
 * <li>
 * {@link org.interstellarocean.terraforming.ModelTransformer} should use obtained
 * {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformerBuilder} to actually create and configure
 * {@link org.interstellarocean.terraforming.reflection.ReflectionModelTransformer} instance.
 * Instances returned by the builder are thread-safe.
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
 * 	// This is the only contact with actual reflection transforming library. Easy to change if needed!
 * 	private static final Class&lt;?&gt; FOO_LIBRARY = org.bar.Foo.class;
 *
 * 	private final ReflectionModelTransformerFactory reflectionModelTransformerFactory;
 *
 * 	private ReflectionModelTransformer&lt;FooDomain, FooDto&gt; toDtoReflectionModelTransformer;
 *
 * 	&#64;Inject // @Autowired, etc.
 * 	public FooToDtoModelTransformer(ReflectionModelTransformerFactory reflectionModelTransformerFactory) {
 * 		this.reflectionModelTransformerFactory = reflectionModelTransformerFactory;
 * 	}
 *
 * 	&#64;PostConstruct
 * 	void buildReflectionModelTransformer() {
 * 		ReflectionModelTransformerBuilder&lt;FooDomain, FooDto&gt; builder = reflectionModelTransformerFactory.getBuilderFor(FOO_LIBRARY);
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
 * @see org.interstellarocean.terraforming.reflection.ReflectionModelTransformerBuilder
 * @see org.interstellarocean.terraforming.reflection.ReflectionModelTransformerProvider
 * @see org.interstellarocean.terraforming.reflection.ReflectionModelTransformer
 *
 * @see <a href="https://github.com/InterstellarOcean/terraforming/wiki/Reflection-model-transforming">Documentation Wiki</a>
 *
 * @author Dariusz Wakuliński
 */
package org.interstellarocean.terraforming.reflection;
