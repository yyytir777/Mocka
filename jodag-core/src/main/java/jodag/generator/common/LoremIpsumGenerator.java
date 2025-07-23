package jodag.generator.common;


import jodag.generator.AbstractGenerator;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class LoremIpsumGenerator extends AbstractGenerator<String> {

    private static final LoremIpsumGenerator INSTANCE;

    static {
        try {
            INSTANCE = new LoremIpsumGenerator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final List<String> loremIpsum;

    private LoremIpsumGenerator() throws IOException {
        super("lorem ipsum", String.class);
        InputStream is = getClass().getClassLoader().getResourceAsStream("lorem_ipsum.txt");
        if (is == null) {
            throw new FileNotFoundException("리소스를 찾을 수 없습니다: lorem_ipsum.txt");
        }

        this.loremIpsum = new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.toList());
    }

    public static LoremIpsumGenerator getInstance() {
        return INSTANCE;
    }

    // 무작위 길이의 lorem ipsum 반환
    @Override
    public String get() {
        int length = (int) randomProvider.getGaussian(300, 20);
        return generate(length);
    }

    public String get(int length) {
        return generate(length);
    }

    public String get(int mean, int stdev) {
        int length = (int) randomProvider.getGaussian(mean, stdev);
        return generate(length);
    }

    private String generate(int length) {
        int idx = 0;
        StringBuilder sb = new StringBuilder(length);
        while(sb.length() < length) {
            sb.append(loremIpsum.get(idx++ % loremIpsum.size())).append(' ');
        }
        return sb.substring(0, length);
    }
}
