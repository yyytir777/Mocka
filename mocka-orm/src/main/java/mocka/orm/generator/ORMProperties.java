package mocka.orm.generator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration properties for ORM-related settings.
 *
 * <p>
 * These properties are bound from the application configuration file with the prefix {@code mocka.orm}.
 * </p>
 *
 * <p><b>Configuration Example (application.yaml):</b></p>
 * <pre>
 * mocka:
 *   orm:
 *     orm-type: [HIBERNATE, MYBATIS]  # List of ORM types to use
 *     association-size: 10             # Size of collection instances to generate
 * </pre>
 */
@Configuration
@ConfigurationProperties(prefix = "mocka.orm")
public class ORMProperties {

    private List<ORMType> ormType = null;
    private Integer associationSize = 5;
    private final MyBatis myBatis = new MyBatis();

    public void setOrmType(List<ORMType> ormType) {
        this.ormType = ormType;
    }

    public List<ORMType> getOrmType() {
        return ormType;
    }

    public Integer getAssociationSize() {
        return associationSize;
    }

    public void setAssociationSize(Integer associationSize) {
        this.associationSize = associationSize;
    }

    public String getLocation() {
        return myBatis.getLocation();
    }

    public void setLocation(String location) {
        myBatis.setLocation(location);
    }

    private static class MyBatis {
        private String location;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
