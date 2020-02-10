package cn.luotuoyulang.hashmapsource.collection.list.linkedlist;


import java.util.LinkedList;

public class LinkedTest {

    public static void main(String[] args) {
        new LinkedList<>();
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        System.out.println(list.get(2));
        list.remove("a");
        System.out.println(list.size());
    }
}
