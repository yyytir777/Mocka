package mocka.core.file;

import mocka.core.exception.GeneratorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PathResourceLoader Tes")
public class PathResourceLoaderTest {

    @TempDir
    Path absolutePath;

    @Test
    @DisplayName("returns inputStream when file exists (absolute path)")
    void returns_inputStream_when_file_exists() throws IOException {
        Path file = absolutePath.resolve("absolute.txt");
        Files.writeString(file, "hello world!");

        InputStream inputStream = PathResourceLoader.getPath(file.toAbsolutePath().toString());
        assertThat(inputStream).isNotNull();
        assertThat(new String(inputStream.readAllBytes())).isEqualTo("hello world!");
    }

    @Test
    @DisplayName("returns inputStream when file exists (relative path)")
    void returns_inputStream_when_file_exists_relative() {
        InputStream inputStream = PathResourceLoader.getPath("test.txt");
        assertThat(inputStream).isNotNull();
    }

    @Test
    @DisplayName("throws GeneratorException when file does not exists")
    void throws_GeneratorException_when_file_does_not_exists() {
        assertThatThrownBy(() -> PathResourceLoader.getPath("no_exists.txt"))
                .isInstanceOf(GeneratorException.class);
    }

    @Test
    @DisplayName("throws IllegalArgumentException when file is null or empty ptah")
    void throws_IllegalArgumentException_when_file_is_null_or_empty_ptah() {
        assertThatThrownBy(() -> PathResourceLoader.getPath(null))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> PathResourceLoader.getPath(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("returns extensions of files")
    void getExtension_shouldReturnFileExtension() {
        assertThat(PathResourceLoader.getExtension("example.txt")).isEqualTo("txt");
        assertThat(PathResourceLoader.getExtension("/path/to/file.json")).isEqualTo("json");
        assertThat(PathResourceLoader.getExtension("no_extension")).isEmpty();
    }
}
