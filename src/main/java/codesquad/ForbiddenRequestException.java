package codesquad;

public class ForbiddenRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ForbiddenRequestException() {
        super();
    }

    public ForbiddenRequestException(String message) {
        super(message);
    }
}
