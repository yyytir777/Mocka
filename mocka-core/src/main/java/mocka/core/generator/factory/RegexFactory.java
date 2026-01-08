package mocka.core.generator.factory;

import mocka.core.generator.regex.RegexGenerator;

public interface RegexFactory {

    default RegexGenerator asRegex() {
        return RegexGenerator.getInstance();
    }
}
