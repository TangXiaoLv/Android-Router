package com.tangxiaolv.router.utils;


import com.google.gson.Gson;

import android.support.test.runner.AndroidJUnit4;

import com.tangxiaolv.router.entity.A;
import com.tangxiaolv.router.entity.B;
import com.tangxiaolv.router.entity.ObjGenerator;
import com.tangxiaolv.router.exceptions.ValueParseException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static com.tangxiaolv.router.utils.ReflectTool.getFirstGeneric;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ValueParserTest {

    @Test
    public void check_Primitive() throws ValueParseException {
        Object parse;
        parse = ValueParser.parse("1", Integer.class.getName());
        assertEquals(parse, 1);
        parse = ValueParser.parse("1", Double.class.getName());
        assertEquals(parse, 1d);
        parse = ValueParser.parse("1", Long.class.getName());
        assertEquals(parse, 1L);
        parse = ValueParser.parse("1", Float.class.getName());
        assertEquals(parse, 1f);
        parse = ValueParser.parse("true", Boolean.class.getName());
        assertEquals(parse, true);
    }

    @Test
    public void check_List_List() throws ValueParseException {
        List<A> listA = ObjGenerator.getListA();
        Object parse;

        String firstGeneric = getFirstGeneric(new Mark<List<A>>() {
        });
        parse = ValueParser.parse(listA, firstGeneric);
        assertEquals(parse, listA);

        firstGeneric = getFirstGeneric(new Mark<List<B>>() {
        });
        parse = ValueParser.parse(listA, firstGeneric);
        assertTrue(parse instanceof List);
        assertTrue(((List) parse).get(0) instanceof B);
    }

    @Test
    public void check_Obj_Obj() throws ValueParseException {
        A a = ObjGenerator.getA();

        Object parse;

        parse = ValueParser.parse(a, A.class.getName());
        Assert.assertEquals(parse, a);

        parse = ValueParser.parse(a, B.class.getName());
        assertTrue(parse instanceof B);
    }

    @Test
    public void check_Obj_Map() throws ValueParseException {
//        A a = ObjGenerator.getA();
//
//        Object parse = ValueParser.parse(a, Map.class.getCanonicalName());
//        assertTrue(parse instanceof Map);
    }

    @Test
    public void check_Map_Obj() {


    }

    @Test
    public void check_Json_Obj() throws ValueParseException {
        A a = ObjGenerator.getA();
        String json = new Gson().toJson(a);
        Object parse = ValueParser.parse(json, A.class.getCanonicalName());
        assertTrue(parse instanceof A);
    }

    @Test
    public void check_Json_Array() throws ValueParseException {
        List<A> listA = ObjGenerator.getListA();
        String json = new Gson().toJson(listA);

        Object parse = ValueParser.parse(json, getFirstGeneric(new Mark<A[]>() {
        }));
        assertNotNull(parse);
        assertTrue(parse.getClass().isArray());
        assertTrue(((Object[]) parse)[0] instanceof A);
    }

    @Test
    public void check_Json_List() throws ValueParseException {
        List<A> listA = ObjGenerator.getListA();
        String json = new Gson().toJson(listA);

        Object parse = ValueParser.parse(json, getFirstGeneric(new Mark<List<A>>() {
        }));
        assertNotNull(parse);
        assertTrue(parse instanceof List);
        assertTrue(((List) parse).get(0) instanceof A);

    }

    @Test
    public void check_Json_Map() throws ValueParseException {
        A a = ObjGenerator.getA();
        String json = new Gson().toJson(a);

        Object parse = ValueParser.parse(json, Map.class.getCanonicalName());
        assertTrue(parse instanceof Map);
    }

    @SuppressWarnings("all")
    public static class Mark<T> {
    }
}
