package com.tangxiaolv.router.exceptions;

/**
 * Third party throw
 */
public class RouterRemoteException extends Exception {

    private int code;

    public RouterRemoteException() {
        super();
    }

    public RouterRemoteException(int code, String message) {
        super(message);
        this.code = code;
    }

    public RouterRemoteException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public RouterRemoteException(int code, Throwable cause) {
        super(cause);
        this.code = code;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}