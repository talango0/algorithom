package leetcode.tree;

import java.util.HashSet;

public class _1676_二叉树的最近公共祖先4 {

    TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes){
        //将列表转化为哈希集合，便于判断元素是否存在
        HashSet<Integer> values = new HashSet<>();
        for (TreeNode node : nodes) {
            values.add(node.val);
        }
        return find(root, values);
    }

    //在二叉树中寻找最近的父节点
    private TreeNode find(TreeNode root, HashSet<Integer> values) {
        if (root == null) {
            return null;
        }
        //前序位置
        if (values.contains(root.val)) {
            return root;
        }
        TreeNode left = find(root.left, values);
        TreeNode right = find(root.right, values);
        //后序位置，已经知道左右子树是否存在目标值
        if (left != null && right != null){
            return root;
        }
        return left == null ? right:left;
    }

}
