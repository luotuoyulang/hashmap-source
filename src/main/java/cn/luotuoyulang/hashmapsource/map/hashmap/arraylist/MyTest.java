package cn.luotuoyulang.hashmapsource.map.hashmap.arraylist;

import java.util.*;

public class MyTest {

    public static void main(String[] args) {
        Map<Object, Object> hashMap = new HashMap<>();
        MyArrayListHashMap myArrayListHashMap = new MyArrayListHashMap();
        myArrayListHashMap.put(97,"xiaoli");
        myArrayListHashMap.put("a","小李");
        System.out.println(myArrayListHashMap.get("a"));
        System.out.println(myArrayListHashMap.get(97));
    }
}
