package jodag.generator.common;


import jodag.generator.AbstractGenerator;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class EmailGenerator extends AbstractGenerator<String> {

    private static EmailGenerator INSTANCE;

    private final List<String> emails;

    private EmailGenerator() throws IOException {
        super("email", String.class);
        InputStream is = getClass().getClassLoader().getResourceAsStream("email.txt");
        if (is == null) {
            throw new FileNotFoundException("리소스를 찾을 수 없습니다: email.txt");
        }

        this.emails = new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.toList());
    }

    public static synchronized EmailGenerator getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new EmailGenerator();
            } catch (IOException e) {
                throw new RuntimeException("EmailGenerator 생성 실패", e);
            }
        }
        return INSTANCE;
    }

    @Override
    public String get() {
        String username = randomStringWithNum(5, 10);
        String domain = randomString(3, 7);
        String tld = randomString(2, 3);
        return username + "@" + domain + "." + tld;
    }

    private String randomStringWithNum(int min, int max) {
        int length = randomProvider.nextInt(max - min + 1) + min;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            sb.append(randomProvider.nextAlphanum());
        }
        return sb.toString();
    }

    private String randomString(int min, int max) {
        int length = randomProvider.nextInt(max - min + 1) + min;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            sb.append(randomProvider.nextAlphabet());
        }
        return sb.toString();
    }
}
