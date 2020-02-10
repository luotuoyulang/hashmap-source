package cn.luotuoyulang.hashmapsource.collection.set.hashset;

import java.util.HashSet;
import java.util.Set;

public class Test {

    /**
     * HashSet 集合底层包装了 HashMap 集合，能够继承HashMap 特性 填充value
     * 1、HashSet 底层 add  实现 包装了HashMap 集合
     * HashSet 如何实现保证key 不重复
     * 答：HashSet 包装了 HashMap , HashMap 中 key 是唯一的，所以 HashSet 元素值也是唯一的
     *     HashMap 底层 通过 HashCode 相同且 equals 比较对象值相等
     * @param args
     */
    public static void main(String[] args) {
        Set<Object> objects = new HashSet<>();
        objects.add(null);
        for (Object object : objects) {
            System.out.println(object);
        }
    }
}
