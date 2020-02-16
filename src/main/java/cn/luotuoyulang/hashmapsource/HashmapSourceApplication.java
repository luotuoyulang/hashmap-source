package cn.luotuoyulang.hashmapsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class HashmapSourceApplication {

    public static void main(String[] args) {

        List list= new LinkedList<>();

        TreeMap<Object, Object> objectObjectTreeMap = new TreeMap<>();

        HashMap<Object, Object> map = new HashMap<>();
        map.put("","");

        SpringApplication.run(HashmapSourceApplication.class, args);
    }

}
