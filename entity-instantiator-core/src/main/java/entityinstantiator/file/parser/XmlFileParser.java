package entityinstantiator.file.parser;

import java.io.InputStream;

public class XmlFileParser implements FileParser {

    private static final XmlFileParser INSTANCE = new XmlFileParser();

    private XmlFileParser() {}

    public static XmlFileParser getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T parse(InputStream inputStream, Class<T> clazz) {
        return null;
    }
}
