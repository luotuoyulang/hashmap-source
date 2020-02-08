package cn.luotuoyulang.hashmapsource.collection.list.vector;

/**
 * Vector 与  ArrayList 集合区别
 * 相同点：底层都是采用数组实现
 * 1、ArrayList 默认不会对我们数组做初始化 （第一次调用add方法的时候 才会初始化）
 *    vector 默认初始化的大小为10
 * 2、ArrayList 扩容为原来的  0.5 倍
 *    vector 在原来的基础上再增加一倍  ， 可以通过构造方法设置扩容机制
 * 3、ArrayList 线程不安全
 *    vector 线程安全
 *    CopyWriteArrayList 在增加和删除时加锁，在查询的时候没有加锁，因为有violet 关键字
 * 疑问：
 * 1、为什么填一个元素，数组的容量会被扩容到 20
 * 2、violet 关键字的用发
 * 总结：
 * 1、看源码，能把英文注解翻译成中文，就算成功
 */
public class VectorTest {

    public static void main(String[] args) {
        MyVector<String> myVector = new MyVector<>();
        myVector.add("My Vector !!!");
        myVector.add("我得  Veco");
        System.out.println(myVector.get(1));
        System.out.println(myVector.size());
    }
}
