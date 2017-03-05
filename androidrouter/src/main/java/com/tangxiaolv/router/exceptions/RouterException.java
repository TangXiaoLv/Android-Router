
package com.tangxiaolv.router.exceptions;

/**
 * Router Exception
 */
public class RouterException extends Exception {

    public RouterException() {
        super();
    }

    public RouterException(String message) {
        super(message);
    }

    public RouterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouterException(Throwable cause) {
        super(cause);
    }
}
