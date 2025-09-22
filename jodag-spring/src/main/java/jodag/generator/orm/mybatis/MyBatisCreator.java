package jodag.generator.orm.mybatis;

import jodag.generator.GenerateType;
import jodag.generator.VisitedPath;
import jodag.generator.orm.ORMResolver;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class MyBatisCreator implements ORMResolver {

    private final MyBatisLoader myBatisLoader;

    public MyBatisCreator(MyBatisLoader myBatisLoader) {
        this.myBatisLoader = myBatisLoader;
    }

    @Override
    public <T> T create(Class<T> clazz, GenerateType generateType) {
        // TODO MyBatis 인스턴스 생성 로직 구현해야함
        return null;
    }

    @Override
    public <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        // TODO MyBatis 인스턴스 생성 로직 구현해야함
        return null;
    }

    @Override
    public void load() {
        myBatisLoader.load();
    }
}
