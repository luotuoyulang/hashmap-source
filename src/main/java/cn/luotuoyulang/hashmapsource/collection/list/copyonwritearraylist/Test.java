package cn.luotuoyulang.hashmapsource.collection.list.copyonwritearraylist;

import java.util.concurrent.CopyOnWriteArrayList;

public class Test {

    public static void main(String[] args) {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        list.add("123");
        System.out.println(list.get(0));
    }
}
