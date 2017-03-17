package com.tangxiaolv.router.operators;

import com.tangxiaolv.router.Reject;
import com.tangxiaolv.router.Resolve;

public final class PromiseMap<T, R> extends AbstractPromiseWithUpstream<T, R> {

    private final Func<T, R> map;

    public PromiseMap(CPromise<T> source, Func<T, R> map) {
        super(source);
        this.map = map;
    }

    @Override
    public void callActual(final Resolve<R> resolve, final Reject reject) {
        source.callActual(new Resolve<T>() {
            @Override
            public void call(T result) {
                resolve.call(map.call(result));
            }
        }, reject);
    }
}
