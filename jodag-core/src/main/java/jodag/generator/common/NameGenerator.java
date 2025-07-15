package jodag.generator.common;

import jodag.generator.GenerateType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class NameGenerator extends DefaultAbstractGenerator<String> {

    private static NameGenerator INSTANCE;

    private final List<String> names;

    private NameGenerator(GenerateType generateType) throws IOException {
        super(generateType.name(), String.class);
        InputStream is = getClass().getClassLoader().getResourceAsStream("email.txt");
        if (is == null) {
            throw new FileNotFoundException("리소스를 찾을 수 없습니다: email.txt");
        }

        this.names = new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.toList());
    }

    public static synchronized NameGenerator getInstance(GenerateType generateType) {
        if(INSTANCE == null) {
            try {
                INSTANCE = new NameGenerator(generateType);
            } catch (IOException e) {
                throw new RuntimeException("NameGenerator 생성 실패", e);
            }
        }
        return INSTANCE;
    }

    @Override
    public String get() {
        return names.get(randomProvider.nextInt(names.size()));
    }
}
