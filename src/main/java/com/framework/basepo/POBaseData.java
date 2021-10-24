package com.framework.basepo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@Data
public class POBaseData {
//    public HashMap<String,HashMap<String,String>> dataMap;
//    public List<HashMap<String, Object>> data;

    public String name;
    public String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    /*public ArrayList<HashMap<String, HashMap<String, String>>> getDataMap() {
        return dataMap;
    }

    public void setDataMap(ArrayList<HashMap<String, HashMap<String, String>>> dataMap) {
        this.dataMap = dataMap;
    }*/
}
