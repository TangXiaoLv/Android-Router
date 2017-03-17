package com.tangxiaolv.router.operators;

import com.tangxiaolv.router.Reject;
import com.tangxiaolv.router.Resolve;

public final class PromiseCall<T, R> extends AbstractPromiseWithUpstream<T, R> {

    private final Func<T, R> map;

    public PromiseCall(CPromise<T> source, Func<T, R> map) {
        super(source);
        this.map = map;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void callActual(final Resolve<R> resolve, final Reject reject) {
        source.call(new Resolve<Object>() {
            @Override
            public void call(Object result) {
                resolve.call(map.call((T) result));
            }
        }, reject);
    }
}
