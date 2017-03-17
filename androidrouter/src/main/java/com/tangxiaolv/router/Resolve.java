
package com.tangxiaolv.router;

/**
 * Third party call the result.
 *
 * @see Promise
 */
public interface Resolve<T> {

    void call(T result);
}
