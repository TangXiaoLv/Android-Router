package com.tangxiaolv.router.exceptions;

/**
 * Third party throw
 */
public class RouterRemoteException extends Exception {

    public RouterRemoteException() {
        super();
    }

    public RouterRemoteException(String message) {
        super(message);
    }

    public RouterRemoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouterRemoteException(Throwable cause) {
        super(cause);
    }
}
