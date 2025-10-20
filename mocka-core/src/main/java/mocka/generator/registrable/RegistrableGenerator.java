package mocka.generator.registrable;

import mocka.file.PathResourceLoader;
import mocka.generator.AbstractGenerator;

import java.io.*;
import java.util.List;

/**
 * A Generator that is registered in the DataRegistry.
 * Each instance holds a file path and provides random access
 * to the file’s data through the get method, returning values from it.
 */
public class RegistrableGenerator<T> extends AbstractGenerator<T> {

    private final List<T> data;
    private final String path;

    @SuppressWarnings("unchecked")
    public RegistrableGenerator(String key, String resourcePath, Class<T> type) {
        super(key, type);
        this.path = resourcePath;
        InputStream is = PathResourceLoader.getPath(resourcePath);
        this.data = (List<T>) new BufferedReader(new InputStreamReader(is))
                .lines().toList();
    }

    @Override
    public T get() {
        return data.get(randomProvider.getInt(data.size()));
    }

    public String getPath() {
        return path;
    }
}
