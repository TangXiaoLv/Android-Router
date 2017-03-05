package com.tangxiaolv.router.exceptions;

public class ValueParseException extends Exception {

    public ValueParseException() {
        super();
    }

    public ValueParseException(String message) {
        super(message);
    }

    public ValueParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueParseException(Throwable cause) {
        super(cause);
    }
}
