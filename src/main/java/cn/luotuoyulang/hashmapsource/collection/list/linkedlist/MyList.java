package cn.luotuoyulang.hashmapsource.collection.list.arraylist.linkedlist;

public interface MyList<E>{

    boolean add(E e);

    E get(int index);

    int size();

    E remove(int index);

    boolean remove(Object o);

}
