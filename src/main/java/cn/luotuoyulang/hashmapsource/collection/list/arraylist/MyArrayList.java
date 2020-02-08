package cn.luotuoyulang.hashmapsource.collection.list.arraylist;

import java.util.Arrays;

public class MyArrayList<E> implements MyList<E>{

    protected transient int modCount = 0;

    private static final int DEFAULT_CAPACITY = 10;

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    // 定义一个空集合
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    // transient  不需要序列化，可节约磁盘空间 , 作为最终集合
    transient Object[] elementData; // non-private to simplify nested class access


    private int size;

    public MyArrayList() {
        // 给集合初始化一个空集合
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * byte 8位，1字节，最大存储数据量是255，存放的数据范围是-128〜127之间
     * short：16位，2字节，最大数据存储量是65536，数据范围是-32768〜32767之间.
     * INT：32位，4字节为，最大数据存储容量是2的32次方减1，数据范围是负的2的31次方到正的2的31次方减1
     * long：64位，8字节，最大数据存储容量是2的64次方减1，数据范围为负的2的63次方到正的2的63次方减1
     * float：32位，一位符号数3.4E-4 5~1.4 E38，直接赋值时必须在数字后加F或f.
     * double：64位，数据范围在4.9E-324~1.8e308，赋值时可以加d或d也可以不加
     * ————————————————
     * 版权声明：本文为CSDN博主「PPPPPeanut」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/peanutwzk/article/details/79771484
     * @param minCapacity
     * @return
     */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        // 旧数组 又移一位 然后再加上  旧数组
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            // 第一次对我们的数组作初始化操作
            newCapacity = minCapacity;
        // 限制我们数组扩容最大值  数组最大值  MAX_ARRAY_SIZE
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            // Integer.MAX_VALUE - 8 = 2 的 32 次方 - 8
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    private void ensureExplicitCapacity(int minCapacity) {
        // 线程安全问题
        modCount++;

        // overflow-conscious code
        // 判断是否需要创建新数组（数组是否扩容）
        if (minCapacity - elementData.length > 0){
            grow(minCapacity);
        }
    }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    @Override
    public boolean add(Object e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    E elementData(int index) {
        return (E) elementData[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E remove(int index) {
        // 检查我们的下标位置是否越界
        rangeCheck(index);

        modCount++;
        // 获取要删除的对象
        E oldValue = elementData(index);
        // 就算移动得位置
        int numMoved = size - index - 1;
        // 判断如果删除数据得时候，不是最后一位得情况下，将删除后面的数据往前移动一位
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        // 如果numMoved 为 0 得情况下，说明后面不需要往前移动，直接将最后一条数据赋值为null
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }
}
