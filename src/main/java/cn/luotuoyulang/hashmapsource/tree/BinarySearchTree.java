package cn.luotuoyulang.hashmapsource.tree;

/**
 * 二叉搜索树
 */
public class BinarySearchTree {

    /**
     * 插入元素
     */
    private int data;

    /**
     * 赋值data 元素
     * @param data
     */
    public BinarySearchTree(int data) {
        this.data = data;
    }

    public BinarySearchTree() {
    }

    /**
     * 左边元素
     */
    BinarySearchTree left;

    /**
     * 右边元素
     */
    BinarySearchTree right;

    /**
     * 添加方法
     *
     * @param root 平衡点
     * @param data 插入值
     */
    public void insert(BinarySearchTree root, int data) {
        if (data > root.data) {
            if (root.right != null) {
                insert(root.right, data);
            } else {
                root.right = new BinarySearchTree(data);
            }
        } else {
            if (root.left != null) {
                insert(root.left, data);
            } else {
                root.left = new BinarySearchTree(data);
            }
        }
    }

    /**
     * 查询
     * @param root
     */
    public void search(BinarySearchTree root){
        if(root != null){
            search(root.left);
            System.out.println(root.data);
            search(root.right);
        }
    }

    public static void main(String[] args) {
        int[] data = {3,6,5,2,1,4};
        BinarySearchTree root = new BinarySearchTree(data[0]);
        for (int i = 1; i < data.length; i++) {
            root.insert(root,data[i]);
        }
        root.search(root);
    }
}
