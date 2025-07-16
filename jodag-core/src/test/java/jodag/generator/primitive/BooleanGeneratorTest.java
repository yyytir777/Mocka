package jodag.generator.primitive;

import jodag.registry.DataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BooleanGenerator 관련 테스트입니다.")
class BooleanGeneratorTest {

    @Test
    @DisplayName("BooleanGenerator 인스턴스를 가져옵니다.(dataRegistry에 기본으로 생성되어 있습니다.)")
    void getInstance() {
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.getGenerator();
    }

    @Test
    void get() {

    }
}