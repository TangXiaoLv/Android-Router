package com.tangxiaolv.router;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.ArrayMap;

import com.tangxiaolv.router.ParamsWrapper;

import org.json.JSONException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ParamsWrapperTest {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Test
    public void checkObj() throws JSONException {
        String json = "{'a':1,'b':'2'}";

        ParamsWrapper wrapper = new ParamsWrapper(json);
        String json2 = "{'c':1,'d':'2'}";
        List<String> arr2 = new ArrayList<>();
        arr2.add("a");
        arr2.add("b");
        Map<String, Object> map2 = new ArrayMap<>();
        map2.put("A","1");
        map2.put("B","2");

        wrapper.append(json2);
        wrapper.append(arr2);
        wrapper.append(map2);
        System.out.println();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Test
    public void checkArray() throws JSONException {
        String json = "[1,2,3,4]";

        ParamsWrapper wrapper = new ParamsWrapper(json);
        String json2 = "[5,6,7,8]";
        List<String> arr2 = new ArrayList<>();
        arr2.add("9");
        arr2.add("10");

        wrapper.append(json2);
        wrapper.append(arr2);
        System.out.println();
    }
}
