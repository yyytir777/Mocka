package jodag.generator.factory;

import jodag.exception.GeneratorException;
import jodag.generator.AbstractGenerator;
import jodag.generator.Generator;
import jodag.generator.common.*;
import jodag.generator.registrable.RegistrableGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GeneratorRegistry {

    protected static final Map<String, Generator<?>> GENERATOR_MAP = new ConcurrentHashMap<>();

    static {
        putGenerator(EmailGenerator.getInstance());
        putGenerator(NameGenerator.getInstance());
        putGenerator(LoremIpsumGenerator.getInstance());
        putGenerator(CountryGenerator.getInstance());
        putGenerator(PhoneNumberGenerator.getInstance());
        putGenerator(NetworkAddressGenerator.getInstance());
        putGenerator(DateGenerator.getInstance());
    }

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
     * Adds {@code RegistrableGenerator}
     * @param key unique identifier of the {@code RegistrableGenerator}
     * @param path the file path used as a source for the generator (must be unique)
     * @param type type of the data the RegistrableGenerator generates
     * @param <T> the type parameter of the Generator
     * @return the newly created {@code Generator}
     * @throws GeneratorException if a generator with the same key or path already exists
     */
    public static <T> void putGenerator(String key, String path, Class<T> type) {
        Generator<?> generator = GENERATOR_MAP.put(key, new RegistrableGenerator<>(key, path, type));
        if (generator != null) {
            throw new GeneratorException("Duplicate generator key: " + key);
        }
    }


    /**
     * Retrieves the {@code RegistrableGenerator} by the given key parameter, identifier of Generator. <br>
     *
     * @param key  unique identifier of the {@code RegistrableGenerator}
     * @param <T> the type parameter of the Generator
     * @return the {@code Generator} registered under the given key.
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
     * Retrieves the {@link RegistrableGenerator} for the given key. <br>
     * If RegistrableGenerator isn't exists, create new one with key, path, type. <br>
     * then return it.
     * @param key  unique identifier of the RegistrableGenerator
     * @param path  the path of the file used as a source of RegistrableGenerator
     * @param type  type of the data the RegistrableGenerator generates.
     * @param <T> the type parameter of the Generator
     * @return {@link Generator}
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
     * Removes all registered {@code RegistrableGenerator} instances. <br>
     * After calling this method, new generators must be registered.
     */
    public void clearRegistrableGenerator() {
        GENERATOR_MAP.clear();
    }

    /**
     * returns all the names of {@code RegistrableGenerator} instances.
     * @return a list of generator names.
     */
    public List<String> getRegistrableGeneratorNames() {
        return new ArrayList<>(GENERATOR_MAP.keySet());
    }

    /**
     *
     * @param key
     * @return
     */
    public Boolean existsRegistrableGenerator(String key) {
        return GENERATOR_MAP.containsKey(key);
    }
}
