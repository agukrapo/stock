package ar.com.ak.util;

public class PersistanceException extends RuntimeException {

    private static final long serialVersionUID = -833176842184794814L;

    public PersistanceException() {
        super();
    }

    public PersistanceException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public PersistanceException(String arg0) {
        super(arg0);
    }

    public PersistanceException(Throwable arg0) {
        super(arg0);
    }

}
