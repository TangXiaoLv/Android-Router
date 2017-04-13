
package com.tangxiaolv.router;

/**
 * Third party call the result.
 *
 * @see Promise
 */
public interface Resolve<T> {

    /**
     * Note:Support different types transformation.eg:A -> B
     */
    void call(T result);
}
