package entityinstantiator.generator.factory;

import entityinstantiator.generator.regex.RegexGenerator;

public interface RegexFactory {

    default RegexGenerator asRegex() {
        return RegexGenerator.getInstance();
    }
}
