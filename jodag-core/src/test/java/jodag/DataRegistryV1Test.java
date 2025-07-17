package jodag;

import jodag.generator.GeneratorInfo;
import jodag.registry.DataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DataRegistry_테스트")
class DataRegistryV1Test {

    @Test
    @DisplayName("DataRegistry에서_특정_Generator의_정보를_조회합니다.")
    public void getGeneratorMetadata() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();

        // when
        String key = "NAME";
        GeneratorInfo<?> info = dataRegistry.getGeneratorInfo(key);

        // then
        assertThat(info).isNotNull();
    }

    @Test
    @DisplayName("DataRegistry에서_모든_Generator_정보를_조회합니다.")
    public void getAllGenerators() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();

        // when
        List<GeneratorInfo<?>> metadata = dataRegistry.getAllGeneratorInfo();

        // then
        for (GeneratorInfo<?> m : metadata) {
            System.out.println("m = " + m.toString());
            assertThat(m).isNotNull();
        }
    }
}