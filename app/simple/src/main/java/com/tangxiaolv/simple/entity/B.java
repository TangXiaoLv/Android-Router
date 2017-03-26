package com.tangxiaolv.simple.entity;

import com.tangxiaolv.router.interfaces.IRouter;

import java.io.Serializable;
import java.util.List;

public class B implements Serializable, IRouter {

    //Primitive type
    private String str;
    private int inte;
    private long lon;
    private double doub;
    private float flo;
    private boolean bool;

    private String[] strArr;
    private int[] inteArr;
    private long[] lonArr;
    private double[] doubArr;
    private float[] floArr;
    private boolean[] boolArr;

    private Integer iinte;
    private Long llon;
    private Double ddoub;
    private Float fflo;
    private Boolean bbool;

    private Integer[] iinteArr;
    private Long[] llonArr;
    private Double[] ddoubArr;
    private Float[] ffloArr;
    private Boolean[] bboolArr;

    //Complex type
    private List<String> listStr;
    private B1 obj;
    private B1[] objArr;
    private List<B1> objList;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getInte() {
        return inte;
    }

    public void setInte(int inte) {
        this.inte = inte;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public double getDoub() {
        return doub;
    }

    public void setDoub(double doub) {
        this.doub = doub;
    }

    public float getFlo() {
        return flo;
    }

    public void setFlo(float flo) {
        this.flo = flo;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public String[] getStrArr() {
        return strArr;
    }

    public void setStrArr(String[] strArr) {
        this.strArr = strArr;
    }

    public int[] getInteArr() {
        return inteArr;
    }

    public void setInteArr(int[] inteArr) {
        this.inteArr = inteArr;
    }

    public long[] getLonArr() {
        return lonArr;
    }

    public void setLonArr(long[] lonArr) {
        this.lonArr = lonArr;
    }

    public double[] getDoubArr() {
        return doubArr;
    }

    public void setDoubArr(double[] doubArr) {
        this.doubArr = doubArr;
    }

    public float[] getFloArr() {
        return floArr;
    }

    public void setFloArr(float[] floArr) {
        this.floArr = floArr;
    }

    public boolean[] getBoolArr() {
        return boolArr;
    }

    public void setBoolArr(boolean[] boolArr) {
        this.boolArr = boolArr;
    }

    public List<String> getListStr() {
        return listStr;
    }

    public void setListStr(List<String> listStr) {
        this.listStr = listStr;
    }

    public B1 getObj() {
        return obj;
    }

    public void setObj(B1 obj) {
        this.obj = obj;
    }

    public B1[] getObjArr() {
        return objArr;
    }

    public void setObjArr(B1[] objArr) {
        this.objArr = objArr;
    }

    public List<B1> getObjList() {
        return objList;
    }

    public void setObjList(List<B1> objList) {
        this.objList = objList;
    }

    public Integer getIinte() {
        return iinte;
    }

    public void setIinte(Integer iinte) {
        this.iinte = iinte;
    }

    public Long getLlon() {
        return llon;
    }

    public void setLlon(Long llon) {
        this.llon = llon;
    }

    public Double getDdoub() {
        return ddoub;
    }

    public void setDdoub(Double ddoub) {
        this.ddoub = ddoub;
    }

    public Float getFflo() {
        return fflo;
    }

    public void setFflo(Float fflo) {
        this.fflo = fflo;
    }

    public Boolean getBbool() {
        return bbool;
    }

    public void setBbool(Boolean bbool) {
        this.bbool = bbool;
    }

    public Integer[] getIinteArr() {
        return iinteArr;
    }

    public void setIinteArr(Integer[] iinteArr) {
        this.iinteArr = iinteArr;
    }

    public Long[] getLlonArr() {
        return llonArr;
    }

    public void setLlonArr(Long[] llonArr) {
        this.llonArr = llonArr;
    }

    public Double[] getDdoubArr() {
        return ddoubArr;
    }

    public void setDdoubArr(Double[] ddoubArr) {
        this.ddoubArr = ddoubArr;
    }

    public Float[] getFfloArr() {
        return ffloArr;
    }

    public void setFfloArr(Float[] ffloArr) {
        this.ffloArr = ffloArr;
    }

    public Boolean[] getBboolArr() {
        return bboolArr;
    }

    public void setBboolArr(Boolean[] bboolArr) {
        this.bboolArr = bboolArr;
    }
}
