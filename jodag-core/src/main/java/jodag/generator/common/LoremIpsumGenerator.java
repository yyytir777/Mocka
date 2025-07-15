package jodag.generator.common;

import jodag.generator.GenerateType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class LoremIpsumGenerator extends DefaultAbstractGenerator<String> {

    private static LoremIpsumGenerator INSTANCE;

    private final List<String> loremIpsum;

    public LoremIpsumGenerator(GenerateType generateType) throws IOException {
        super(generateType.name(), String.class);
        InputStream is = getClass().getClassLoader().getResourceAsStream("email.txt");
        if (is == null) {
            throw new FileNotFoundException("리소스를 찾을 수 없습니다: email.txt");
        }

        this.loremIpsum = new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.toList());
    }

    public static synchronized LoremIpsumGenerator getInstance(GenerateType generateType) {
        if (INSTANCE == null) {
            try {
                INSTANCE = new LoremIpsumGenerator(generateType);
            } catch (IOException e) {
                throw new RuntimeException("LoremIpsumGenerator 생성 실패", e);
            }
        }
        return INSTANCE;
    }

    @Override
    public String get() {
        return loremIpsum.get(randomProvider.nextInt(loremIpsum.size()));
    }
}
