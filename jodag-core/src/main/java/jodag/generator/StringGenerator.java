package jodag.generator;

public interface StringGenerator extends Generator<String> {

    String get(int length);
}
