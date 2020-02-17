package cn.luotuoyulang.hashmapsource.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



public class Dog {
    private String name;

    public Dog(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dog() {
    }

    public static void main(String[] args) {
        Dog dog = new Dog("红色");
        String name = dog.getName();
        dog.setName("绿色");
        System.out.println(dog.getName());
        System.out.println(name);

        String a = new String("123");
        String b = a;
        a = new String("456");
        System.out.println(b);
    }
}
