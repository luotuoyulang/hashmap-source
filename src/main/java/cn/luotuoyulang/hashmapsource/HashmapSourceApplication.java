package cn.luotuoyulang.hashmapsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class HashmapSourceApplication {

    public static void main(String[] args) {

        List list= new LinkedList<>();

        SpringApplication.run(HashmapSourceApplication.class, args);
    }

}
