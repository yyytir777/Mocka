package jodag.registry;

import jodag.exception.dataRegistry.NotFoundGeneratorException;
import jodag.generator.Generator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("DataRegistry 테스트 코드입니다.")
class DataRegistryTest {

    private final static String key = "name";
    private final static Path path = Path.of("src/test/resources/test.txt");

    @BeforeEach
    @DisplayName("테스트메서드 호출 시 dataRegistry의 registry초기화")
    void setUp() {
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.clearAll();
    }

    @Test
    @DisplayName("DataRegistry 인스턴스를 가져옵니다.")
    void get_dataRegistry() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();

        // when & then
        assertThat(dataRegistry).isNotNull();
    }

    @Test
    @DisplayName("DataRegistry는 싱글톤으로 관리됩니다. 여러 번 호출해도 같은 인스턴스를 반환합니다.")
    void dataRegistry_is_singleton() {
        // given
        DataRegistry dataRegistry1 = DataRegistry.getInstance();
        DataRegistry dataRegistry2 = DataRegistry.getInstance();

        // when & then
        assertThat(dataRegistry1).isSameAs(dataRegistry2);
    }

    @Test
    @DisplayName("DataRegistry에 Generator를 등록합니다.")
    void add_generator() {
        // given & when
        DataRegistry dataRegistry = DataRegistry.getInstance();

        // then
        assertDoesNotThrow(() -> dataRegistry.add(key, path, String.class));
    }

    @Test
    @DisplayName("DataRegistry에서 등록된 Generator를 가져옵니다.")
    void get_generator() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.add(key, path, String.class);

        // when
        Generator<String> generator = dataRegistry.get("name");

        // then
        assertThat(generator).isNotNull();
    }

    @Test
    @DisplayName("등록된 Generator를 삭제합니다.")
    void delete_generator() {
        // given & when
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.add(key, path, String.class);

        // then
        assertDoesNotThrow(() -> dataRegistry.delete(key));
        assertThrows(NotFoundGeneratorException.class, () -> dataRegistry.get(key));
    }
}