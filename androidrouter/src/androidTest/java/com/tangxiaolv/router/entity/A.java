package com.tangxiaolv.router.entity;

import com.tangxiaolv.router.interfaces.IRouter;

import java.util.List;

public class A extends Base implements IRouter {
    private A1 obj;
    private A1[] objArr;
    private List<A1> objList;

    public A1 getObj() {
        return obj;
    }

    public void setObj(A1 obj) {
        this.obj = obj;
    }

    public A1[] getObjArr() {
        return objArr;
    }

    public void setObjArr(A1[] objArr) {
        this.objArr = objArr;
    }

    public List<A1> getObjList() {
        return objList;
    }

    public void setObjList(List<A1> objList) {
        this.objList = objList;
    }
}
