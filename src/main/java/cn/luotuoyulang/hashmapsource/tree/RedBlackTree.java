package cn.luotuoyulang.hashmapsource.tree;

/**
 * 红黑树
 */
public class RedBlackTree {

    /**
     * 记录当前根节点
     */
    private Node root;

    /**
     * 节点
     */
    class Node{

        /**
         * 节点内容
         */
        private int value;

        /**
         * 左节点
         */
        private Node left;

        /**
         * 右节点
         */
        private Node right;

        /**
         * 节点颜色
         */
        private NodeColor color;

        /**
         * 记录当前节点父亲 没有父亲的情况下能够根节点
         */
        private Node parent;

        /**
         * 全参构造方法
         * @param value
         * @param left
         * @param right
         * @param color
         * @param parent
         */
        public Node(int value, Node left, Node right, NodeColor color, Node parent) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.color = color;
            this.parent = parent;
        }

        /**
         * 无参构造方法
         */
        public Node() {
        }
    }

    /**
     * 添加节点
     * @param value
     */
    public void  insert(int value){
        if(root == null){
            root = new Node(value,null,null,NodeColor.black,null);
        }else{
            // 已经存在根节点，二叉搜索树
            Node node = inserValue(value);
            // 当前节点判断成功时候 开始对我们的这个节点实现变色 或者 旋转
        }
    }

    /**
     * 修复红黑树
     */
    public void requiredTree(Node newNode){
        if(newNode.parent.color.equals(NodeColor.red)){
            // 修复颜色
        }
    }

    public Node inserValue(int value){
        return getPosition(root,value);
    }

    public Node getPosition(Node node, int value) {
        Node sourceNode = null;
        if (value > node.value) {
            // 右子树
            if (node.right == null) {
                sourceNode = new Node(value, null, null, NodeColor.red, node);
                node.right = sourceNode;
            }else{
                getPosition(node.right,value);
            }
        } else {
            // 左子树
            if(node.left == null){
                sourceNode = new Node(value, null, null, NodeColor.red, node);
                node.left = sourceNode;
            }else{
                getPosition(node.left,value);
            }
        }
        return sourceNode;
    }

    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(1);
        redBlackTree.insert(2);
        redBlackTree.insert(3);
    }
}
