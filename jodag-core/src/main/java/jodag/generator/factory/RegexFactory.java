package jodag.generator.factory;

import jodag.generator.regex.RegexGenerator;

public interface RegexFactory {

    default RegexGenerator asRegex() {
        return RegexGenerator.getInstance();
    }
}
