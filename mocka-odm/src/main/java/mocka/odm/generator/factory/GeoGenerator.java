package mocka.odm.generator.factory;

import mocka.core.generator.AbstractGenerator;
import mocka.core.generator.primitive.DoubleGenerator;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;

import java.util.List;
import java.util.stream.IntStream;

public class GeoGenerator extends AbstractGenerator<String> {

    private static final GeoGenerator INSTANCE = new GeoGenerator();
    private final DoubleGenerator doubleGenerator = DoubleGenerator.getInstance();

    public static GeoGenerator getInstance() {
        return INSTANCE;
    }

    private GeoGenerator() {
        super("geo", String.class);
    }

    @Override
    public String get() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    public Point getPoint() {
        double x = doubleGenerator.getDouble(-100.0, 100.0);
        double y = doubleGenerator.getDouble(-100.0, 100.0);
        return new Point(x, y);
    }

    public List<Point> getPoints(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> getPoint())
                .toList();
    }

    public Box getBox() {
        Double upperX = doubleGenerator.getDouble(-100.0, 100.0);
        Double upperY = doubleGenerator.getDouble(-100.0, 100.0);

        Double lowerX = doubleGenerator.getDouble(-100.0, upperX);
        Double lowerY = doubleGenerator.getDouble(-90.0, upperY);
        return new Box(new Point(lowerX, lowerY), new Point(upperX, upperY));
    }

    /**
     * 실제 위치 값 기반 Box
     */
    public Box getGeoBox() {
        Double upperX = doubleGenerator.getDouble(-180.0, 100.0);
        Double upperY = doubleGenerator.getDouble(-90.0, 90.0);

        Double lowerX = doubleGenerator.getDouble(-180.0, upperX);
        Double lowerY = doubleGenerator.getDouble(-90.0, upperY);
        return new Box(new Point(lowerX, lowerY), new Point(upperX, upperY));
    }

    public Circle getCircle() {
        Point center = getPoint();
        double radius = doubleGenerator.getDouble(0.1, 50.0);
        return new Circle(center, radius);
    }

    public Circle getCircle(Point p) {
        return new Circle(p, doubleGenerator.getPositiveDouble());
    }

    public Circle getCircle(double radius) {
        if(radius < 0) throw new IllegalArgumentException("radius must be positive");
        return new Circle(getPoint(), radius);
    }

    public Polygon getPolygon() {
        return getPolygon(randomProvider.getInt(3, 10));
    }

    public Polygon getPolygon(int pointCount) {
        List<Point> points = IntStream.range(0, pointCount)
                .mapToObj(i -> getPoint())
                .toList();
        return new Polygon(points);
    }
}
