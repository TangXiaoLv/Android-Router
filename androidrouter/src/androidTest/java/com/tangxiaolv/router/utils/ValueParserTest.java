package com.tangxiaolv.router.utils;


import com.google.gson.Gson;

import android.support.test.runner.AndroidJUnit4;
import android.util.ArrayMap;

import com.tangxiaolv.router.entity.A;
import com.tangxiaolv.router.entity.B;
import com.tangxiaolv.router.entity.ObjGenerator;
import com.tangxiaolv.router.exceptions.ValueParseException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tangxiaolv.router.utils.ReflectTool.tryGetGeneric;
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

        String firstGeneric = tryGetGeneric(new Mark<List<A>>() {
        });
        parse = ValueParser.parse(listA, firstGeneric);
        assertEquals(parse, listA);

        firstGeneric = tryGetGeneric(new Mark<List<B>>() {
        });
        parse = ValueParser.parse(listA, firstGeneric);
        assertTrue(parse instanceof List);
        parse = ((List) parse).get(0);
        assertTrue(parse instanceof B);
        assertNotNull(((B) parse).getListStr());
    }

    @Test
    public void check_Obj_Obj() throws ValueParseException {
        A a = ObjGenerator.getA();

        Object parse;

        parse = ValueParser.parse(a, A.class.getName());
        Assert.assertEquals(parse, a);

        parse = ValueParser.parse(a, B.class.getName());
        assertTrue(parse instanceof B);
        assertNotNull(((B) parse).getListStr());
    }

    @Test
    public void check_Json_Obj() throws ValueParseException {
        A a = ObjGenerator.getA();
        String json = new Gson().toJson(a);
        Object parse = ValueParser.parse(json, A.class.getCanonicalName());
        assertTrue(parse instanceof A);
        assertNotNull(((A) parse).getListStr());
    }

    @Test
    public void check_Json_Array() throws ValueParseException {
        List<A> listA = ObjGenerator.getListA();
        String json = new Gson().toJson(listA);

        Object parse = ValueParser.parse(json, tryGetGeneric(new Mark<A[]>() {
        }));
        assertNotNull(parse);
        assertTrue(parse.getClass().isArray());
        parse = ((Object[]) parse)[0];
        assertTrue(parse instanceof A);
        assertNotNull(((A) parse).getListStr());
    }

    @Test
    public void check_Json_List() throws ValueParseException {
        List<A> listA = ObjGenerator.getListA();
        String json = new Gson().toJson(listA);

        Object parse = ValueParser.parse(json, tryGetGeneric(new Mark<List<A>>() {
        }));
        assertNotNull(parse);
        assertTrue(parse instanceof List);
        parse = ((List) parse).get(0);
        assertTrue(parse instanceof A);
        assertNotNull(((A) parse).getListStr());
    }

    @Test
    public void check_Json_Map() throws ValueParseException {
        A a = ObjGenerator.getA();
        String json = new Gson().toJson(a);

        Object parse = ValueParser.parse(json, Map.class.getCanonicalName());
        assertTrue(parse instanceof Map);
    }

    @Test
    public void check_Obj_Map() throws ValueParseException {
    }

    @Test
    public void check_Map_Obj() throws ValueParseException {
        Map<String, Object> map = new HashMap<>();
        map.put("inte", 10);
        map.put("lon", 20L);

        Object parse = ValueParser.parse(map, tryGetGeneric(new Mark<A>() {
        }));
        assertTrue(parse instanceof A);
        assertTrue(((A) parse).getInte() == 10);
    }

    @Test
    public void check_Map_Map() throws ValueParseException {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        Object parse = ValueParser.parse(map, Map.class.getCanonicalName());
        assertTrue(parse instanceof Map);

        HashMap<String, Object> maps = new HashMap<>();
        maps.put("a", 1);
        maps.put("b", true);
        maps.put("c", "3");
        parse = ValueParser.parse(maps, Map.class.getCanonicalName());
        assertTrue(parse instanceof Map);
        assertTrue(((Map) parse).get("a") instanceof Integer);
    }

    @SuppressWarnings("all")
    public static class Mark<T> {
    }
}
