package com.tangxiaolv.router.entity;

import com.tangxiaolv.router.interfaces.IRouter;

import java.io.Serializable;
import java.util.List;

public class B extends Base implements Serializable, IRouter {

    private B1 obj;
    private B1[] objArr;
    private List<B1> objList;

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
}
