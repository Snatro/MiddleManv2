package exceptions;

public class LoginPasswordException extends Exception{

    public LoginPasswordException() {
        super("Password was incorrect!");
    }

    public LoginPasswordException(String message) {
        super(message);
    }

    public LoginPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginPasswordException(Throwable cause) {
        super(cause);
    }

    protected LoginPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
