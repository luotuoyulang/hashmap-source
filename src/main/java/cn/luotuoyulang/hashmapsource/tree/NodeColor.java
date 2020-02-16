package cn.luotuoyulang.hashmapsource.tree;

/**
 * node 节点颜色
 */
public enum NodeColor {

    red(1,"red"),
    black(2,"black");

    int color;

    String desc;

    NodeColor(int color, String desc) {
        this.color = color;
        this.desc = desc;
    }
}
