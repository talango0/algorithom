package leetcode.tree;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <pre>
 * ┌────────────────────────────────────┐
 * │                ┌───┐               │
 * │   BST          │ 8 │               │
 * │                └───┘               │
 * │                  │                 │
 * │        ┌─────────┴────────┐        │
 * │        │                  │        │
 * │      ┌─▼─┐              ┌─▼─┐      │
 * │      │ 3 │              │ 10│      │
 * │      └───┘              └───┘      │
 * │        │                  │        │
 * │  ┌─────┴────┐             └─────┐  │
 * │┌─▼─┐      ┌─▼─┐               ┌─▼─┐│
 * ││ 1 │      │ 6 │               │ 14││
 * │└───┘      └───┘               └───┘│
 * │             │                   │  │
 * │         ┌───┴────┐         ┌────┘  │
 * │       ┌─▼─┐    ┌─▼─┐     ┌─▼─┐     │
 * │       │ 4 │    │ 7 │     │ 13│     │
 * │       └───┘    └───┘     └───┘     │
 * └────────────────────────────────────┘
 * 1、如果当前节点会对下面的子节点有整体影响，可以通过辅助函数增长参数列表，借助参数传递信息。
 *
 * 2、在二叉树递归框架之上，扩展出一套 BST 代码框架：
 *
 * void BST(TreeNode root, int target) {
 *     if (root.val == target)
 *         // 找到目标，做点什么
 *     if (root.val < target)
 *         BST(root.right, target);
 *     if (root.val > target)
 *         BST(root.left, target);
 * }
 * 3、根据代码框架掌握了 BST 的增删查改操作。
 * </pre>
 * @see leetcode.tree._450_删除二叉搜索树中的节点
 * @see leetcode.tree._700_二叉搜索树中的搜索
 * @see leetcode.tree._701_二叉搜索树中的插入操作
 * @see leetcode.tree._98_验证二叉搜索树
 * @see leetcode.tree._230_二叉搜索树第k小的元素
 * @see leetcode.tree._1038_从二叉搜索树到累加树
 * @see leetcode.jzhoffer.剑指_Offer_II_054_所有大于等于节点的值之和
 * @see leetcode.tree._95_不同的二叉搜索树2
 * @see leetcode.tree._96_不同的二叉搜索树
 *
 * @author mayanwei
 */
public class BinarySearchTree {
//    public static class TreeNode{
//        public Integer key;
//        public  TreeNode right;
//        public TreeNode left;
//        public TreeNode p;
//        public TreeNode(Integer key) {
//            this.key = key;
//        }
//
//        @Override
//        public String toString() {
//            return "TreeNode{" +
//                    "key=" + key +
//                    '}';
//        }
//    }

    /**
     * 中序遍历
     * @param x
     */
    public static void inOrderTreeWalk(TreeNode x){
        if(x != null){
            inOrderTreeWalk(x.left);
            System.out.print(x.key+" ");
            inOrderTreeWalk(x.right);
        }
    }


    /**
     * 查询二叉搜索树(递归版本)
     * @param x
     * @param k
     * @return
     */
    public static TreeNode treeSearch(TreeNode x, Integer k){
        if(x == null|| x.key.equals(k)){
            return x;
        }
        if(k < x.key ){
            return treeSearch(x.left, k);
        }
        return treeSearch(x.right, k);
    }

    /**
     * 查询二叉搜索树（迭代版本）
     * @param x
     * @param k
     * @return
     */
    public static TreeNode treeSearch_v2(TreeNode x, Integer k){
       while (x != null && !k.equals(x.key)){
           if(k < x.key){
               x = x.left;
           }else{
               x = x.right;
           }
       }
       return x;
    }

    /**
     * 最大关键字元素｜最小关键字元素
     * @param args
     */
    /**
     * 最大关键字
     * @param x
     * @return
     */
    public static TreeNode treeMinimum(TreeNode x){
        while (x.left != null){
            x = x.left;
        }
        return x;
    }

    /**
     * 最大关键字
     * @param x
     * @return
     */
    public static TreeNode treeMaxmum(TreeNode x){
        while (x.right != null){
            x = x.right;
        }
        return x;
    }


    /* 前驱和后继 */

    /**
     * 后继，找比它大的里面的最小值
     * @param x
     * @return
     */
    public static TreeNode treeSuccessor(TreeNode x){
        if(x.right!=null){
            return treeMinimum(x.right);
        }
        TreeNode y = x.p;
        while (y != null && x == y.right){
            x = y;
            y = y.p;
        }
        return y;
    }

    /**
     * 前驱，找比它小的里面的最大值
     * @param x
     * @return
     */
    public static TreeNode treePredecessor(TreeNode x){
        if(x.left!=null){
            return treeMaxmum(x.left);
        }
        TreeNode y = x.p;
        while (y != null && x == y.left){
            x = y;
            y = y.p;
        }
        return y;
    }

    /**
     * 定理；
     *     在一颗高度为h的二叉树上，动态集合上的操作 search,minimum,maxmum,successor,Predecessor 可以在O（h）时间内完成
     */

    /**
     * 插入二叉搜索树
     * @param T
     * @param z
     */
    public static void insertSearchTree(TreeNode T, TreeNode z){
        TreeNode y = null;
        TreeNode x = T;
        while (x != null){
            y = x;
            if(z.key < x.key){
                x = x.left;
            }else {
                x = x.right;
            }
        }
        z.p = y;
        if(y == null){
            T = z;
        }else if(z.key < y.key){
            y.left = z;
        }else{
            y.right = z;
        }
        
    }


    /**
     * 用 v替换u
     * 注意： transplant 并没有处理v.left和v.right的更新，这行更新都由transplant调度这运行。
     * @param T
     * @param u
     * @param v
     */
    public void transPlant(TreeNode T, TreeNode u, TreeNode v){
        if(u.p == null){
            T = v;
        }else if( u == u.p.left){
            u.p.left = v;
        }else{
            u.p.right = v;
        }
        if(v != null){
            v.p = u.p;
        }
    }


    /**
     * 删除二叉搜索树中的结点
     * @param T
     * @param z
     */
    public void treeDelete(TreeNode T, TreeNode z){
        if (z.left == null){
            transPlant(T, z, z.right);
        }else if(z.right == null){
            transPlant(T, z, z.left);
        }else {
            //左右孩子都不为空时
            TreeNode y = treeMinimum(z.right);
            if(y.p != z){
                transPlant(T, y, y.right);
                y.right = z.right;
                y.right.p = y;
            }else{
                transPlant(T, z, y);
            }
            y.left = z.left;
            y.left.p = y;

        }
    }

    /**
     * 综合以上，
     * 定理：在一颗高度为h的二叉搜索树上，实现动态集合INSERT和DELETE的运行时间均为O(h)
     */

    public int treeHeight(TreeNode root){
        int h = 0;
        List<Integer> hList = new ArrayList<>();
        treeHeight( root,  h, hList);
        Optional<Integer> max = hList.stream().max(Integer::compareTo);
        if(max.isPresent()){
            h = max.get();
        }
        return h;
    }

    private void treeHeight(TreeNode node, int h, List<Integer>hList) {
        if(node == null) {
            hList.add(h);
            return;
        }else {
            h++;
            treeHeight(node.left, h, hList);
            treeHeight(node.right, h, hList);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(6);
        BinarySearchTree.insertSearchTree(root, new TreeNode(5));
        BinarySearchTree.insertSearchTree(root, new TreeNode(7));
        BinarySearchTree.insertSearchTree(root, new TreeNode(2));
        BinarySearchTree.insertSearchTree(root, new TreeNode(5));
        BinarySearchTree.insertSearchTree(root, new TreeNode(8));

//        root.right = new TreeNode(7);
//        root.left.p = root;
//        root.right.p = root;
//        root.left.left = new TreeNode(2);
//        root.left.right = new TreeNode(5);
//        root.left.left.p = root.left;
//        root.left.right.p = root.left;
//        root.right.right = new TreeNode(8);
//        root.right.right.p = root.right;        root.left = new TreeNode(5);

        BinarySearchTree.inOrderTreeWalk(root);
//        TreeNode searchResult = BinarySearchTree.treeSearch(root, 2);
//        TreeNode searchResult = BinarySearchTree.treeSearch_v2(root, 2);
//        System.out.println(searchResult);
//
//        TreeNode minimum = BinarySearchTree.treeMinimum(root);
//        System.out.println("minimum= "+minimum);
//
//        TreeNode maxmum = BinarySearchTree.treeMaxmum(root);
//        System.out.println("maxmum= "+maxmum);

//        System.out.println("root.left successor"+ BinarySearchTree.treeSuccessor(root.left));
//        System.out.println("root.left successor"+ BinarySearchTree.treePredecessor(root.right));

        BinarySearchTree binarySearchTree = new BinarySearchTree();
//        binarySearchTree.treeDelete(root, root.right);
//        BinarySearchTree.inOrderTreeWalk(root);
        System.out.println("二叉搜索树的高度为："+ binarySearchTree.treeHeight(root));


    }
}

