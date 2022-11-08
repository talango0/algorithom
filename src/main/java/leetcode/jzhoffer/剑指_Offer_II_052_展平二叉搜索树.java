package leetcode.jzhoffer;

import leetcode.tree.TreeNode;
import leetcode.tree._897_递增顺序搜索树;

/**
 * @author mayanwei
 * @date 2022-10-31.
 * @see _897_递增顺序搜索树
 */
public class 剑指_Offer_II_052_展平二叉搜索树{
    class Solution{
        /**
         * 二叉树中序遍历的结果是有序的
         */

        // 输入一颗 BST，返回一个有序【链表】
        public TreeNode increasingBST(TreeNode root) {
            if (root == null) {
                return null;
            }
            // 先把左右子树拉平
            TreeNode left = increasingBST(root.left);
            root.left = null;
            TreeNode right = increasingBST(root.right);
            root.right = right;
            // 左子树为空的话，就不用处理
            if (left == null) {
                return root;
            }
            // 左子树非空，需要把根节点和右子树接到左子树末尾
            TreeNode p = left;
            while (p != null && p.right != null) {
                p = p.right;
            }
            p.right = root;
            return left;
        }

    }
}
