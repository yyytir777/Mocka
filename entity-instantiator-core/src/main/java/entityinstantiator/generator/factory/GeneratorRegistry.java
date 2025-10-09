package entityinstantiator.generator.factory;

import entityinstantiator.exception.GeneratorException;
import entityinstantiator.generator.AbstractGenerator;
import entityinstantiator.generator.Generator;
import entityinstantiator.generator.array.ByteArrayGenerator;
import entityinstantiator.generator.common.*;
import entityinstantiator.generator.datetime.DateTimeGenerator;
import entityinstantiator.generator.datetime.LegacyDateGenerator;
import entityinstantiator.generator.datetime.SqlDateGenerator;
import entityinstantiator.generator.numeric.BigDecimalGenerator;
import entityinstantiator.generator.numeric.BigIntegerGenerator;
import entityinstantiator.generator.primitive.*;
import entityinstantiator.generator.regex.RegexGenerator;
import entityinstantiator.generator.registrable.RegistrableGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@code GeneratorRegistry} is central static registry that manages all {@link Generator}
 * instances within the entity-instantiator-core library. <br>
 * <p>
 * It provides static methods to register, retrieve, and manage generators identified by unique keys.
 * The registry is backed by a thread-safe {@link ConcurrentHashMap}, ensuring safe concurrent access. <br>
 *
 * <p>By default, several common generators (e.g. {@link EmailGenerator}, {@link NameGenerator},
 * {@link CountryGenerator}) are registered in the static initializer.
 */
public class GeneratorRegistry {

    protected static final Map<String, Generator<?>> GENERATOR_MAP = new ConcurrentHashMap<>();

    static {
        // CommonFactory
        putGenerator(EmailGenerator.getInstance());
        putGenerator(NameGenerator.getInstance());
        putGenerator(LoremIpsumGenerator.getInstance());
        putGenerator(CountryGenerator.getInstance());
        putGenerator(PhoneNumberGenerator.getInstance());
        putGenerator(NetworkAddressGenerator.getInstance());
        putGenerator(DateGenerator.getInstance());

        // PrimitiveFactory
        putGenerator(BooleanGenerator.getInstance());
        putGenerator(ByteGenerator.getInstance());
        putGenerator(ShortGenerator.getInstance());
        putGenerator(IntegerGenerator.getInstance());
        putGenerator(LongGenerator.getInstance());
        putGenerator(CharacterGenerator.getInstance());
        putGenerator(DoubleGenerator.getInstance());
        putGenerator(FloatGenerator.getInstance());
        putGenerator(StringGenerator.getInstance());

        // ExtendedFactory
        putGenerator(BigIntegerGenerator.getInstance());
        putGenerator(BigDecimalGenerator.getInstance());
        putGenerator(DateTimeGenerator.getInstance());
        putGenerator(LegacyDateGenerator.getInstance());
        putGenerator(SqlDateGenerator.getInstance());
        putGenerator(ByteArrayGenerator.getInstance());
        putGenerator(CharacterGenerator.getInstance());

        // RegexFactory
        putGenerator(RegexGenerator.getInstance());
    }

    /**
     * Registers the given {@link Generator} into the Registry.
     * <p>
     * if {@link Generator} implements {@link RegistrableGenerator}, the Generator put as RegistrableGenerator.
     * if {@link Generator} implements {@link AbstractGenerator}, the Generator put as Generator extends AbstractGenerator.
     *
     * @param generator the generator to register
     * @param <T> the type of value to be generated
     * @throws UnsupportedOperationException if the generator type is not supported.
     */
    public static <T> void putGenerator(T generator) {
        if (generator instanceof RegistrableGenerator<?> registrableGenerator) {
            GENERATOR_MAP.put(registrableGenerator.getKey(), registrableGenerator);
        } else if (generator instanceof AbstractGenerator<?> abstractGenerator) {
            GENERATOR_MAP.put(abstractGenerator.getKey(), abstractGenerator);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Creates {@code RegistrableGenerator} by key, path, and type parameters and registers the Generator.
     * @param key  unique identifier of the generator
     * @param path file path used as the source for the generator
     * @param type the type of data the generator produces
     * @param <T>  the type parameter of the generator
     * @throws GeneratorException if a generator with the same key already exists
     */
    public static <T> void putGenerator(String key, String path, Class<T> type) {
        Generator<?> generator = GENERATOR_MAP.put(key, new RegistrableGenerator<>(key, path, type));
        if (generator != null) {
            throw new GeneratorException("Duplicate generator key: " + key);
        }
    }

    /**
     * Retrieves a registered {@link Generator} by its unique key.
     * @param key unique identifier of the generator
     * @param <T> the type parameter of the generator
     * @return the {@link Generator} registered under the given key
     * @throws GeneratorException if no generator is found for the given key
     */
    @SuppressWarnings("unchecked")
    public static <T> Generator<T> getGenerator(String key) {
        Generator<?> generator = GENERATOR_MAP.get(key);
        if (generator == null) {
            throw new GeneratorException("No generator for key: " + key);
        }
        return (Generator<T>) generator;
    }

    /**
     * Retrieves a {@link RegistrableGenerator} for the given key.
     * <p>
     * If a generator with the specified key does not exist, a new one is created
     * with the provided {@code path} and {@code type}, registered, and then returned.
     *
     * @param key  unique identifier of the generator
     * @param path file path used as the source for the generator
     * @param type the type of data the generator produces
     * @param <T>  the type parameter of the generator
     * @return the existing or newly created {@link Generator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Generator<T> getGenerator(String key, String path, Class<T> type) {
        Generator<T> generator = (Generator<T>) GENERATOR_MAP.get(key);
        if (generator == null) {
            generator = new RegistrableGenerator<>(key, path, type);
            GENERATOR_MAP.put(key, generator);
        }
        return generator;
    }

    /**
     * Removes all registered RegistrableGenerators from the registry.
     * Built-in generators (e.g. {@code EmailGenerator}, {@code NameGenerator})
     * are not affected and remain registered.
     *
     * <p>After calling this method, any custom {@code RegistrableGenerator}
     * must be registered again before use.
     */
    public static void clearRegistrableGenerator() {
        GENERATOR_MAP.entrySet().removeIf(entry -> entry.getValue() instanceof RegistrableGenerator<?>);
    }

    /**
     * Returns the list of all keys (names) of registered generators.
     * @return a list of generator keys currently registered
     */
    public static List<String> getGeneratorNames() {
        return new ArrayList<>(GENERATOR_MAP.keySet());
    }

    /**
     * Checks whether a generator with the given key exists in the registry.
     *
     * @param key the unique identifier of the generator
     * @return {@code true} if a generator is registered under the given key,
     *         {@code false} otherwise
     */
    public static Boolean existsRegistrableGenerator(String key) {
        return GENERATOR_MAP.containsKey(key);
    }
}
