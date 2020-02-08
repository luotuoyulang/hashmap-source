package cn.luotuoyulang.hashmapsource.collection.list.arraylist;

import java.util.Arrays;

/**
 *  Fail-Fast 机制原理
 *  Fail-Fast 是我们Java集合框架为了解决集合中结构发生改变的时候,快速失败的机制。
 */
public class ArrayListTest {

    /**
     * 增加到16的长度时 ， 容量扩充到 22
     */
    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>();
//        for (int i = 0; i < 15; i++) {
//            list.add("Hello World !!!!");
//        }
        list.add("Hello World !!!!");
        list.add("123");
        System.out.println(list.size());
        list.remove(0);
        System.out.println(list.size());
        expansionCapacity();
    }

    public static void expansionCapacity(){
        System.err.println("==================================");
        Object[] elementData = new Object[]{1,2,3};
        int newCapacity = 10;
        System.out.println("没有赋值之前的数组=="+elementData.length);
        elementData = Arrays.copyOf(elementData, newCapacity);
        System.out.println("赋值之前的数组=="+elementData.length);
        for (Object elementDatum : elementData) {
            System.out.println(elementDatum);
        }
    }
}
