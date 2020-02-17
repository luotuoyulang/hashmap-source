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
            requiredTree(node);
        }
    }

    /**
     * 修复红黑树
     */
    public void requiredTree(Node newNode){
        if(newNode.parent.color.equals(NodeColor.red)){
            // 实现对我们的红黑树进行变色 或旋转
            Node y = newNode.parent.parent;
            Node f = newNode.parent;
            // 当前节点父亲是红色，且它的祖父亲节点的另一个子节点也是红色（叔叔节点）
            if (y.left != null && y.left.color.equals(NodeColor.red)
                    && y.right != null && y.right.color.equals(NodeColor.red)) {
                // 叔叔节点的颜色改为黑色
                y.left.color = NodeColor.black;
                // 父亲节点的颜色改为黑色
                f.color = NodeColor.black;
                // 爷爷节点不为root 节点情况下改为红色
                if(y != root){
                    y.color = NodeColor.red;
                }
                return;
            }

            // 当父亲节点为红色情况，叔叔节点为黑色或者为空的情况下，且当前的节点是右子树，左旋转以父节点作为左旋
            if((y.left == null || NodeColor.black.equals(y.left.color)) && f.right == newNode){
                leftRotate(y);
                // 将父亲节点改为黑色
                f.color = NodeColor.black;
                // 将祖父节点改为红色
                y.color = NodeColor.red;
            }
        }
    }

    /**
     * 左旋修复
     * 概念：当父亲节点为红色情况，叔叔节点为黑色情况，且当前的节点是右子树，左旋转以父节点作为左旋
     */
    public void leftRotate(Node x){
        // x 的 右子树
        Node y = x.right;
        // x 旋转成功之后 x的又子树 = y 的左子树
        x.right = y.left;
        if(y.left != null){
            y.left.parent = x;
        }
        if(x.parent == null){
            root = y;
        }else{
            // 确定我们的x 值 在左子树
            if(x.parent.left == x){
                x.parent.left = y;
            }else{
                x.parent.right = y;
            }
        }
        // x 父亲为 y
        y.left = x;
        x.parent = y;
    }

    /**
     * 右旋修复
     * 概念：
     */
    public void rightRotate(Node newNode){

    }

    public Node inserValue(int value){
        return getPosition(root,value);
    }

    public Node getPosition(Node node, int value) {
        if (value > node.value) {
            // 右子树
            if (node.right == null) {
                Node sourceNode = new Node(value, null, null, NodeColor.red, node);
                node.right = sourceNode;
                return sourceNode;
            }else{
                return getPosition(node.right,value);
            }
        } else {
            // 左子树
            if(node.left == null){
                Node sourceNode = new Node(value, null, null, NodeColor.red, node);
                node.left = sourceNode;
                return sourceNode;
            }else{
                return getPosition(node.left,value);
            }
        }
    }

    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(1);
        redBlackTree.insert(2);
        redBlackTree.insert(3);
    }
}
