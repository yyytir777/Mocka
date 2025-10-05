package entityinstantiator.file.parser;

public final class FileParserFactory {

    private FileParserFactory() {}

    public static FileParser of(String extension) {
        return switch (extension) {
            case "json" -> JsonFileParser.getInstance();
            case "csv" -> CsvFileParser.getInstance();
            case "yaml", "yml" -> YamlFileParser.getInstance();
            case "xml" -> XmlFileParser.getInstance();
            default -> null;
        };
    }
}
