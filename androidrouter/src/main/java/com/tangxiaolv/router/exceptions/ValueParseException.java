package com.tangxiaolv.router.exceptions;

/**
 * Router params parser exception {@link com.tangxiaolv.router.utils.ValueParser}
 */
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
