package leetcode.jzhoffer;

import leetcode.tree.TreeNode;
import leetcode.tree._124_二叉树中最大路径和;

/**
 * @author mayanwei
 * @date 2022-10-09.
 * @see _124_二叉树中最大路径和
 */
public class 剑指_Offer_II_051_节点之和最大的路径{
    class Solution {
        private int max = Integer.MIN_VALUE;
        public int maxPathSum(TreeNode root) {
            onSiderMax(root);
            return max;
        }
        public int onSiderMax(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int l = Math.max(0, onSiderMax(root.left));
            int r = Math.max(0, onSiderMax(root.right));
            max = Math.max(l + r + root.val, max);
            return Math.max(l, r) + root.val;
        }
    }
}
