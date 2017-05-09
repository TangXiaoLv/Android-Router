
package com.tangxiaolv.router.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public final class ReflectToolTest {

    @Test
    public void checkMap() {
        String cls;

        Map<String, Object> map = new HashMap<>();
        cls = ReflectTool.tryGetGeneric(map);
        assertEquals(cls, null);

        Mark<Map<String,Object>> mark = new Mark<Map<String, Object>>() {
        };
        cls = ReflectTool.tryGetGeneric(mark);
        assertEquals(cls, "java.util.Map<java.lang.String, java.lang.Object>");
    }

    @Test
    public void checkList() {
        String cls;

        List<String> list = new ArrayList<>();
        cls = ReflectTool.tryGetGeneric(list);
        assertEquals(cls, null);

        Mark<List<String>> mark = new Mark<List<String>>() {
        };
        cls = ReflectTool.tryGetGeneric(mark);
        assertEquals(cls, "java.util.List<java.lang.String>");

        IMark<List<String>> iMark = new IMark<List<String>>() {
        };
        cls = ReflectTool.tryGetGeneric(iMark);
        assertEquals(cls, "java.util.List<java.lang.String>");

        MarkImpl<List<String>> markImpl = new MarkImpl<List<String>>() {
        };
        cls = ReflectTool.tryGetGeneric(markImpl);
        assertEquals(cls, "java.util.List<java.lang.String>");
    }

    @Test
    public void checkCustomObj() {
        String cls;

        Object o = new Object();
        cls = ReflectTool.tryGetGeneric(o);
        assertEquals(cls, null);

        MarkImpl<A> markA = new MarkImpl<A>() {
        };
        cls = ReflectTool.tryGetGeneric(markA);
        assertEquals(cls, A.class.getName());

        MarkImpl<B> markB = new MarkImpl<B>() {
        };
        cls = ReflectTool.tryGetGeneric(markB);
        assertEquals(cls, B.class.getName());

        MarkImpl<C> markC = new MarkImpl<C>() {
        };
        cls = ReflectTool.tryGetGeneric(markC);
        assertEquals(cls, C.class.getName());

        MarkImpl<D> markD = new MarkImpl<D>() {
        };
        cls = ReflectTool.tryGetGeneric(markD);
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
