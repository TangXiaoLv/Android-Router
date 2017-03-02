
package com.tangxiaolv.simple;

import java.util.List;

public class Entity_1 {

    private String key1;
    private int key2;
    private boolean key3;
    private float key4;
    private List<Entity> key5;

    public Entity_1() {
    }

    public Entity_1(String key1, int key2, boolean key3, float key4) {
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

    public List<Entity> getKey5() {
        return key5;
    }

    public void setKey5(List<Entity> key5) {
        this.key5 = key5;
    }
}
