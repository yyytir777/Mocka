package mocka.generator.factory;

import mocka.generator.regex.RegexGenerator;

public interface RegexFactory {

    default RegexGenerator asRegex() {
        return RegexGenerator.getInstance();
    }
}
