package cn.luotuoyulang.hashmapsource.hashmap.arraylist;

/**
 * hashMap 中存放一条数据 包含那些信息  key 和 value 使用一个对象实现包装
 * @param <K>
 * @param <V>
 */
public interface MyMap<K,V> {

    int size();

    V put(K key, V value);

    V get(K key);

    /**
     * 存放一套键值对
     * @param <K>
     * @param <V>
     */
    interface Entry<K,V> {

        K getKey();

        V getValue();

        /**
         * 设置 value
         * @param value
         * @return
         */
        V setValue(V value);
    }
}
