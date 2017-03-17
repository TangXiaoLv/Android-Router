package com.tangxiaolv.router.operators;

/**
 * Abstract base class for operators that take an upstream source.
 *
 * @param <T> the upstream value type
 * @param <R> the output value type
 */
abstract class AbstractPromiseWithUpstream<T, R> extends CPromise<R> implements HasUpstreamPromise<T> {

    /**
     * The upstream source.
     */
    final CPromise<T> source;

    /**
     * Constructs a CPromiseSource wrapping the given non-null source CPromise.
     *
     * @param source the source CPromise instance, not null
     */
    AbstractPromiseWithUpstream(CPromise<T> source) {
        this.source = source;
    }

    @Override
    public CPromise<T> source() {
        return source;
    }
}