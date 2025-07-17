package jodag;

import jodag.generator.Generator;
import jodag.generator.GeneratorFactory;
import jodag.generator.common.EmailGenerator;
import jodag.generator.common.LoremIpsumGenerator;
import jodag.generator.common.NameGenerator;
import jodag.generator.primitive.PrimitiveGenerator;
import org.junit.jupiter.api.Test;

public class JodagMainTest {

    @Test
    void test() {
        // string 반환
//        StringGenerator stringGenerator = GeneratorFactory.string();
//        String string = stringGenerator.get();
//        String string = stringGenerator.get(10);


        // email 반환
        EmailGenerator emailGenerator = GeneratorFactory.email();
        String email = emailGenerator.get();
        System.out.println("email = " + email);

        // name 반환
        NameGenerator nameGenerator = GeneratorFactory.name();
        String name = nameGenerator.get();
        System.out.println("name = " + name);

        // lorem ipsum 반환
        LoremIpsumGenerator loremIpsumGenerator = GeneratorFactory.loremIpsum();
        String loremIpsum = loremIpsumGenerator.get();

        PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

        Boolean bool = primitiveGenerator.getBoolean();
        Byte b = primitiveGenerator.getByte();
        Short s = primitiveGenerator.getShort();
        Character c = primitiveGenerator.getCharacter();
        Integer i = primitiveGenerator.getInteger();
        Long l = primitiveGenerator.getLong();
        Float f = primitiveGenerator.getFloat();
        Double d = primitiveGenerator.getDouble();

        Generator<String> generator = GeneratorFactory.getRegistableGenerator("asdf", "name.txt", String.class);
        String s1 = generator.get();
        System.out.println("s1 = " + s1);
    }
}
