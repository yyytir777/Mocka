package jodag.generator;

import jodag.generator.array.ArrayGenerator;
import jodag.generator.common.CountryGenerator;
import jodag.generator.common.EmailGenerator;
import jodag.generator.common.LoremIpsumGenerator;
import jodag.generator.common.NameGenerator;
import jodag.generator.datetime.CommonDateGenerator;
import jodag.generator.numeric.NumericGenerator;
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
    private static final CountryGenerator countryGenerator = CountryGenerator.getInstance();

    private static final PrimitiveGenerator primitiveGenerator = PrimitiveGenerator.getInstance();
    private static final NumericGenerator numericGenerator = NumericGenerator.getInstance();
    private static final CommonDateGenerator commonDateGenerator = CommonDateGenerator.getInstance();
    private static final ArrayGenerator arrayGenerator = ArrayGenerator.getInstance();

    private static final Map<String, Generator<?>> registableGenerator = new ConcurrentHashMap<>();


    public static StringGenerator string() {
        return stringGenerator;
    }

    public static EmailGenerator email() {
        return emailGenerator;
    }

    public static CountryGenerator country() {
        return countryGenerator;
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
    public static <T> Generator<T> getRegistableGenerator(String key, String path, Class<T> type) {
        Generator<T> generator = (Generator<T>) registableGenerator.get(key);
        if(generator == null) {
            generator = new RegisterableGenerator<>(key, path, type);
            registableGenerator.put(key, generator);
        }
        return generator;
    }
}
