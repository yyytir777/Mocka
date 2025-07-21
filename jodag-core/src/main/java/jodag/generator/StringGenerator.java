package jodag.generator;

import java.util.Random;

public class StringGenerator extends AbstractGenerator<String> {

    private static final StringGenerator INSTANCE = new StringGenerator();
    private final Random random = new Random();

    private StringGenerator() {
        super("string", String.class);
    }

    public static StringGenerator getInstance() {
        return INSTANCE;
    }

    public String get() {
        return "demo";
    }
}
