package entityinstantiator.file;


import entityinstantiator.exception.GeneratorException;
import org.apache.commons.io.FilenameUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathResourceLoader {

    /**
     * Reads a file based on the given string path and returns it as an {@link InputStream}.
     * <p>
     * This method supports two types of paths:
     * <ul>
     *   <li><b>Absolute Path</b>: Reads the file from the local file system using an absolute path.</li>
     *   <li><b>Classpath Path</b>: Reads a resource from within the classpath.
     *       If the path starts with '/', it will be removed before loading the resource via the class loader.</li>
     * </ul>
     * <p>
     * If the file does not exist or cannot be read, a {@link FileNotFoundException} or {@link IOException} will be thrown.
     *
     * @param path the absolute path or the resource path within the classpath
     * @return an {@link InputStream} to read the file from the given path
     * @throws IllegalArgumentException if the given path is {@code null} or empty
     * @throws GeneratorException if the file does not exist at the given path
     * @throws GeneratorException if an error occurs while reading the file
     */
    public static InputStream getPath(String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path is blank");

        try {
            Path filePath = Paths.get(path);
            if (filePath.isAbsolute()) {
                return Files.newInputStream(filePath);
            }
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path.startsWith("/") ? path.substring(1) : path);

            if(inputStream == null){
                throw new FileNotFoundException("Resource not found : " + path);
            }
            return inputStream;
        } catch (FileNotFoundException e) {
            throw new GeneratorException(e.getMessage());
        } catch (IOException ioe) {
            throw new GeneratorException("Failed to load resource " + path, ioe);
        }
    }

    public static String getExtension(String path) {
        return FilenameUtils.getExtension(path);
    }
}
