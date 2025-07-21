package jodag.generator.common;


import jodag.generator.AbstractGenerator;

import java.util.Locale;

public class DefaultGenerator extends AbstractGenerator<String> {

    private static DefaultGenerator INSTANCE;

    private DefaultGenerator() {
        super("defaults", String.class);
    }

    public static synchronized DefaultGenerator getInstance() {
        if (INSTANCE == null) INSTANCE = new DefaultGenerator();
        return INSTANCE;
    }

    @Override
    public String get() {
        return randomProvider.nextCharacter(Locale.ENGLISH).toString();
    }
}
