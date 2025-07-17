package jodag;

import jodag.generator.Generator;
import org.junit.jupiter.api.Test;

public class JodagV2Test {

    @Test
    void test() {
        // string 반환
        StringGenerator stringGenerator = GeneratorFactory.string();
        String string = stringGenerator.get();
        String string = stringGenerator.get(10);


        // email 반환
        EmailGenerator emailGenerator = GeneratorFactory.email();
        String email = emailGenerator.get();

        // name 반환
        NameGenerator nameGenerator = GeneratorFactory.name();
        String name = nameGenerator.get();

        // lorem ipsum 반환
        LoremIpsumGenerator loremIpsumGenerator = GeneratorFactory.loremIpsum();
        String loremIpsum = loremIpsumGenerator.get();

        PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

        Boolean bool = primitiveGenerator.getBoolean();
        Byte b = primitiveGenerator.getByte();
        Short s = primitiveGenerator.getShort();
        Character c = primitiveGenerator.getCharcter();
        Integer i = primitiveGenerator.getInteger();
        Long l = primitiveGenerator.getLong();
        Float f = primitiveGenerator.getFloat();
        Double d = primitiveGenerator.getDouble();
    }
}
