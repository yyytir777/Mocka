package entityinstantiator.generator.factory;


import entityinstantiator.generator.Generator;

/**
 * The {@code GeneratorFactory} is the central entry point of the entity-instantiator-core library. <br>
 * It is responsible for providing access to the built-in {@link Generator} implementations
 * and for managing their registration. <br>
 *
 * <p>Default generators can be accessed through the {@code as~} methods defined in the
 * implemented factory interfaces, such as:
 * <ul>
 *   <li>{@link PrimitiveFactory} – provides primitive type generators (e.g. Integer, Long, Double, String so on.).</li>
 *   <li>{@link CommonFactory} – provides common data generators (e.g. Email, Name, Country).</li>
 *   <li>{@link ExtendedFactory} – provides extended type generators (SQL supported) (e.g. BigInteger, DateTime).</li>
 *   <li>{@link RegexFactory} – provides regex-based string generators.</li>
 * </ul>
 *
 * <p>This class extends {@link GeneratorRegistry}, which manages all registered generators.
 * By default, commonly used generators are automatically registered, but additional custom
 * generators can also be registered manually if needed.
 *
 * <h3>Usage Example</h3>
 * <pre>{@code
 * GeneratorFactory generatorFactory = new GeneratorFactory();
 *
 * // Get instance of Generator by `as~` method
 * IntegerGenerator integerGenerator = factory.asInteger();
 * // Generate a random integer
 * Integer integer = integerGenerator.get();
 *
 * // Generate a random email
 * String email = generatorFactory.asEmail().get();
 *
 * // Generate a string based on a regex pattern
 * String matched = generatorFactory.asRegex().get("\\d{3}-[A-Z]{2}");
 * // ex) matched = 123-AB
 * }</pre>
 *
 * <p>Note: {@code GeneratorFactory} instances are lightweight and can be created multiple times,
 * but in most cases a single shared instance is sufficient.
 */
public class GeneratorFactory extends GeneratorRegistry implements PrimitiveFactory, CommonFactory, ExtendedFactory, RegexFactory {

}
