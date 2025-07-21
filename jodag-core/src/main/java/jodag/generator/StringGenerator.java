package jodag.generator;

public class StringGenerator extends AbstractGenerator<String> {

    private static StringGenerator INSTANCE;

    private StringGenerator() {
        super("string", String.class);
    }

    public static synchronized StringGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StringGenerator();
        }
        return INSTANCE;
    }

    public String get() {
        return "demo";
    }
}
