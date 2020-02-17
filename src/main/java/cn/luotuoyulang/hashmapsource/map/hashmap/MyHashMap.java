//package cn.luotuoyulang.hashmapsource.map.hashmap;
//
//import java.io.Serializable;
//import java.util.*;
//import java.util.function.BiConsumer;
//import java.util.function.BiFunction;
//import java.util.function.Function;
//
//public class MyHashMap<K,V>  extends AbstractMap<K,V>
//    implements MyMap<K,V>, Cloneable, Serializable {
//
//    /**
//     * 阈值
//     */
//    final float loadFactor;
//
//    transient int modCount;
//
//    transient int size;
//
//    /**
//     * 加载因子 0.75
//     */
//    static final float DEFAULT_LOAD_FACTOR = 0.75f;
//
//    /**
//     * 默认数组容量
//     */
//    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
//
//    transient Node<K,V>[] table;
//
//    static final int MAXIMUM_CAPACITY = 1 << 30;
//
//    /**
//     * 转红黑树的阈值
//     */
//    static final int TREEIFY_THRESHOLD = 8;
//
//    /**
//     * 加载因子大小
//     */
//    int threshold;
//
//    static final int MIN_TREEIFY_CAPACITY = 64;
//
//    public MyHashMap() {
//        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
//    }
//
//    /**
//     * 计算哈希值
//     * 如果key 为 null 则存储在 数组的第一个位置
//     * @param key
//     * @return
//     */
//    static final int hash(Object key) {
//        int h;
//        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
//    }
//
//    /**
//     * 对数组进行扩容
//     * @return
//     */
//    final MyHashMap.Node<K,V>[] resize() {
//        MyHashMap.Node<K,V>[] oldTab = table;
//        int oldCap = (oldTab == null) ? 0 : oldTab.length;
//        int oldThr = threshold;
//        int newCap, newThr = 0;
//        if (oldCap > 0) {
//            // 最大容量做限制
//            if (oldCap >= MAXIMUM_CAPACITY) {
//                threshold = Integer.MAX_VALUE;
//                return oldTab;
//            }
//            // 扩容一倍
//            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
//                    oldCap >= DEFAULT_INITIAL_CAPACITY)
//                // 新的加载因在也扩大一倍
//                newThr = oldThr << 1; // double threshold
//        }
//        else if (oldThr > 0) // initial capacity was placed in threshold
//            newCap = oldThr;
//        else {               // zero initial threshold signifies using defaults
//            newCap = DEFAULT_INITIAL_CAPACITY;
//            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
//        }
//        if (newThr == 0) {
//            float ft = (float)newCap * loadFactor;
//            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
//                    (int)ft : Integer.MAX_VALUE);
//        }
//        threshold = newThr;
//        @SuppressWarnings({"rawtypes","unchecked"})
//        MyHashMap.Node<K,V>[] newTab = (MyHashMap.Node<K,V>[])new MyHashMap.Node[newCap];
//        table = newTab;
//        if (oldTab != null) {
//            for (int j = 0; j < oldCap; ++j) {
//                MyHashMap.Node<K,V> e;
//                if ((e = oldTab[j]) != null) {
//                    oldTab[j] = null;
//                    if (e.next == null)
//                        newTab[e.hash & (newCap - 1)] = e;
//                    else if (e instanceof TreeNode)
//                        ((MyHashMap.TreeNode<K,V>)e).split(this, newTab, j, oldCap);
//                    else { // preserve order
//                        MyHashMap.Node<K,V> loHead = null, loTail = null;
//                        MyHashMap.Node<K,V> hiHead = null, hiTail = null;
//                        MyHashMap.Node<K,V> next;
//                        do {
//                            next = e.next;
//                            if ((e.hash & oldCap) == 0) {
//                                if (loTail == null)
//                                    loHead = e;
//                                else
//                                    loTail.next = e;
//                                loTail = e;
//                            }
//                            else {
//                                if (hiTail == null)
//                                    hiHead = e;
//                                else
//                                    hiTail.next = e;
//                                hiTail = e;
//                            }
//                        } while ((e = next) != null);
//                        if (loTail != null) {
//                            loTail.next = null;
//                            newTab[j] = loHead;
//                        }
//                        if (hiTail != null) {
//                            hiTail.next = null;
//                            newTab[j + oldCap] = hiHead;
//                        }
//                    }
//                }
//            }
//        }
//        return newTab;
//    }
//
//    static final class TreeNode<K,V> extends java.util.LinkedHashMap.Entry<K,V> {
//        MyHashMap.TreeNode<K,V> parent;  // red-black tree links
//        MyHashMap.TreeNode<K,V> left;
//        MyHashMap.TreeNode<K,V> right;
//        MyHashMap.TreeNode<K,V> prev;    // needed to unlink next upon deletion
//        boolean red;
//        TreeNode(int hash, K key, V val, MyHashMap.Node<K,V> next) {
//            super(hash, key, val, next);
//        }
//
//        /**
//         * Returns root of tree containing this node.
//         */
//        final MyHashMap.TreeNode<K,V> root() {
//            for (MyHashMap.TreeNode<K,V> r = this, p;;) {
//                if ((p = r.parent) == null)
//                    return r;
//                r = p;
//            }
//        }
//
//        /**
//         * Ensures that the given root is the first node of its bin.
//         */
//        static <K,V> void moveRootToFront(MyHashMap.Node<K,V>[] tab, MyHashMap.TreeNode<K,V> root) {
//            int n;
//            if (root != null && tab != null && (n = tab.length) > 0) {
//                int index = (n - 1) & root.hash;
//                MyHashMap.TreeNode<K,V> first = (MyHashMap.TreeNode<K,V>)tab[index];
//                if (root != first) {
//                    MyHashMap.Node<K,V> rn;
//                    tab[index] = root;
//                    MyHashMap.TreeNode<K,V> rp = root.prev;
//                    if ((rn = root.next) != null)
//                        ((MyHashMap.TreeNode<K,V>)rn).prev = rp;
//                    if (rp != null)
//                        rp.next = rn;
//                    if (first != null)
//                        first.prev = root;
//                    root.next = first;
//                    root.prev = null;
//                }
//                assert checkInvariants(root);
//            }
//        }
//
//        /**
//         * Finds the node starting at root p with the given hash and key.
//         * The kc argument caches comparableClassFor(key) upon first use
//         * comparing keys.
//         */
//        final MyHashMap.TreeNode<K,V> find(int h, Object k, Class<?> kc) {
//            MyHashMap.TreeNode<K,V> p = this;
//            do {
//                int ph, dir; K pk;
//                MyHashMap.TreeNode<K,V> pl = p.left, pr = p.right, q;
//                if ((ph = p.hash) > h)
//                    p = pl;
//                else if (ph < h)
//                    p = pr;
//                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
//                    return p;
//                else if (pl == null)
//                    p = pr;
//                else if (pr == null)
//                    p = pl;
//                else if ((kc != null ||
//                        (kc = comparableClassFor(k)) != null) &&
//                        (dir = compareComparables(kc, k, pk)) != 0)
//                    p = (dir < 0) ? pl : pr;
//                else if ((q = pr.find(h, k, kc)) != null)
//                    return q;
//                else
//                    p = pl;
//            } while (p != null);
//            return null;
//        }
//
//        /**
//         * Calls find for root node.
//         */
//        final MyHashMap.TreeNode<K,V> getTreeNode(int h, Object k) {
//            return ((parent != null) ? root() : this).find(h, k, null);
//        }
//
//        /**
//         * Tie-breaking utility for ordering insertions when equal
//         * hashCodes and non-comparable. We don't require a total
//         * order, just a consistent insertion rule to maintain
//         * equivalence across rebalancings. Tie-breaking further than
//         * necessary simplifies testing a bit.
//         */
//        static int tieBreakOrder(Object a, Object b) {
//            int d;
//            if (a == null || b == null ||
//                    (d = a.getClass().getName().
//                            compareTo(b.getClass().getName())) == 0)
//                d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
//                        -1 : 1);
//            return d;
//        }
//
//        /**
//         * Forms tree of the nodes linked from this node.
//         * @return root of tree
//         */
//        final void treeify(MyHashMap.Node<K,V>[] tab) {
//            MyHashMap.TreeNode<K,V> root = null;
//            for (MyHashMap.TreeNode<K,V> x = this, next; x != null; x = next) {
//                next = (MyHashMap.TreeNode<K,V>)x.next;
//                x.left = x.right = null;
//                if (root == null) {
//                    x.parent = null;
//                    x.red = false;
//                    root = x;
//                }
//                else {
//                    K k = x.key;
//                    int h = x.hash;
//                    Class<?> kc = null;
//                    for (MyHashMap.TreeNode<K,V> p = root;;) {
//                        int dir, ph;
//                        K pk = p.key;
//                        if ((ph = p.hash) > h)
//                            dir = -1;
//                        else if (ph < h)
//                            dir = 1;
//                        else if ((kc == null &&
//                                (kc = comparableClassFor(k)) == null) ||
//                                (dir = compareComparables(kc, k, pk)) == 0)
//                            dir = tieBreakOrder(k, pk);
//
//                        MyHashMap.TreeNode<K,V> xp = p;
//                        if ((p = (dir <= 0) ? p.left : p.right) == null) {
//                            x.parent = xp;
//                            if (dir <= 0)
//                                xp.left = x;
//                            else
//                                xp.right = x;
//                            root = balanceInsertion(root, x);
//                            break;
//                        }
//                    }
//                }
//            }
//            moveRootToFront(tab, root);
//        }
//
//        /**
//         * Returns a list of non-TreeNodes replacing those linked from
//         * this node.
//         */
//        final MyHashMap.Node<K,V> untreeify(HashMap<K,V> map) {
//            MyHashMap.Node<K,V> hd = null, tl = null;
//            for (MyHashMap.Node<K,V> q = this; q != null; q = q.next) {
//                MyHashMap.Node<K,V> p = map.replacementNode(q, null);
//                if (tl == null)
//                    hd = p;
//                else
//                    tl.next = p;
//                tl = p;
//            }
//            return hd;
//        }
//
//        /**
//         * Tree version of putVal.
//         */
//        final MyHashMap.TreeNode<K,V> putTreeVal(HashMap<K,V> map, MyHashMap.Node<K,V>[] tab,
//                                               int h, K k, V v) {
//            Class<?> kc = null;
//            boolean searched = false;
//            MyHashMap.TreeNode<K,V> root = (parent != null) ? root() : this;
//            for (MyHashMap.TreeNode<K,V> p = root;;) {
//                int dir, ph; K pk;
//                if ((ph = p.hash) > h)
//                    dir = -1;
//                else if (ph < h)
//                    dir = 1;
//                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
//                    return p;
//                else if ((kc == null &&
//                        (kc = comparableClassFor(k)) == null) ||
//                        (dir = compareComparables(kc, k, pk)) == 0) {
//                    if (!searched) {
//                        HashMap.TreeNode<K,V> q, ch;
//                        searched = true;
//                        if (((ch = p.left) != null &&
//                                (q = ch.find(h, k, kc)) != null) ||
//                                ((ch = p.right) != null &&
//                                        (q = ch.find(h, k, kc)) != null))
//                            return q;
//                    }
//                    dir = tieBreakOrder(k, pk);
//                }
//
//                MyHashMap.TreeNode<K,V> xp = p;
//                if ((p = (dir <= 0) ? p.left : p.right) == null) {
//                    HashMap.Node<K,V> xpn = xp.next;
//                    HashMap.TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn);
//                    if (dir <= 0)
//                        xp.left = x;
//                    else
//                        xp.right = x;
//                    xp.next = x;
//                    x.parent = x.prev = xp;
//                    if (xpn != null)
//                        ((HashMap.TreeNode<K,V>)xpn).prev = x;
//                    moveRootToFront(tab, balanceInsertion(root, x));
//                    return null;
//                }
//            }
//        }
//
//        /**
//         * Removes the given node, that must be present before this call.
//         * This is messier than typical red-black deletion code because we
//         * cannot swap the contents of an interior node with a leaf
//         * successor that is pinned by "next" pointers that are accessible
//         * independently during traversal. So instead we swap the tree
//         * linkages. If the current tree appears to have too few nodes,
//         * the bin is converted back to a plain bin. (The test triggers
//         * somewhere between 2 and 6 nodes, depending on tree structure).
//         */
//        final void removeTreeNode(MyHashMap<K,V> map, MyHashMap.Node<K,V>[] tab,
//                                  boolean movable) {
//            int n;
//            if (tab == null || (n = tab.length) == 0)
//                return;
//            int index = (n - 1) & hash;
//            MyHashMap.TreeNode<K,V> first = (MyHashMap.TreeNode<K,V>)tab[index], root = first, rl;
//            MyHashMap.TreeNode<K,V> succ = (MyHashMap.TreeNode<K,V>)next, pred = prev;
//            if (pred == null)
//                tab[index] = first = succ;
//            else
//                pred.next = succ;
//            if (succ != null)
//                succ.prev = pred;
//            if (first == null)
//                return;
//            if (root.parent != null)
//                root = root.root();
//            if (root == null || root.right == null ||
//                    (rl = root.left) == null || rl.left == null) {
//                tab[index] = first.untreeify(map);  // too small
//                return;
//            }
//            MyHashMap.TreeNode<K,V> p = this, pl = left, pr = right, replacement;
//            if (pl != null && pr != null) {
//                MyHashMap.TreeNode<K,V> s = pr, sl;
//                while ((sl = s.left) != null) // find successor
//                    s = sl;
//                boolean c = s.red; s.red = p.red; p.red = c; // swap colors
//                MyHashMap.TreeNode<K,V> sr = s.right;
//                MyHashMap.TreeNode<K,V> pp = p.parent;
//                if (s == pr) { // p was s's direct parent
//                    p.parent = s;
//                    s.right = p;
//                }
//                else {
//                    MyHashMap.TreeNode<K,V> sp = s.parent;
//                    if ((p.parent = sp) != null) {
//                        if (s == sp.left)
//                            sp.left = p;
//                        else
//                            sp.right = p;
//                    }
//                    if ((s.right = pr) != null)
//                        pr.parent = s;
//                }
//                p.left = null;
//                if ((p.right = sr) != null)
//                    sr.parent = p;
//                if ((s.left = pl) != null)
//                    pl.parent = s;
//                if ((s.parent = pp) == null)
//                    root = s;
//                else if (p == pp.left)
//                    pp.left = s;
//                else
//                    pp.right = s;
//                if (sr != null)
//                    replacement = sr;
//                else
//                    replacement = p;
//            }
//            else if (pl != null)
//                replacement = pl;
//            else if (pr != null)
//                replacement = pr;
//            else
//                replacement = p;
//            if (replacement != p) {
//                MyHashMap.TreeNode<K,V> pp = replacement.parent = p.parent;
//                if (pp == null)
//                    root = replacement;
//                else if (p == pp.left)
//                    pp.left = replacement;
//                else
//                    pp.right = replacement;
//                p.left = p.right = p.parent = null;
//            }
//
//            MyHashMap.TreeNode<K,V> r = p.red ? root : balanceDeletion(root, replacement);
//
//            if (replacement == p) {  // detach
//                MyHashMap.TreeNode<K,V> pp = p.parent;
//                p.parent = null;
//                if (pp != null) {
//                    if (p == pp.left)
//                        pp.left = null;
//                    else if (p == pp.right)
//                        pp.right = null;
//                }
//            }
//            if (movable)
//                moveRootToFront(tab, r);
//        }
//
//        /**
//         * Splits nodes in a tree bin into lower and upper tree bins,
//         * or untreeifies if now too small. Called only from resize;
//         * see above discussion about split bits and indices.
//         *
//         * @param map the map
//         * @param tab the table for recording bin heads
//         * @param index the index of the table being split
//         * @param bit the bit of hash to split on
//         */
//        final void split(MyHashMap<K,V> map, MyHashMap.Node<K,V>[] tab, int index, int bit) {
//            MyHashMap.TreeNode<K,V> b = this;
//            // Relink into lo and hi lists, preserving order
//            MyHashMap.TreeNode<K,V> loHead = null, loTail = null;
//            MyHashMap.TreeNode<K,V> hiHead = null, hiTail = null;
//            int lc = 0, hc = 0;
//            for (MyHashMap.TreeNode<K,V> e = b, next; e != null; e = next) {
//                next = (MyHashMap.TreeNode<K,V>)e.next;
//                e.next = null;
//                if ((e.hash & bit) == 0) {
//                    if ((e.prev = loTail) == null)
//                        loHead = e;
//                    else
//                        loTail.next = e;
//                    loTail = e;
//                    ++lc;
//                }
//                else {
//                    if ((e.prev = hiTail) == null)
//                        hiHead = e;
//                    else
//                        hiTail.next = e;
//                    hiTail = e;
//                    ++hc;
//                }
//            }
//
//            if (loHead != null) {
//                if (lc <= UNTREEIFY_THRESHOLD)
//                    tab[index] = loHead.untreeify(map);
//                else {
//                    tab[index] = loHead;
//                    if (hiHead != null) // (else is already treeified)
//                        loHead.treeify(tab);
//                }
//            }
//            if (hiHead != null) {
//                if (hc <= UNTREEIFY_THRESHOLD)
//                    tab[index + bit] = hiHead.untreeify(map);
//                else {
//                    tab[index + bit] = hiHead;
//                    if (loHead != null)
//                        hiHead.treeify(tab);
//                }
//            }
//        }
//
//        /* ------------------------------------------------------------ */
//        // Red-black tree methods, all adapted from CLR
//
//        static <K,V> MyHashMap.TreeNode<K,V> rotateLeft(MyHashMap.TreeNode<K,V> root,
//                                                        MyHashMap.TreeNode<K,V> p) {
//            MyHashMap.TreeNode<K,V> r, pp, rl;
//            if (p != null && (r = p.right) != null) {
//                if ((rl = p.right = r.left) != null)
//                    rl.parent = p;
//                if ((pp = r.parent = p.parent) == null)
//                    (root = r).red = false;
//                else if (pp.left == p)
//                    pp.left = r;
//                else
//                    pp.right = r;
//                r.left = p;
//                p.parent = r;
//            }
//            return root;
//        }
//
//        static <K,V> MyHashMap.TreeNode<K,V> rotateRight(MyHashMap.TreeNode<K,V> root,
//                                                         MyHashMap.TreeNode<K,V> p) {
//            MyHashMap.TreeNode<K,V> l, pp, lr;
//            if (p != null && (l = p.left) != null) {
//                if ((lr = p.left = l.right) != null)
//                    lr.parent = p;
//                if ((pp = l.parent = p.parent) == null)
//                    (root = l).red = false;
//                else if (pp.right == p)
//                    pp.right = l;
//                else
//                    pp.left = l;
//                l.right = p;
//                p.parent = l;
//            }
//            return root;
//        }
//
//        static <K,V> MyHashMap.TreeNode<K,V> balanceInsertion(MyHashMap.TreeNode<K,V> root,
//                                                              MyHashMap.TreeNode<K,V> x) {
//            x.red = true;
//            for (MyHashMap.TreeNode<K,V> xp, xpp, xppl, xppr;;) {
//                if ((xp = x.parent) == null) {
//                    x.red = false;
//                    return x;
//                }
//                else if (!xp.red || (xpp = xp.parent) == null)
//                    return root;
//                if (xp == (xppl = xpp.left)) {
//                    if ((xppr = xpp.right) != null && xppr.red) {
//                        xppr.red = false;
//                        xp.red = false;
//                        xpp.red = true;
//                        x = xpp;
//                    }
//                    else {
//                        if (x == xp.right) {
//                            root = rotateLeft(root, x = xp);
//                            xpp = (xp = x.parent) == null ? null : xp.parent;
//                        }
//                        if (xp != null) {
//                            xp.red = false;
//                            if (xpp != null) {
//                                xpp.red = true;
//                                root = rotateRight(root, xpp);
//                            }
//                        }
//                    }
//                }
//                else {
//                    if (xppl != null && xppl.red) {
//                        xppl.red = false;
//                        xp.red = false;
//                        xpp.red = true;
//                        x = xpp;
//                    }
//                    else {
//                        if (x == xp.left) {
//                            root = rotateRight(root, x = xp);
//                            xpp = (xp = x.parent) == null ? null : xp.parent;
//                        }
//                        if (xp != null) {
//                            xp.red = false;
//                            if (xpp != null) {
//                                xpp.red = true;
//                                root = rotateLeft(root, xpp);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        static <K,V> MyHashMap.TreeNode<K,V> balanceDeletion(MyHashMap.TreeNode<K,V> root,
//                                                             MyHashMap.TreeNode<K,V> x) {
//            for (MyHashMap.TreeNode<K,V> xp, xpl, xpr;;)  {
//                if (x == null || x == root)
//                    return root;
//                else if ((xp = x.parent) == null) {
//                    x.red = false;
//                    return x;
//                }
//                else if (x.red) {
//                    x.red = false;
//                    return root;
//                }
//                else if ((xpl = xp.left) == x) {
//                    if ((xpr = xp.right) != null && xpr.red) {
//                        xpr.red = false;
//                        xp.red = true;
//                        root = rotateLeft(root, xp);
//                        xpr = (xp = x.parent) == null ? null : xp.right;
//                    }
//                    if (xpr == null)
//                        x = xp;
//                    else {
//                        MyHashMap.TreeNode<K,V> sl = xpr.left, sr = xpr.right;
//                        if ((sr == null || !sr.red) &&
//                                (sl == null || !sl.red)) {
//                            xpr.red = true;
//                            x = xp;
//                        }
//                        else {
//                            if (sr == null || !sr.red) {
//                                if (sl != null)
//                                    sl.red = false;
//                                xpr.red = true;
//                                root = rotateRight(root, xpr);
//                                xpr = (xp = x.parent) == null ?
//                                        null : xp.right;
//                            }
//                            if (xpr != null) {
//                                xpr.red = (xp == null) ? false : xp.red;
//                                if ((sr = xpr.right) != null)
//                                    sr.red = false;
//                            }
//                            if (xp != null) {
//                                xp.red = false;
//                                root = rotateLeft(root, xp);
//                            }
//                            x = root;
//                        }
//                    }
//                }
//                else { // symmetric
//                    if (xpl != null && xpl.red) {
//                        xpl.red = false;
//                        xp.red = true;
//                        root = rotateRight(root, xp);
//                        xpl = (xp = x.parent) == null ? null : xp.left;
//                    }
//                    if (xpl == null)
//                        x = xp;
//                    else {
//                        MyHashMap.TreeNode<K,V> sl = xpl.left, sr = xpl.right;
//                        if ((sl == null || !sl.red) &&
//                                (sr == null || !sr.red)) {
//                            xpl.red = true;
//                            x = xp;
//                        }
//                        else {
//                            if (sl == null || !sl.red) {
//                                if (sr != null)
//                                    sr.red = false;
//                                xpl.red = true;
//                                root = rotateLeft(root, xpl);
//                                xpl = (xp = x.parent) == null ?
//                                        null : xp.left;
//                            }
//                            if (xpl != null) {
//                                xpl.red = (xp == null) ? false : xp.red;
//                                if ((sl = xpl.left) != null)
//                                    sl.red = false;
//                            }
//                            if (xp != null) {
//                                xp.red = false;
//                                root = rotateRight(root, xp);
//                            }
//                            x = root;
//                        }
//                    }
//                }
//            }
//        }
//
//        /**
//         * Recursive invariant check
//         */
//        static <K,V> boolean checkInvariants(MyHashMap.TreeNode<K,V> t) {
//            MyHashMap.TreeNode<K,V> tp = t.parent, tl = t.left, tr = t.right,
//                    tb = t.prev, tn = (MyHashMap.TreeNode<K,V>)t.next;
//            if (tb != null && tb.next != t)
//                return false;
//            if (tn != null && tn.prev != t)
//                return false;
//            if (tp != null && t != tp.left && t != tp.right)
//                return false;
//            if (tl != null && (tl.parent != t || tl.hash > t.hash))
//                return false;
//            if (tr != null && (tr.parent != t || tr.hash < t.hash))
//                return false;
//            if (t.red && tl != null && tl.red && tr != null && tr.red)
//                return false;
//            if (tl != null && !checkInvariants(tl))
//                return false;
//            if (tr != null && !checkInvariants(tr))
//                return false;
//            return true;
//        }
//    }
//
//    MyHashMap.Node<K,V> newNode(int hash, K key, V value, MyHashMap.Node<K,V> next) {
//        return new MyHashMap.Node<>(hash, key, value, next);
//    }
//
//    void afterNodeAccess(Node<K,V> p) { }
//
//    void afterNodeInsertion(boolean evict) { }
//
//    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
//                   boolean evict) {
//        /**
//         * 数组中存放链表的所有链表
//         */
//        MyHashMap.Node<K,V>[] tab;
//
//        /**
//         * 某个链表中的某个节点
//         */
//        MyHashMap.Node<K,V> p;
//
//        /**
//         * n 当前数组的长度
//         * i 当前所在的索引位置
//         */
//        int n, i;
//
//        /**
//         * 如果当前我们的hashMap 中 table中的数组为空的情况下 就开始扩容
//         */
//        if ((tab = table) == null || (n = tab.length) == 0)
//            n = (tab = resize()).length;
//        // 如果没有从链表中获取到数组值，就开始准备复制
//        if ((p = tab[i = (n - 1) & hash]) == null)
//            tab[i] = newNode(hash, key, value, null);
//        else {
//            MyHashMap.Node<K,V> e; K k;
//            // 如果hash值相等的情况下且key的内容也相等的情况下 实现对我们的value值覆盖
//            if (p.hash == hash &&
//                    ((k = p.key) == key || (key != null && key.equals(k))))
//                e = p;
//            // 如果节点属于红黑树，则存储则按红黑树存储
////            else if (p instanceof TreeNode)
////                e = ((MyHashMap.TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
//            // binCount 用来判断是否用红黑树存储
//            else {
//                // k 的  hash 值相同，但是 key 的内容不同
//                for (int binCount = 0; ; ++binCount) {
//                    if ((e = p.next) == null) {
//                        // 链表的下一个节点为当前 key 尾插法，意思就是插到尾部
//                        // jdk7 hashMap 插入最新的放入到最前面 头插法
//                        p.next = newNode(hash, key, value, null);
//                        // 如果链表个数大于8
//                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
//                            // 转红黑树进行存放
//                            treeifyBin(tab, hash);
//                        break;
//                    }
//                    // 在遍历的过程中有可能产生key 相同的情况，则开始覆盖 value
//                    if (e.hash == hash &&
//                            ((k = e.key) == key || (key != null && key.equals(k))))
//                        break;
//                    p = e;
//                }
//            }
//            // 进行对 value 值 进行修改
//            if (e != null) { // existing mapping for key
//                V oldValue = e.value;
//                if (!onlyIfAbsent || oldValue == null)
//                    e.value = value;
//                afterNodeAccess(e);
//                return oldValue;
//            }
//        }
//        ++modCount;
//        if (++size > threshold)
//            resize();
//        afterNodeInsertion(evict);
//        return null;
//    }
//
//
//
//
//    MyHashMap.TreeNode<K,V> replacementTreeNode(MyHashMap.Node<K,V> p, MyHashMap.Node<K,V> next) {
//        return new MyHashMap.TreeNode<>(p.hash, p.key, p.value, next);
//    }
//
//    /**
//     * 转红黑树进行存放
//     * @param tab
//     * @param hash
//     */
//    final void treeifyBin(MyHashMap.Node<K,V>[] tab, int hash) {
//        int n, index; MyHashMap.Node<K,V> e;
//        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
//            resize();
//        else if ((e = tab[index = (n - 1) & hash]) != null) {
//            MyHashMap.TreeNode<K,V> hd = null, tl = null;
//            do {
//                MyHashMap.TreeNode<K,V> p = replacementTreeNode(e, null);
//                if (tl == null)
//                    hd = p;
//                else {
//                    p.prev = tl;
//                    tl.next = p;
//                }
//                tl = p;
//            } while ((e = e.next) != null);
//            if ((tab[index] = hd) != null)
//                hd.treeify(tab);
//        }
//    }
//
//    @Override
//    public V put(K key, V value) {
//        return putVal(hash(key), key, value, false, true);
//    }
//
//    @Override
//    public Set<Map.Entry<K, V>> entrySet() {
//        return null;
//    }
//
//    @Override
//    public V getOrDefault(Object key, V defaultValue) {
//        return null;
//    }
//
//    @Override
//    public void forEach(BiConsumer<? super K, ? super V> action) {
//
//    }
//
//    @Override
//    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
//
//    }
//
//    @Override
//    public V putIfAbsent(K key, V value) {
//        return null;
//    }
//
//    @Override
//    public boolean remove(Object key, Object value) {
//        return false;
//    }
//
//    @Override
//    public boolean replace(K key, V oldValue, V newValue) {
//        return false;
//    }
//
//    @Override
//    public V replace(K key, V value) {
//        return null;
//    }
//
//    @Override
//    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
//        return null;
//    }
//
//    @Override
//    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
//        return null;
//    }
//
//    @Override
//    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
//        return null;
//    }
//
//    @Override
//    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
//        return null;
//    }
//
//    @Override
//    public V get(Object key) {
//        return null;
//    }
//
//    /**
//     * hashMap 底层应用了链表结构
//     * @param <K>
//     * @param <V>
//     */
//    static class Node<K,V> implements MyMap.Entry<K,V> {
//        final int hash;
//        final K key;
//        V value;
//        MyHashMap.Node<K,V> next;
//
//        Node(int hash, K key, V value, MyHashMap.Node<K,V> next) {
//            this.hash = hash;
//            this.key = key;
//            this.value = value;
//            this.next = next;
//        }
//
//        public final K getKey()        { return key; }
//        public final V getValue()      { return value; }
//        public final String toString() { return key + "=" + value; }
//
//        public final int hashCode() {
//            return Objects.hashCode(key) ^ Objects.hashCode(value);
//        }
//
//        public final V setValue(V newValue) {
//            V oldValue = value;
//            value = newValue;
//            return oldValue;
//        }
//
//        public final boolean equals(Object o) {
//            if (o == this)
//                return true;
//            if (o instanceof Map.Entry) {
//                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
//                if (Objects.equals(key, e.getKey()) &&
//                        Objects.equals(value, e.getValue()))
//                    return true;
//            }
//            return false;
//        }
//    }
//}
