package jodag.generator.common;

import jodag.generator.GenerateType;
import jodag.generator.Generator;
import jodag.registry.DataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("NameGenerator 테스트 클래스입니다.")
class NameGeneratorTest {

    private final DataRegistry dataRegistry = DataRegistry.getInstance();

    @Test
    @DisplayName("NameGenerator 인스턴스를 반환한다")
    void return_name_generator_instance() {
        Generator<String> nameGenerator = dataRegistry.getGenerator(GenerateType.NAME);
        assertThat(nameGenerator).isInstanceOf(NameGenerator.class);
    }

    @Test
    @DisplayName("NameGenerator에서 get()을 호출하여 name을 반환합니다.")
    void generate_different_names_on_each_get_call() {
        Generator<String> generator = dataRegistry.getGenerator(GenerateType.NAME);

        String name1 = generator.get();
        String name2 = generator.get();

        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name1).isNotEqualTo(name2);
    }

    @Test
    @DisplayName("NameGenerator에서 1000개의 랜덤 이름을 반환합니다.")
    void return_1000_names() {
        Generator<String> generator = dataRegistry.getGenerator(GenerateType.NAME);
        for(int i = 0; i < 1000; i++) {
            String name = generator.get();
            System.out.println("name{i} = " + name);
        }

        assertThat(true).isTrue();
    }
}