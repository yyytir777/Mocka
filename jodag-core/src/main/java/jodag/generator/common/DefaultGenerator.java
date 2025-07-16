package jodag.generator.common;


import jodag.generator.AbstractGenerator;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultGenerator extends AbstractGenerator<String> {

    private static DefaultGenerator INSTANCE;

    private final List<String> defaults;

    private DefaultGenerator() throws IOException {
        super("defaults", String.class);

        InputStream is = getClass().getClassLoader().getResourceAsStream("default.txt");
        if (is == null) {
            throw new FileNotFoundException("리소스를 찾을 수 없습니다: email.txt");
        }

        this.defaults = new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.toList());
    }

    public static synchronized DefaultGenerator getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new DefaultGenerator();
            } catch (IOException e) {
                throw new RuntimeException("DefaultGenerator 생성 실패", e);
            }
        }
        return INSTANCE;
    }

    @Override
    public String get() {
        return defaults.get(randomProvider.nextInt(defaults.size()));
    }
}
