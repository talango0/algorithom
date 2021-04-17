package leetcode.tree;



/**
 * 红黑树是众多搜索树中的一种，能能够保证最坏的情况下基本动态集合操作的时间复杂度为O(lgn)
 * @author mayanwei
 */
public class RedBlankTree {
/*
    红黑树保证每条路径不超过其他路径长度的2倍，因此是近似于平衡的。
    红黑树的性质
    1. 每个结点或是红色的，或是黑色的。
    2. 根结点是黑色的
    3. 每个叶结点是（NIL）是黑色的。
    4. 如果一个结点是红色的，那么它的两个子结点必定为黑色的。
    5. 对于每个结点，从该结点到其所有后代的结点的简单路径上，均包含相同数目的黑色结点。

    黑高（blank-height）,从某个结点x出发（不含该结点）到达一个叶结点的任意一条简单路径上的黑色结点的个数成为该结点的黑高，记为 bh(x)。根据性质
    5，黑高的定义是明确的，因为从该结点出发的所有下降到其叶结点的简单路径的黑结点的个数是相同的。于是定义一颗红黑树的高为其根结点的黑高。


    定理1. 一颗有n个内部结点的红黑树的高度至多为 2lg(n+1)

*/

    enum Color{
        BLANK,
        RED;
    }

    class TreeNode{
        TreeNode left;
        TreeNode right;
        TreeNode key;
        TreeNode p;
        Color color;
        public TreeNode(TreeNode key) {
            this.key = key;
        }
    }

    public static void main(String[] args) {

    }

    public void reOrderArray(int [] array) {
    }
}
