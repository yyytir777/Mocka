package jodag.generator.regex;

import java.util.regex.Pattern;

public class RegexParser {

    private static final RegexParser INSTANCE = new RegexParser();

    public static RegexParser getInstance() {
        return INSTANCE;
    }

    public String get(Pattern pattern) {
        return generate(pattern);
    }

    // TODO Regex Pattern Generate Core Logic
    private String generate(Pattern pattern) {

        return "asdfasdfasf";
    }
}
