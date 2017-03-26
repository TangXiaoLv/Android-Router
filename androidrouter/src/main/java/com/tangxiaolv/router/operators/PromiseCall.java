package com.tangxiaolv.router.operators;

import com.tangxiaolv.router.Reject;
import com.tangxiaolv.router.Resolve;
import com.tangxiaolv.router.exceptions.ValueParseException;
import com.tangxiaolv.router.utils.ReflectTool;
import com.tangxiaolv.router.utils.ValueParser;

public final class PromiseCall<T, R> extends AbstractPromiseWithUpstream<T, R> {

    private final Func<T, R> func;

    public PromiseCall(CPromise<T> source, Func<T, R> func) {
        super(source);
        this.func = func;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void callActual(final Resolve<R> resolve, final Reject reject) {
        source.call(new Resolve<Object>() {
            @Override
            public void call(Object result) {
                try {
                    String firstGeneric = ReflectTool.getFirstGeneric(func);
                    resolve.call(func.call((T) ValueParser.parse(result, firstGeneric)));
                } catch (ValueParseException e) {
                    reject.call(e);
                }
            }
        }, reject);
    }
}
