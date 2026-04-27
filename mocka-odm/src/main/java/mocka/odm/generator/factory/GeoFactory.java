package mocka.odm.generator.factory;

public interface GeoFactory {

    default GeoGenerator asGeo() {
        return GeoGenerator.getInstance();
    }

    default GeoJsonGenerator asGeoJson() {
        return GeoJsonGenerator.getInstance();
    }

    default BsonGenerator asBson() {
        return BsonGenerator.getInstance();
    }
}
