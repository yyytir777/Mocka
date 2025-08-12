package jodag.generator.common;

import jodag.generator.AbstractGenerator;
import jodag.generator.regex.RegexParser;

import java.util.regex.Pattern;

public class RegexGenerator extends AbstractGenerator<String> {

    private static final RegexGenerator INSTANCE = new RegexGenerator();
    private final RegexParser regexParser;

    private RegexGenerator() {
        super("regex", String.class);
        this.regexParser = RegexParser.getInstance();
    }

    public static RegexGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        return "regex";
    }

    public String get(Pattern pattern) {
        return regexParser.get(pattern);
    }
}
