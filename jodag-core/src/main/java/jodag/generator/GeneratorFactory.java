package jodag.generator;

import jodag.exception.GeneratorException;
import jodag.generator.array.ArrayGenerator;
import jodag.generator.common.EmailGenerator;
import jodag.generator.common.LoremIpsumGenerator;
import jodag.generator.common.NameGenerator;
import jodag.generator.datetime.CommonDateGenerator;
import jodag.generator.numeric.NumericGenerator;
import jodag.generator.primitive.PrimitiveGenerator;
import jodag.generator.registable.RegisterableGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static jodag.generator.StringGenerator.getInstance;

public class GeneratorFactory {

    private static final StringGenerator stringGenerator = getInstance();
    private static final EmailGenerator emailGenerator = EmailGenerator.getInstance();
    private static final NameGenerator nameGenerator = NameGenerator.getInstance();
    private static final LoremIpsumGenerator loremIpsumGenerator = LoremIpsumGenerator.getInstance();

    private static final PrimitiveGenerator primitiveGenerator = PrimitiveGenerator.getInstance();
    private static final NumericGenerator numericGenerator = NumericGenerator.getInstance();
    private static final CommonDateGenerator commonDateGenerator = CommonDateGenerator.getInstance();
    private static final ArrayGenerator arrayGenerator = ArrayGenerator.getInstance();

    private static final Map<String, RegisterableGenerator<?>> registableGenerators = new ConcurrentHashMap<>();


    public static StringGenerator string() {
        return stringGenerator;
    }

    public static EmailGenerator email() {
        return emailGenerator;
    }

    public static NameGenerator name() {
        return nameGenerator;
    }

    public static LoremIpsumGenerator loremIpsum() {
        return loremIpsumGenerator;
    }

    public static PrimitiveGenerator primitive() {
        return primitiveGenerator;
    }

    public static NumericGenerator numeric() {return numericGenerator;}

    public static CommonDateGenerator dateTime() {return commonDateGenerator;}

    public static ArrayGenerator array() {return arrayGenerator;}


    @SuppressWarnings("unchecked")
    public static <T> RegisterableGenerator<T> getRegistableGenerator(String key, String path, Class<T> type) {
        RegisterableGenerator<T> generator = (RegisterableGenerator<T>) registableGenerators.get(key);
        if(generator == null) {
            generator = new RegisterableGenerator<>(key, path, type);
            registableGenerators.put(key, generator);
        }
        return generator;
    }

    @SuppressWarnings("unchecked")
    public static <T> RegisterableGenerator<T> getRegistableGenerator(String key) {
        RegisterableGenerator<T> generator = (RegisterableGenerator<T>) registableGenerators.get(key);
        if(generator == null) {
            throw new GeneratorException("No generator for key: " + key);
        }
        return generator;
    }

    @SuppressWarnings("unchecked")
    public static <T> RegisterableGenerator<T> getRegistableGenerator(String path, Class<T> type) {
        for (RegisterableGenerator<?> generator : registableGenerators.values()) {
            if(generator.getPath().equals(path)) {
                return (RegisterableGenerator<T>) generator;
            }
        }
        throw new GeneratorException("Cannot found Generator for path");
    }

    public static <T> RegisterableGenerator<T> register(String key, String path, Class<T> type) {
        RegisterableGenerator<T> created = new RegisterableGenerator<>(key, path, type);

        Generator<?> generator = registableGenerators.putIfAbsent(key, created);
        if(generator != null) {
            throw new GeneratorException("Duplicate key: " + key);
        }
        return created;
    }

    public static void clearRegistableGenerator() {
        registableGenerators.clear();
    }

    public static List<String> getRegistableGeneratorNames() {
        return new ArrayList<>(registableGenerators.keySet());
    }
}
