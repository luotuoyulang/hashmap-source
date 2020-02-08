package cn.luotuoyulang.hashmapsource.collection.list.arraylist;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * fail-fast 机制，解决办法。。
 *
 *         final void checkForComodification() {
 *             if (modCount != expectedModCount)
 *                 throw new ConcurrentModificationException();
 *         }
 *
 *  如果线程不安全,ArrayList会抛出 ConcurrentModificationException 异常
 */
public class ThreadArrayListTest {

    public static ArrayList<String> list = new ArrayList<>();

    /**
     * 此集合是线程安全集合，不会报错
     */
    public static CopyOnWriteArrayList<String> list1 = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {

        new Thread(){
            @Override
            public void run() {
                for(int i = 0; i< 10 ; i++){
                    list.add(String.valueOf(i));
                    print();
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                for(int i = 0; i< 10 ; i++){
                    list.add(String.valueOf(i));
                    print();
                }
            }
        }.start();
    }

    public static void print(){
        for (String s : list) {
            System.out.println(s);
        }
    }

}
