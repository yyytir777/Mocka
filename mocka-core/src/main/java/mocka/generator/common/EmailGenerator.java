package mocka.generator.common;


import mocka.generator.AbstractGenerator;
import mocka.generator.regex.RegexGenerator;

import java.util.List;


public class EmailGenerator extends AbstractGenerator<String> {

    private static final EmailGenerator INSTANCE =  new EmailGenerator();
    private static final List<String> DOMAIN = List.of("com", "io", "net", "org");
    private final RegexGenerator regexGenerator = RegexGenerator.getInstance();

    private EmailGenerator() {
        super("email", String.class);
    }

    public static EmailGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        String localPart = regexGenerator.get("\\w{5,10}");
        String domainName = regexGenerator.get("\\w{3,7}");
        String topLevelDomain = DOMAIN.get(randomProvider.getNextIdx(DOMAIN.size()));
        return localPart + "@" + domainName + "." + topLevelDomain;
    }
}
