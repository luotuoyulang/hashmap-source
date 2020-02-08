package cn.luotuoyulang.hashmapsource.collection.list.arraylist.linkedlist.mylist;

public class Test {

    public Node last;

    public Node first;

    class Node{
        Test.Node pre;
        Test.Node next;
        Test.Node element;

        public Node(Node pre, Node next, Node element) {
            this.pre = pre;
            this.next = next;
            this.element = element;
        }
    }


}
