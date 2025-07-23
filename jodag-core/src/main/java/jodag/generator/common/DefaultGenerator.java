package jodag.generator.common;


import jodag.generator.AbstractGenerator;
import jodag.generator.StringGenerator;


public class DefaultGenerator extends AbstractGenerator<String> {

    private static final DefaultGenerator INSTANCE = new DefaultGenerator();

    private DefaultGenerator() {
        super("defaults", String.class);
    }

    public static DefaultGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        return StringGenerator.getInstance().get();
    }
}
