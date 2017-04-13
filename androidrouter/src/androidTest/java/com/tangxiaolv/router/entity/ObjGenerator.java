package com.tangxiaolv.router.entity;

import com.tangxiaolv.router.entity.A;
import com.tangxiaolv.router.entity.A1;
import com.tangxiaolv.router.entity.B;
import com.tangxiaolv.router.entity.B1;

import java.util.ArrayList;
import java.util.List;

public class ObjGenerator {

    public static A getA() {
        A a = new A();
        a.setBool(true);
        a.setBoolArr(new boolean[]{true, false});
        a.setDoub(1d);
        a.setDoubArr(new double[]{1d, 1d});
        a.setFlo(2f);
        a.setFloArr(new float[]{2f, 2f});
        a.setInte(3);
        a.setInteArr(new int[]{3, 3});
        a.setLon(4L);
        a.setLonArr(new long[]{4L, 4L});

        a.setBbool(true);
        a.setBboolArr(new Boolean[]{true, false});
        a.setDdoub(1d);
        a.setDdoubArr(new Double[]{1d, 1d});
        a.setFflo(2f);
        a.setFfloArr(new Float[]{2f, 2f});
        a.setIinte(3);
        a.setIinteArr(new Integer[]{3, 3});
        a.setLlon(4L);
        a.setLlonArr(new Long[]{4L, 4L});

        a.setStr("5");
        a.setStrArr(new String[]{"5", "5"});

        List<String> listStr = new ArrayList<>();
        listStr.add("6");
        listStr.add("6");
        listStr.add("6");
        a.setListStr(listStr);

        a.setObj(getA1());
        a.setObjList(getListA1());
        a.setObjArr(new A1[]{getA1(), getA1()});
        return a;
    }

    private static A1 getA1() {
        A1 a = new A1();
        a.setBool(true);
        a.setBoolArr(new boolean[]{true, false});
        a.setDoub(1d);
        a.setDoubArr(new double[]{1d, 1d});
        a.setFlo(2f);
        a.setFloArr(new float[]{2f, 2f});
        a.setInte(3);
        a.setInteArr(new int[]{3, 3});
        a.setLon(4L);
        a.setLonArr(new long[]{4L, 4L});

        a.setBbool(true);
        a.setBboolArr(new Boolean[]{true, false});
        a.setDdoub(1d);
        a.setDdoubArr(new Double[]{1d, 1d});
        a.setFflo(2f);
        a.setFfloArr(new Float[]{2f, 2f});
        a.setIinte(3);
        a.setIinteArr(new Integer[]{3, 3});
        a.setLlon(4L);
        a.setLlonArr(new Long[]{4L, 4L});

        a.setStr("5");
        a.setStrArr(new String[]{"5", "5"});

        List<String> listStr = new ArrayList<>();
        listStr.add("6");
        listStr.add("6");
        listStr.add("6");
        a.setListStr(listStr);
        return a;
    }

    public static B getB() {
        B b = new B();
        b.setBool(true);
        b.setBoolArr(new boolean[]{true, false});
        b.setDoub(7d);
        b.setDoubArr(new double[]{7d, 7d});
        b.setFlo(8f);
        b.setFloArr(new float[]{8f, 8f});
        b.setInte(9);
        b.setInteArr(new int[]{9, 9});
        b.setLon(10L);
        b.setLonArr(new long[]{10L, 10L});

        b.setBbool(true);
        b.setBboolArr(new Boolean[]{true, false});
        b.setDdoub(7d);
        b.setDdoubArr(new Double[]{7d, 7d});
        b.setFflo(8f);
        b.setFfloArr(new Float[]{8f, 8f});
        b.setIinte(9);
        b.setIinteArr(new Integer[]{9, 9});
        b.setLlon(10L);
        b.setLlonArr(new Long[]{10L, 10L});

        b.setStr("11");
        b.setStrArr(new String[]{"11", "11"});

        List<String> listStr = new ArrayList<>();
        listStr.add("12");
        listStr.add("12");
        listStr.add("12");
        b.setListStr(listStr);

        b.setObj(getB1());
        b.setObjList(getListB1());
        b.setObjArr(new B1[]{getB1(), getB1()});
        return b;
    }

    private static B1 getB1() {
        B1 b = new B1();
        b.setBool(true);
        b.setBoolArr(new boolean[]{true, false});
        b.setDoub(7d);
        b.setDoubArr(new double[]{7d, 7d});
        b.setFlo(8f);
        b.setFloArr(new float[]{8f, 8f});
        b.setInte(9);
        b.setInteArr(new int[]{9, 9});
        b.setLon(10L);
        b.setLonArr(new long[]{10L, 10L});

        b.setBbool(true);
        b.setBboolArr(new Boolean[]{true, false});
        b.setDdoub(7d);
        b.setDdoubArr(new Double[]{7d, 7d});
        b.setFflo(8f);
        b.setFfloArr(new Float[]{8f, 8f});
        b.setIinte(9);
        b.setIinteArr(new Integer[]{9, 9});
        b.setLlon(10L);
        b.setLlonArr(new Long[]{10L, 10L});

        b.setStr("11");
        b.setStrArr(new String[]{"11", "11"});

        List<String> listStr = new ArrayList<>();
        listStr.add("12");
        listStr.add("12");
        listStr.add("12");
        b.setListStr(listStr);
        return b;
    }

    private static List<A1> getListA1() {
        List<A1> listA = new ArrayList<>();
        listA.add(getA1());
        listA.add(getA1());
        listA.add(getA1());
        return listA;
    }

    private static List<B1> getListB1() {
        List<B1> listB = new ArrayList<>();
        listB.add(getB1());
        listB.add(getB1());
        listB.add(getB1());
        return listB;
    }

    public static List<A> getListA() {
        List<A> listA = new ArrayList<>();
        listA.add(getA());
        listA.add(getA());
        listA.add(getA());
        return listA;
    }

    public static List<B> getListB() {
        List<B> listB = new ArrayList<>();
        listB.add(getB());
        listB.add(getB());
        listB.add(getB());
        return listB;
    }
}
