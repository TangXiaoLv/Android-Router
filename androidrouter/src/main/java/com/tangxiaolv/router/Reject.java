
package com.tangxiaolv.router;

/**
 * Third party call the error.
 *
 * @see Promise
 */
public interface Reject {

    /**
     * Returns the error,Called in main thread.
     *
     * @param e {@link com.tangxiaolv.router.exceptions.RouterRemoteException}
     */
    void call(Exception e);
}
