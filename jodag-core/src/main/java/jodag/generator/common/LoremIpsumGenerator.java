package jodag.generator.common;


import jodag.generator.AbstractGenerator;

import java.io.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class LoremIpsumGenerator extends AbstractGenerator<String> {

    private static LoremIpsumGenerator INSTANCE;

    private final List<String> loremIpsum;

    private LoremIpsumGenerator() throws IOException {
        super("lorem ipsum", String.class);
        InputStream is = getClass().getClassLoader().getResourceAsStream("name.txt");
        if (is == null) {
            throw new FileNotFoundException("리소스를 찾을 수 없습니다: email.txt");
        }

        this.loremIpsum = new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.toList());
    }

    public static synchronized LoremIpsumGenerator getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new LoremIpsumGenerator();
            } catch (IOException e) {
                throw new RuntimeException("LoremIpsumGenerator 생성 실패", e);
            }
        }
        return INSTANCE;
    }

    @Override
    public String get() {
        return loremIpsum.get(ThreadLocalRandom.current().nextInt(loremIpsum.size()));
    }
}
