package cn.luotuoyulang.hashmapsource.map.hashmap.arraylist;

import java.util.LinkedList;

public class MyArrayListHashMap<K,V> implements MyMap<K,V> {

    /**
     * 存放我们的所有的键值对集合
     * @return
     */
//    List<Entry<K,V>> listEntry = new ArrayList<>();
//
//    Object[] obj = new Object[100];

    LinkedList<Entry<K,V>>[] objects = new LinkedList[100];

    @Override
    public int size() {
//        return listEntry.size();
        return 0;
    }

    @Override
    public V put(K key, V v) {
        int hashCode = hash(key);
        LinkedList<Entry<K,V>> list = objects[hashCode];
        if(list == null){
            LinkedList<Entry<K,V>> listEntry = new LinkedList<>();
            listEntry.add(new Entry<>(key,v));
            objects[hashCode] = listEntry;
            return v;
        }
        for (Entry entry : list) {
            if(entry.getKey().equals(key)){
                entry.setValue(v);
                return v;
            }
        }
        list.add(new Entry<>(key,v));
        objects[hashCode] = list;
        return v;
    }

    static final int hash(Object key) {
        return key.hashCode();
    }

    @Override
    public V get(K key) {
//        for (Entry<K, V> kvEntry : listEntry) {
//            if(kvEntry.k.equals(key)){
//                return kvEntry.getValue();
//            }
//        }
//        return null;

//        int i = key.hashCode();
//        i = i % obj.length;
//        return  (V)obj[i];

        int hashCode = hash(key);
        LinkedList<Entry<K,V>> list = objects[hashCode];
        if(list == null){
            return null;
        }
        for (Entry entry : list) {
            if(entry.getKey().equals(key)){
                return (V) entry.getValue();
            }
        }
        return null;
    }

    static class Entry<K,V> implements MyMap.Entry<K,V> {

        private K k;
        private V v;

        public Entry(K k, V v) {
            this.k = k;
            this.v = v;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        @Override
        public V setValue(V value) {
            return this.v = value;
        }
    }
}
