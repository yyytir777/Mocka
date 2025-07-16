package jodag.registry;

import jodag.exception.dataRegistry.DuplicateGeneratorException;
import jodag.exception.dataRegistry.NotFoundGeneratorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("DataRegistry에서 발생하는 예외 테스트")
public class DataRegistryExceptionTest {

    private final static String key = "name";
    private final static Path path = Path.of("src/main/resources/test.txt");

    @BeforeEach
    @DisplayName("테스트메서드 호출 시 dataRegistry의 registry초기화")
    void setUp() {
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.clearAll();
    }

    @Test
    @DisplayName("generator등록 시, 같은 key가 이미 등록되어있다면 예외를 던집니다.")
    void throw_dataRegistryException_if_there_is_same_key() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.add(key, path, String.class);

        // when & then
        assertThrows(DuplicateGeneratorException.class,
                () -> dataRegistry.add(key, path, String.class));
    }

    @Test
    @DisplayName("generator 조회 시, key값에 대해 존재하는 generator가 없다면 예외를 던집니다.")
    void throw_dataRegistryException_if_there_is_duplicate_key() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.add(key, path, String.class);

        // when & then
        String anotherKey = "name1";
        assertThrows(NotFoundGeneratorException.class,
                () -> dataRegistry.get(anotherKey));
    }

    @Test
    @DisplayName("generator 삭제 시, 삭제하려는 generator의 key값이 없다면 예외를 던접니다.")
    void throw_dataRegistryException_if_there_is_no_key() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.add(key, path, String.class);

        // when & then
        String anotherKey = "name1";
        assertThrows(NotFoundGeneratorException.class,
                () -> dataRegistry.delete(anotherKey));
    }
}
