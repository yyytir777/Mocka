package jodag.generator.common;


import jodag.generator.AbstractGenerator;
import jodag.generator.Generator;
import jodag.generator.StringGenerator;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DefaultGenerator extends AbstractGenerator<String> implements StringGenerator {

    private static DefaultGenerator INSTANCE;

    private DefaultGenerator() {
        super("defaults", String.class);
    }

    public static synchronized DefaultGenerator getInstance() {
        if (INSTANCE == null) INSTANCE = new DefaultGenerator();
        return INSTANCE;
    }

    @Override
    public String get(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(randomProvider.nextCharacter(Locale.ENGLISH));
        }

        return sb.toString();
    }

    @Override
    public String get() {
        return randomProvider.nextCharacter(Locale.ENGLISH).toString();
    }
}
