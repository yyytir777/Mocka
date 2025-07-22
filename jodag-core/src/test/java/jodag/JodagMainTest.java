package jodag;

import jodag.generator.Generator;
import jodag.generator.GeneratorFactory;
import jodag.generator.StringGenerator;
import jodag.generator.common.EmailGenerator;
import jodag.generator.common.LoremIpsumGenerator;
import jodag.generator.common.NameGenerator;
import jodag.generator.primitive.PrimitiveGenerator;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

public class JodagMainTest {

    @Test
    void test() {
//         string 반환
        StringGenerator stringGenerator = GeneratorFactory.string();
        String string = stringGenerator.get();
        System.out.println("string = " + string);
        String string1 = stringGenerator.get(15);
        System.out.println("string1 = " + string1);


        // email 반환
        EmailGenerator emailGenerator = GeneratorFactory.email();
        String email = emailGenerator.get();
        Pattern pattern = Pattern.compile("\\w+@\\w+\\.\\w+");
        assertThat(pattern.matcher(email).matches()).isTrue();

        // name 반환
        NameGenerator nameGenerator = GeneratorFactory.name();
        String name = nameGenerator.get();
        assertThat(name).isNotEmpty();

        // lorem ipsum 반환
        LoremIpsumGenerator loremIpsumGenerator = GeneratorFactory.loremIpsum();
        String loremIpsum = loremIpsumGenerator.get();
        assertThat(loremIpsum).isNotEmpty();

        PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

        Boolean bool = primitiveGenerator.getBoolean();
        assertThat(bool).isIn(Boolean.TRUE, Boolean.FALSE);

        Byte b = primitiveGenerator.getByte();
        assertThat(b).isBetween(Byte.MIN_VALUE, Byte.MAX_VALUE);

        Short s = primitiveGenerator.getShort();
        assertThat(s).isBetween(Short.MIN_VALUE, Short.MAX_VALUE);

        Character c = primitiveGenerator.getCharacter();
        assertThat(c).isBetween(Character.MIN_VALUE, Character.MAX_VALUE);

        Integer i = primitiveGenerator.getInteger();
        assertThat(i).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);

        Long l = primitiveGenerator.getLong();
        assertThat(l).isBetween(Long.MIN_VALUE, Long.MAX_VALUE);

        Float f = primitiveGenerator.getFloat();
        assertThat(f).isBetween(Float.MIN_VALUE, Float.MAX_VALUE);

        Double d = primitiveGenerator.getDouble();
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);

        Generator<String> generator = GeneratorFactory.getRegistableGenerator("asdf", "name.txt", String.class);
        String s1 = generator.get();
        assertThat(s1).isNotEmpty();
    }
}
