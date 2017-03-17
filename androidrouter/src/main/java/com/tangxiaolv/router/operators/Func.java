package com.tangxiaolv.router.operators;

/**
 * All Func extend from this.
 */
public interface Func<T, R> {

    /**
     * Apply some calculation to the input value and return some other value.
     *
     * @param t the input value
     * @return the output value
     */
    R call(T t);
}
