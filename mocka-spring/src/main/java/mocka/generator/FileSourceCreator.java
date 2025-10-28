package mocka.generator;

import mocka.file.PathResourceLoader;
import mocka.annotation.FileSource;
import mocka.parser.FileParser;
import mocka.parser.FileParserFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 *
 */
@Component
public class FileSourceCreator {

    /**
     * Creates an instance of the given class by analyzing the {@link FileSource} annotation.
     * <p>
     * This method reads the data file specified in the {@link FileSource} annotation
     * and maps its contents to a new instance of the specified entity class.
     * It is primarily used to generate entities pre-populated with data
     * from an external file source.
     * </p>
     *
     * @param clazz the class type of the entity to create
     * @param fileSource the {@link FileSource} annotation containing file path and metadata
     * @param <T> the type of entity to return
     * @return a new instance of {@code clazz} populated with data from the file
     */
    public <T> T createFromFileSource(Class<T> clazz, FileSource fileSource) {

        String stringPath = fileSource.value();
        InputStream inputStream = PathResourceLoader.getPath(stringPath);
        String extension = PathResourceLoader.getExtension(stringPath);

        FileParser parser = FileParserFactory.of(extension);
        return parser.parse(inputStream, clazz);
    }
}
