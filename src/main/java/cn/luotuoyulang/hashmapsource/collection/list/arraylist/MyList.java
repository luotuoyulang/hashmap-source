package cn.luotuoyulang.hashmapsource.collection.list.arraylist;

public interface MyList<E>{

    /**
     * 往集合中添加我们的元素
     * @param e
     * @return
     */
    boolean add(E e);

    /**
     * 使用下标查询到我们的集合元素
     * @param index
     * @return
     */
    E get(int index);

    /**
     * 集合的大小
     * @return
     */
    int size();

    /**
     * 删除方法
     * @param index
     * @return
     */
    E remove(int index);

}
