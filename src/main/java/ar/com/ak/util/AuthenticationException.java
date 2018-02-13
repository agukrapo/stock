package ar.com.ak.util;

public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 2966568199523872059L;

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public AuthenticationException(String arg0) {
        super(arg0);
    }

    public AuthenticationException(Throwable arg0) {
        super(arg0);
    }
}
