package jodag.exception.dataRegistry;

public class NotFoundGeneratorException extends DataRegistryException {
    public NotFoundGeneratorException(String message) {
        super(message);
    }

    public NotFoundGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
