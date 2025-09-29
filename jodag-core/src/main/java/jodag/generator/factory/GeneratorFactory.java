package jodag.generator.factory;


import jodag.generator.Generator;

/**
 * The {@code GeneratorFactory} is the core class of the jodag-core library. <br>
 * It provides access to default Generators via {@code as~} methods
 * defined in the implemented factory interfaces ({@link PrimitiveFactory},
 * {@link CommonFactory}, {@link ExtendedFactory}). <br>
 * <p>
 * Additionally, it manages {@link Generator} instances
 * that can be registered and retrieved dynamically by key or path.
 * </p>
 */
public class GeneratorFactory extends GeneratorRegistry implements PrimitiveFactory, CommonFactory, ExtendedFactory, RegexFactory {

}
