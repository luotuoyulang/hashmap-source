package cn.luotuoyulang.hashmapsource.collection.list.linkedlist;

/**
 *  arrayList 集合底层数组结构
 *  优点：查询效率非常快 直接可以使用下标定位到元素 时间复杂度非常低 占用内存 增删改效率低
 *  时间复杂度与空间复杂度
 *
 *  链表数据结构   kinked  node 节点
 *
 *  Node 对象中 中包含这些参数  上一个节点  predNode; 当前元素值 item  ; 下一个节点  nextNode;
 *  first 元素  第一个元素 ，  last 元素 最后一个元素
 *  第一个元素的 pred 是null ，每个节点的next 都和下一个 元素的 pred 相同
 *
 *  总结：
 *  1、比较像责任链
 *  2、折半查找法，该个遍历，效率极低
 *  3、在底层中使用静态内部类 Node 节点存放节点元素，三个属性 pred (关联的上一个节点) ，item 当前的值 ，next 下一个节点
 *  4、Add 原理是如何实现 一直在链表之后新增
 *  5、Get 原理 链表缺点：查询效率非常低，所以 linkedList 中采用折半查找法 范围查询定位node 节点
 *  6、删除原理：就是改变头尾结合指向。
 * @param <E>
 */
public class MyLinkedList<E> implements MyList<E> {

    /**
     * 最后一个节点
     */
    transient MyLinkedList.Node<E> last;

    transient int size = 0;

    /**
     * Pointer to first node.
     * Invariant: (first == null && last == null) ||
     *            (first.prev == null && first.item != null)
     * 第一个节点
     */
    transient MyLinkedList.Node<E> first;

    protected transient int modCount = 0;

    public MyLinkedList() {
    }

    private static class Node<E> {
        /**
         * 节点元素值
         */
        E item;
        /**
         * 当前节点的下一个node
         */
        MyLinkedList.Node<E> next;
        /**
         * 当前节点的上一个node
         */
        MyLinkedList.Node<E> prev;

        /**
         * 使用构造函数传递参数
         * @param prev
         * @param element
         * @param next
         */
        Node(MyLinkedList.Node<E> prev, E element, MyLinkedList.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        public Node(E item) {
            this.item = item;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        /**
         * 测试代码
         * @param args
         */
        public static void main(String[] args) {
            Node<String> node1 = new Node<>("第一关");
            Node<String> node2 = new Node<>("第二关");
            node1.setNext(node2);
            node2.setPrev(node1);
            System.out.println(node1);
        }
    }

    void linkLast(E e) {
        // 1、封装我们当前自定义元素,获取当前的最后一个节点
        final MyLinkedList.Node<E> l = last;
        // 封装我们当前自定义元素
        final MyLinkedList.Node<E> newNode = new MyLinkedList.Node<>(l, e, null);
        // 当前新增节点肯定是链表中最后一个节点
        last = newNode;
        if (l == null)
            // 如果我们链表中没有最后一个节点说明 当前新增的元素是一个
            first = newNode;
        else
            // 原来的最后一个节点的下一个节点就是当前新增的节点
            l.next = newNode;
        size++;
        modCount++;
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    MyLinkedList.Node<E> node(int index) {
//         assert isElementIndex(index);

        if (index < (size >> 1)) {
            MyLinkedList.Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            MyLinkedList.Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    @Override
    public E get(int index) {
        // 查询索引是否越界
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public int size() {
        return size;
    }

    E unlink(MyLinkedList.Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final MyLinkedList.Node<E> next = x.next;
        final MyLinkedList.Node<E> prev = x.prev;

        if (prev == null) {
            // 判断当前对象是第一个对象
            first = next;
        } else {
            // 将上一个对象的下一个对象赋值
            prev.next = next;
            // 将当前对象置为空  等待垃圾回收器回收
            x.prev = null;
        }

        if (next == null) {
            // 判断当前对象是最后一个对象
            last = prev;
        } else {
            // 将下一个对象的上一个对象赋值
            next.prev = prev;
            // 将当前对象置为空  等待垃圾回收器回收
            x.next = null;
        }

        // 将当前对象置为空  等待垃圾回收器回收
        x.item = null;
        size--;
        modCount++;
        return element;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (MyLinkedList.Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (MyLinkedList.Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }
}
