package leetcode.tree;

import java.util.HashSet;

public class _1676_二叉树的最近公共祖先4{


    /**
     *
     * <pre>
     *  依然给你输入一棵不含重复值的二叉树，但这次不是给你输入p和q两个节点了，
     *  而是给你输入一个包含若干节点的列表nodes（这些节点都存在于二叉树中），让你算这些节点的最近公共祖先。
     * ┌──────────────────────────────────┐
     * │              ┌───┐               │
     * │              │ 3 │               │
     * │              └─┬─┘               │
     * │       ┌────────┴──────────┐      │
     * │       ▼                   ▼      │
     * │    ┏━━━━┓              ┌────┐    │
     * │    ┃ 5  ┃              │ 1  │    │
     * │    ┗━━━━┛              └────┘    │
     * │       │                   │      │
     * │   ┌───┴──┐           ┌────┴───┐  │
     * │┌──▼─┐  ┌─▼─┐       ┌─▼─┐    ┌─▼─┐│
     * ││ 7  │  │ 2 │       │ 6 │    │ 6 ││
     * │└────┘  └───┘       └───┘    └───┘│
     * │          │                       │
     * │      ┌───┴────┐                  │
     * │    ┌─▼─┐    ┌─▼─┐                │
     * │    │ 6 │    │ 6 │                │
     * │    └───┘    └───┘                │
     * └──────────────────────────────────┘
     * nodes = [7,4,6], 那么函数返回 5。
     * 注意：这两道题目明确告诉我们这些节点必定存在于二叉树中，如果没有这个前提条件，就需要改代码了，比如 1644 二叉树最近公共节点 II
     * </pew>
     * @param root
     * @param nodes
     * @return
     */
    TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
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
        if (left != null && right != null) {
            return root;
        }
        return left == null ? right :left;
    }

}
