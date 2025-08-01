package jodag.exception;

import jodag.exception.generator.GeneratorException;

public class MissingRequiredAnnotationException extends GeneratorException {

    public MissingRequiredAnnotationException(String message) {
        super(message);
    }

    public MissingRequiredAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }
}
