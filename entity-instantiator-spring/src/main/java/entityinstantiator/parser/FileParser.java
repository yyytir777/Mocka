package entityinstantiator.parser;


import java.io.InputStream;

public interface FileParser {
    <T> T parse(InputStream inputStream, Class<T> clazz);
}
