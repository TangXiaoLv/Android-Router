
package com.tangxiaolv.router.exceptions;

/**
 * Router Exception: undefined path
 */
public class NotFoundPathException extends IllegalArgumentException {


    public NotFoundPathException() {
        super();
    }

    public NotFoundPathException(String message) {
        super(message);
    }

    public NotFoundPathException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundPathException(Throwable cause) {
        super(cause);
    }
}
