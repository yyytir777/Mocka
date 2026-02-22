package mocka.odm.generator.factory;

import mocka.odm.generator.BsonGenerator;
import mocka.odm.generator.geo.GeoJsonGenerator;
import mocka.odm.generator.geo.GeoGenerator;

public interface GeoFactory {

    default GeoGenerator asGeo() {
        return GeoGenerator.getInstance();
    }

    default GeoJsonGenerator asGeoJson() {
        return GeoJsonGenerator.getInstance();
    }

    default BsonGenerator asOdm() {
        return BsonGenerator.getInstance();
    }
}
