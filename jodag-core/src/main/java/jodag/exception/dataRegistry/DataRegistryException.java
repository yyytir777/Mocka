package jodag.exception.dataRegistry;

public class DataRegistryException extends RuntimeException {
    public DataRegistryException(String message) {
        super(message);
    }

    public DataRegistryException(String message, Throwable cause) {
        super(message, cause);
    }
}
