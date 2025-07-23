package jodag.generator;

import jodag.generator.common.EmailGenerator;
import jodag.generator.common.LoremIpsumGenerator;
import jodag.generator.common.NameGenerator;
import jodag.generator.primitive.PrimitiveGenerator;
import jodag.generator.registable.RegisterableGenerator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static jodag.generator.StringGenerator.getInstance;

public class GeneratorFactory {

    private static final StringGenerator stringGenerator = getInstance();
    private static final EmailGenerator emailGenerator = EmailGenerator.getInstance();
    private static final NameGenerator nameGenerator = NameGenerator.getInstance();
    private static final LoremIpsumGenerator loremIpsumGenerator = LoremIpsumGenerator.getInstance();
    private static final PrimitiveGenerator primitiveGenerator = PrimitiveGenerator.getInstance();

    private static final Map<String, Generator<?>> registableGenerator = new ConcurrentHashMap<>();


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

    @SuppressWarnings("unchecked")
    public static <T> Generator<T> getRegistableGenerator(String key, String path, Class<T> type) {
        Generator<T> generator = (Generator<T>) registableGenerator.get(key);
        if(generator == null) {
            generator = new RegisterableGenerator<>(key, path, type);
            registableGenerator.put(key, generator);
        }
        return generator;
    }

    public static GeneratorInfo<?> getRandomProvider() {
        return GeneratorInfo.from(getInstance());
    }
}
