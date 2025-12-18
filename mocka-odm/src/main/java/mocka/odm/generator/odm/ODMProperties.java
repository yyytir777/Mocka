package mocka.odm.generator.odm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "mocka.odm")
public class ODMProperties {

    private List<ODMType> odmType = null;
    private Integer associationSize = 5;

    public void setOdmType(List<ODMType> odmType) {
        this.odmType = odmType;
    }

    public List<ODMType> getOdmType() {
        return odmType;
    }

    public void setAssociationSize(Integer associationSize) {
        this.associationSize = associationSize;
    }

    public Integer getAssociationSize() {
        return associationSize;
    }
}
