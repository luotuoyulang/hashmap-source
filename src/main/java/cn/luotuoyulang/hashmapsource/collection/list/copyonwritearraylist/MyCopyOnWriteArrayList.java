package cn.luotuoyulang.hashmapsource.collection.list.copyonwritearraylist;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 效率低，适用于读取配置，不需要经常发生改变的环境
 * @param <E>
 */
public class MyCopyOnWriteArrayList<E> implements MyList<E>{

    /**
     * synchronized(不做过多解释)
     *
     * 同步块大家都比较熟悉，通过 synchronized 关键字来实现，所有加上synchronized 和 块语句，在多线程访问的时候，同一时刻只能有一个线程能够用
     *
     * synchronized 修饰的方法 或者 代码块。
     *
     * volatile
     *
     * 用volatile修饰的变量，线程在每次使用变量的时候，都会读取变量修改后的最的值。volatile很容易被误用，用来进行原子性操作。
     */
    private transient volatile Object[] array;

    final transient ReentrantLock lock = new ReentrantLock();

    final void setArray(Object[] a) {
        array = a;
    }

    public MyCopyOnWriteArrayList() {
        setArray(new Object[0]);
    }

    final Object[] getArray() {
        return array;
    }

    /**
     * 锁机制，保证线程安全
     * @param e
     * @return
     */
    @Override
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            // 1、获取原来的数组
            Object[] elements = getArray();
            // 2、获取原来数组的长度为 0
            int len = elements.length;

            // 3、新数组
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }

    private E get(Object[] a, int index) {
        return (E) a[index];
    }

    @Override
    public E get(int index) {
        return get(getArray(), index);
    }

    @Override
    public int size() {
        return 0;
    }
}
