package jodag.generator.common;


import jodag.generator.AbstractGenerator;

import java.util.concurrent.ThreadLocalRandom;


public class EmailGenerator extends AbstractGenerator<String> {

    private static EmailGenerator INSTANCE;

    private EmailGenerator() {
        super("email", String.class);
    }

    public static synchronized EmailGenerator getInstance() {
        if (INSTANCE == null) INSTANCE = new EmailGenerator();
        return INSTANCE;
    }

    @Override
    public String get() {
        String username = randomStringWithNum(7, 10);
        String domain = randomString(3, 7);
        String tld = randomString(2, 3);
        return username + "@" + domain + "." + tld;
    }

    private String randomStringWithNum(int min, int max) {
        int length = min + ThreadLocalRandom.current().nextInt(max - min + 1);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            sb.append(randomProvider.nextAlphanum());
        }
        return sb.toString();
    }

    private String randomString(int min, int max) {
        int length = min + ThreadLocalRandom.current().nextInt(max - min + 1);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            sb.append(randomProvider.nextAlphabet());
        }
        return sb.toString();
    }
}
