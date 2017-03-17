package com.tangxiaolv.router.operators;

/**
 * Interface indicating the implementor has an upstream CPromise-like source available via {@link
 * #source()} method.
 *
 * @param <T> the value type
 */
public interface HasUpstreamPromise<T> {

    /**
     * Returns the source CPromise.
     *
     * @return the source CPromise-like
     */
    CPromise<T> source();
}