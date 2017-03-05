package com.tangxiaolv.router.exceptions;

public class ValueParserException extends Exception {

    public ValueParserException() {
        super();
    }

    public ValueParserException(String message) {
        super(message);
    }

    public ValueParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueParserException(Throwable cause) {
        super(cause);
    }
}
