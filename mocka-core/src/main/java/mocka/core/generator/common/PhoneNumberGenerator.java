package mocka.core.generator.common;

import mocka.core.generator.AbstractGenerator;
import mocka.core.generator.regex.RegexGenerator;


public class PhoneNumberGenerator extends AbstractGenerator<String> {

    private static final PhoneNumberGenerator INSTANCE = new PhoneNumberGenerator();
    private final RegexGenerator regexGenerator = RegexGenerator.getInstance();


    private PhoneNumberGenerator() {
        super("phone_number", String.class);
    }

    public static PhoneNumberGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        return regexGenerator.get("\\d{3}-\\d{4}-\\d{4}");
    }
}
