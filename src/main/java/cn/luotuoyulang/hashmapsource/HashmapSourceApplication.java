package cn.luotuoyulang.hashmapsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class HashmapSourceApplication {

    public static void main(String[] args) {


        new ArrayList<>();
        List list= new LinkedList<>();

        Map<Object, Object> objectObjectTreeMap = new TreeMap<>();

        HashMap<Object, Object> map = new HashMap<>();
        HashMap<Object, Object> map2 = map;
        System.out.println(map2.size());
        map.put("a","小刘");
        map.put(97,"abc");
        System.out.println(map2.size());
        SpringApplication.run(HashmapSourceApplication.class, args);

        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
//        objectObjectConcurrentHashMap.put()
    }

}
