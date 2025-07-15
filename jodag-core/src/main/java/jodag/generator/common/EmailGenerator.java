package jodag.generator.common;

import jodag.generator.GenerateType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class EmailGenerator extends DefaultAbstractGenerator<String> {

    private static EmailGenerator INSTANCE;

    private final List<String> emails;

    public EmailGenerator(GenerateType generateType) throws IOException {
        super(generateType.name(), String.class);
        InputStream is = getClass().getClassLoader().getResourceAsStream("email.txt");
        if (is == null) {
            throw new FileNotFoundException("리소스를 찾을 수 없습니다: email.txt");
        }

        this.emails = new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.toList());
    }

    public static synchronized EmailGenerator getInstance(GenerateType generateType) {
        if (INSTANCE == null) {
            try {
                INSTANCE = new EmailGenerator(generateType);
            } catch (IOException e) {
                throw new RuntimeException("EmailGenerator 생성 실패", e);
            }
        }
        return INSTANCE;
    }

    @Override
    public String get() {
        return emails.get(randomProvider.nextInt(emails.size()));
    }
}
