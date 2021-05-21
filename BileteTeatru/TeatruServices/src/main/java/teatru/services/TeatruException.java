package teatru.services;

public class TeatruException extends Exception{
    public TeatruException() {
    }

    public TeatruException(String message) {
        super(message);
    }

    public TeatruException(String message, Throwable cause) {
        super(message, cause);
    }
}
