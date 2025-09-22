package jodag.generator.orm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jodag.orm")
public class ORMProperties {

    private ORMType ormType = null;
    private Integer AssociationSize = 5;

    public void setOrmType(ORMType ormType) {
        this.ormType = ormType;
    }

    public ORMType getOrmType() {
        return ormType;
    }

    public Integer getAssociationSize() {
        return AssociationSize;
    }

    public void setAssociationSize(Integer associationSize) {
        AssociationSize = associationSize;
    }
}
