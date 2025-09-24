package jodag.generator.orm;

import jodag.exception.GeneratorException;
import jodag.generator.GenerateType;
import jodag.generator.VisitedPath;
import jodag.generator.orm.hibernate.HibernateCreator;
import jodag.generator.orm.mybatis.MyBatisCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Select the ORM implementation (Hibernate or MyBatis) by analyzing the spring bean at runtime. <br>
 * if `SqlSessionFactory` spring bean exists, MyBatis is assumed. <br>
 * if `EntityManagerFactory` spring bean exists, Hibernate is assumed. <br>
 */
@Component
public class ORMCreator {

    private final BeanFactory beanFactory;
    private Map<ORMType, ORMResolver> ormResolver = new HashMap<>();

    private static final String mybatisBeanName = "sqlSessionFactory";
    private static final String hibernateBeanName = "entityManagerFactory";
    private final Set<Class<?>> SCANNED_CLASSES = new HashSet<>();

    public ORMCreator(BeanFactory beanFactory,
                      ORMProperties ormProperties,
                      HibernateCreator hibernateCreator,
                      MyBatisCreator myBatisCreator) {
        this.beanFactory = beanFactory;
        this.ormResolver = resolver(ormProperties, hibernateCreator, myBatisCreator);
    }

    public List<ORMResolver> getResolver() {
        if (this.ormResolver.isEmpty()) {
            throw new GeneratorException("ORM resolver is null");
        }
        return new ArrayList<>(ormResolver.values());
    }

    public <T> T create(Class<T> clazz, GenerateType generateType) {
        if(ormResolver.isEmpty()) {
            throw new GeneratorException("ORM resolver is null");
        } else if(ormResolver.size() > 1) {
            throw new GeneratorException("More than one ORM resolver found");
        }
        return ormResolver.values().iterator().next().create(clazz, generateType);
    }

    public <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        if(ormResolver.isEmpty()) {
            throw new GeneratorException("ORM resolver is null");
        } else if(ormResolver.size() > 1) {
            throw new GeneratorException("More than one ORM resolver found");
        }
        return ormResolver.values().iterator().next().create(clazz, caches, visited);
    }

    /**
     * 사용 ORM이 여러 개일 때, ORMType을 지정하여 instance 생성
     */
    public <T> T create(ORMType ormType, Class<T> clazz, GenerateType generateType) {
        return ormResolver.get(ormType).create(clazz, generateType);
    }

    public <T> T create(ORMType ormType, Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        return  ormResolver.get(ormType).create(clazz, caches, visited);
    }

    public Map<ORMType, ORMResolver> resolver(ORMProperties ormProperties, HibernateCreator hibernateCreator, MyBatisCreator myBatisCreator) {
        boolean hasMyBatis = beanFactory.containsBean(mybatisBeanName);
        boolean hasHibernate = beanFactory.containsBean(hibernateBeanName);

        // 어떠한 의존성도 없을 때
        if (!hasMyBatis && !hasHibernate) {
            throw new GeneratorException("ORM isn't exists");
        }

        // 변수 설정 시
        ORMType ormType = ormProperties.getOrmType();
        if(ormType != null) {
            return switch (ormType) {
                case MYBATIS -> Map.of(ORMType.MYBATIS, myBatisCreator);
                case HIBERNATE -> Map.of(ORMType.HIBERNATE, hibernateCreator);
            };
        }

        // 변수 설정 X
        if(hasMyBatis && hasHibernate) {
            return Map.of(ORMType.MYBATIS, myBatisCreator, ORMType.HIBERNATE, hibernateCreator);
        } else {
            return hasMyBatis ?  Map.of(ORMType.MYBATIS, myBatisCreator) : Map.of(ORMType.HIBERNATE, hibernateCreator);
        }
    }
    public Set<Class<?>> getClasses() {
        return SCANNED_CLASSES;
    }
}
