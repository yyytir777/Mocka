package jodag.exception;

import jodag.exception.generator.GeneratorException;

public class ValueSourceException extends GeneratorException {

    public ValueSourceException(String message) {
        super(message);
    }

    public ValueSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
