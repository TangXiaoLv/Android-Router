
package com.tangxiaolv.router;

/**
 * Third party call the result.
 *
 * @see Promise
 */
public interface Resolve {

    /**
     * Return result,Called in main thread.
     *
     * @param type   type of result
     * @param result result
     */
    void call(String type, Object result);
}
