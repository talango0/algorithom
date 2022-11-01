package leetcode.jzhoffer;

import leetcode.tree.TreeNode;
import leetcode.tree._814_二叉树剪枝;

/**
 * @author mayanwei
 * @date 2022-10-30.
 * @see _814_二叉树剪枝
 */
public class 剑指_Offer_II_047_二叉树剪枝{
    class Solution {
        // 定义：输入一颗二叉树，返回的二叉树叶子节点都是1
        public TreeNode pruneTree(TreeNode root) {
            if (root == null) {
                return null;
            }
            // 二叉树的遍历框架
            root.left = pruneTree(root.left);
            root.right = pruneTree(root.right);
            // 后序遍历的位置，判断自己是否是值为 0 的叶子节点
            if (root.val == 0 && root.left == null && root.right == null) {
                // 返回值会被父节点接收，相当于把自己删掉了
                return null;
            }
            // 如果不是，正常返回
            return root;
        }
    }
}

