package mocka.odm.generator.factory;

import mocka.core.generator.AbstractGenerator;
import mocka.core.generator.primitive.DoubleGenerator;
import mocka.core.generator.primitive.IntegerGenerator;
import org.springframework.data.mongodb.core.geo.*;

import java.util.List;
import java.util.stream.IntStream;

public class GeoJsonGenerator extends AbstractGenerator<String> {

    private static final GeoJsonGenerator INSTANCE = new GeoJsonGenerator();
    private final DoubleGenerator doubleGenerator = DoubleGenerator.getInstance();
    private final GeoGenerator geoGenerator = GeoGenerator.getInstance();
    private final IntegerGenerator intGenerator = IntegerGenerator.getInstance();

    public static GeoJsonGenerator getInstance() {
        return INSTANCE;
    }

    private GeoJsonGenerator() {
        super("geoGson", String.class);
    }

    @Override
    public String get() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    public GeoJsonPoint getGeoJsonPoint() {
        double latitude = doubleGenerator.getDouble(-90.0, 90.0);
        double longitude = doubleGenerator.getDouble(-180.0, 180.0);
        return new GeoJsonPoint(latitude, longitude);
    }

    public List<GeoJsonPoint> getGeoJsonPoints() {
        return getGeoJsonPoints(intGenerator.getInteger(3, 10));
    }

    public List<GeoJsonPoint> getGeoJsonPoints(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> getGeoJsonPoint())
                .toList();
    }

    public GeoJsonMultiPoint getGeoJsonMultiPoint() {
        return getGeoJsonMultiPoint(intGenerator.getInteger(3, 10));
    }

    public GeoJsonMultiPoint getGeoJsonMultiPoint(int cnt) {
        return new GeoJsonMultiPoint(geoGenerator.getPoints(cnt));
    }

    public GeoJsonLineString getGeoJsonLineString() {
        return getGeoJsonLineString(intGenerator.getInteger(3, 10));
    }

    public GeoJsonLineString getGeoJsonLineString(int size) {
        return new GeoJsonLineString(geoGenerator.getPoints(size));
    }

    public GeoJsonPolygon getGeoJsonPolygon() {
        return getGeoJsonPolygon(intGenerator.getInteger(3, 10));
    }

    public GeoJsonPolygon getGeoJsonPolygon(int size) {
        return new GeoJsonPolygon(geoGenerator.getPoints(size));
    }

    public GeoJsonMultiPolygon getGeoJsonMultiPolygon() {
        return getGeoJsonMultiPolygon(intGenerator.getInteger(3, 10));
    }

    public GeoJsonMultiPolygon getGeoJsonMultiPolygon(int size) {
        List<GeoJsonPolygon> geoJsonPolygons = IntStream.range(0, size)
                .mapToObj(i -> {
                    int angle = intGenerator.getInteger(3, 10);
                    return getGeoJsonPolygon(angle);
                }).toList();
        return new GeoJsonMultiPolygon(geoJsonPolygons);
    }

    public GeoJsonMultiLineString getGeoJsonMultiLineString() {
        return getGeoJsonMultiLineString(intGenerator.getInteger(3, 10));
    }

    public GeoJsonMultiLineString getGeoJsonMultiLineString(int size) {
        List<GeoJsonLineString> geoJsonLineStrings = IntStream.range(0, size)
                .mapToObj(i -> getGeoJsonLineString(intGenerator.getInteger(3, 10)))
                .toList();
        return new GeoJsonMultiLineString(geoJsonLineStrings);
    }

    public Sphere getSphere() {
        Double radius = doubleGenerator.getPositiveDouble();
        return new Sphere(geoGenerator.getPoint(), radius);
    }
}
