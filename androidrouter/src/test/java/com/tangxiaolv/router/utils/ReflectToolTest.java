
package com.tangxiaolv.router.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public final class ReflectToolTest {

    @Test
    public void getFirstGeneric() throws NoSuchMethodException {

        String cls;

        Object o = new Object();
        cls = ReflectTool.getFirstGeneric(o);
        assertEquals(cls, null);

        List<String> list = new ArrayList<>();
        cls = ReflectTool.getFirstGeneric(list);
        assertEquals(cls, null);

        Map<String, Object> map = new HashMap<>();
        cls = ReflectTool.getFirstGeneric(map);
        assertEquals(cls, null);

        Mark<List<String>> mark = new Mark<List<String>>() {
        };
        cls = ReflectTool.getFirstGeneric(mark);
        assertEquals(cls, "java.util.List<java.lang.String>");

        IMark<List<String>> iMark = new IMark<List<String>>() {
        };
        cls = ReflectTool.getFirstGeneric(iMark);
        assertEquals(cls, "java.util.List<java.lang.String>");

        MarkImpl<List<String>> markImpl = new MarkImpl<List<String>>() {
        };
        cls = ReflectTool.getFirstGeneric(markImpl);
        assertEquals(cls, "java.util.List<java.lang.String>");

        MarkImpl<A> markA = new MarkImpl<A>() {
        };
        cls = ReflectTool.getFirstGeneric(markA);
        assertEquals(cls, A.class.getName());

        MarkImpl<B> markB = new MarkImpl<B>() {
        };
        cls = ReflectTool.getFirstGeneric(markB);
        assertEquals(cls, B.class.getName());

        MarkImpl<C> markC = new MarkImpl<C>() {
        };
        cls = ReflectTool.getFirstGeneric(markC);
        assertEquals(cls, C.class.getName());

        MarkImpl<D> markD = new MarkImpl<D>() {
        };
        cls = ReflectTool.getFirstGeneric(markD);
        assertEquals(cls, D.class.getName());
    }

    private class Mark<T> {

    }

    private interface IMark<T> {

    }

    private class MarkImpl<T> implements IMark<T> {

    }

    private class A {

    }

    private interface B {

    }

    private abstract class C {

    }

    private final class D {

    }

}
