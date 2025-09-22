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

    private final ORMResolver defaultResolver;
    private final BeanFactory beanFactory;
    private final Map<ORMType, ORMResolver> ORM_RESOLVER_MAP = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(ORMCreator.class);

    private static final String mybatisBeanName = "sqlSessionFactory";
    private static final String hibernateBeanName = "entityManagerFactory";

    private final ORMResolver ormResolver;

    public ORMCreator(BeanFactory beanFactory,
                      ORMProperties ormProperties,
                      HibernateCreator hibernateCreator,
                      MyBatisCreator myBatisCreator) {
        this.beanFactory = beanFactory;
        this.defaultResolver = hibernateCreator;
        this.ormResolver = setOrmResolver(ormProperties, hibernateCreator, myBatisCreator);
    }

    public List<ORMResolver> getResolver() {
        if (this.ormResolver == null) {
            return new ArrayList<>(ORM_RESOLVER_MAP.values());
        }
        return List.of(this.ormResolver);
    }

    public <T> T create(Class<T> clazz, GenerateType generateType) {
        if(ormResolver == null) {
            throw new GeneratorException("ORM resolver is null");
        }
        return ormResolver.create(clazz, generateType);
    }

    /**
     * 사용 ORM이 여러 개일 때, ORMType을 지정하여 instance 생성
     */
    public <T> T create(ORMType ormType, Class<T> clazz, GenerateType generateType) {
        return ORM_RESOLVER_MAP.get(ormType).create(clazz, generateType);
    }

    public <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        return ormResolver.create(clazz, caches, visited);
    }

    public <T> T create(ORMType ormType, Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        return  ORM_RESOLVER_MAP.get(ormType).create(clazz, caches, visited);
    }

    void initResolver(HibernateCreator hibernateCreator, MyBatisCreator myBatisCreator) {
        ORM_RESOLVER_MAP.put(ORMType.HIBERNATE, hibernateCreator);
        ORM_RESOLVER_MAP.put(ORMType.MYBATIS, myBatisCreator);

        hibernateCreator.load();
        myBatisCreator.load();
    }

    public ORMResolver setOrmResolver(ORMProperties ormProperties, HibernateCreator hibernateCreator, MyBatisCreator myBatisCreator) {
        boolean hasMyBatis = beanFactory.containsBean(mybatisBeanName);
        boolean hasHibernate = beanFactory.containsBean(hibernateBeanName);

        // 어떠한 의존성도 없을 때
        if (!hasMyBatis && !hasHibernate) {
            return null;
        }

        // 변수 설정 시
        ORMType ormType = ormProperties.getOrmType();
        if(ormType != null) {
            return switch (ormType) {
                case MYBATIS -> myBatisCreator;
                case HIBERNATE -> hibernateCreator;
            };
        }

        // 변수 설정 X
        if(hasMyBatis && hasHibernate) {
            initResolver(hibernateCreator, myBatisCreator);
            return this.defaultResolver;
        } else {
            ORMResolver resolver =  hasMyBatis ?  myBatisCreator : hibernateCreator;
            resolver.load();
            log.info("ormResolver is initialized {}", resolver);
            return resolver;
        }
    }
}
