package jodag.generator.common;


import jodag.generator.AbstractGenerator;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class LoremIpsumGenerator extends AbstractGenerator<String> {

    private static final LoremIpsumGenerator INSTANCE;

    private final List<String> loremIpsum;

    static {
        try {
            INSTANCE = new LoremIpsumGenerator();
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Lorem Ipsum Generator 초기화 실패" + e);
        }
    }


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

    public static LoremIpsumGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        return loremIpsum.get(randomProvider.getInt(loremIpsum.size()));
    }
}
