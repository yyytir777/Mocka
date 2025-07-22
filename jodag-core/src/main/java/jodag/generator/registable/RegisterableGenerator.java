package jodag.generator.registable;

import jodag.exception.generator.GeneratorException;
import jodag.generator.AbstractGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * DataRegistry에 등록해서 사용하는 Generator,
 * 각 인스턴스틑 파일 경로를 가지며, get메서드를 통해 파일의 데이터에 랜덤 접근하여 값을 return함
 * @param <T>
 */
public class RegisterableGenerator<T> extends AbstractGenerator<T> {

    private final List<T> data;

    @SuppressWarnings("unchecked")
    public RegisterableGenerator(String key, String resourcePath, Class<T> type) {
        super(key, type);
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);

        if(is == null) {
            throw new GeneratorException("Resource not found: " + resourcePath);
        }

        this.data = (List<T>) new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.toList());
    }

    @Override
    public T get() {
        return data.get(randomProvider.getInt(data.size()));
    }
}
