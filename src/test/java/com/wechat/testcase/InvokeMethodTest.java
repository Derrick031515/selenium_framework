package com.wechat.testcase;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class InvokeMethodTest {
    public void test(ArrayList<LinkedHashMap<String,Object>> mapList){
        System.out.println(mapList.size());
        mapList.forEach(ele->{
            ele.entrySet().forEach(entry->{
                System.out.println("key="+entry.getKey()+",value="+entry.getValue());
            });
        });
    }
}
