package mocka.core.generator;

import mocka.core.annotation.FileSource;
import mocka.core.file.PathResourceLoader;
import mocka.core.parser.FileParser;
import mocka.core.parser.FileParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public interface Creator {

    <T> T handleValueSource(Field field);

    String handleRegexSource(Field field);

    <T> T initInstance(Class<T> clazz);

    default <T> T createFromFileSource(Class<T> clazz, FileSource fileSource) {
        String stringPath = fileSource.value();
        String extension = PathResourceLoader.getExtension(stringPath);
        FileParser parser = FileParserFactory.of(extension);

        try (InputStream inputStream = PathResourceLoader.getPath(stringPath)) {
            return parser.parse(inputStream, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file source: " + stringPath, e);
        }
    }
}
