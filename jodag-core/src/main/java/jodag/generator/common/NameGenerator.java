package jodag.generator.common;


import jodag.generator.AbstractGenerator;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class NameGenerator extends AbstractGenerator<String> {

    private static final NameGenerator INSTANCE;

    private final List<String> names;

    static {
        try {
            INSTANCE = new NameGenerator();
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Name Generator 초기화 실패" + e);
        }
    }

    private NameGenerator() throws IOException {
        super("name", String.class);
        InputStream is = getClass().getClassLoader().getResourceAsStream("name.txt");
        if (is == null) {
            throw new FileNotFoundException("리소스를 찾을 수 없습니다: name.txt");
        }

        this.names = new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.toList());
    }

    public static NameGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        return names.get(randomProvider.getInt(names.size()));
    }
}
