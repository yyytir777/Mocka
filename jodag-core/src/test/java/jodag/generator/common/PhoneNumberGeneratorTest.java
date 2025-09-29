package jodag.generator.common;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PhoneNumberGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    void generate() {
        PhoneNumberGenerator phoneNumberGenerator = generatorFactory.asPhoneNumber();
        String s = phoneNumberGenerator.get();
    }
}