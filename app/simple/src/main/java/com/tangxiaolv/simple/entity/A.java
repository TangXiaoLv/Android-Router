
package com.tangxiaolv.simple.entity;

import com.tangxiaolv.router.interfaces.IRouter;

import java.util.List;

public class A implements IRouter{

    private String key1;
    private int key2;
    private boolean key3;
    private float key4;
    private List<B> key5;
    private C key6;

    public A() {
    }

    public A(String key1, int key2, boolean key3, float key4) {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.key4 = key4;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public int getKey2() {
        return key2;
    }

    public void setKey2(int key2) {
        this.key2 = key2;
    }

    public boolean isKey3() {
        return key3;
    }

    public void setKey3(boolean key3) {
        this.key3 = key3;
    }

    public float getKey4() {
        return key4;
    }

    public void setKey4(float key4) {
        this.key4 = key4;
    }

    public List<B> getKey5() {
        return key5;
    }

    public void setKey5(List<B> key5) {
        this.key5 = key5;
    }

    public C getKey6() {
        return key6;
    }

    public void setKey6(C key6) {
        this.key6 = key6;
    }
}
