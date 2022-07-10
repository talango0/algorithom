package leetcode.tree;

/**
 * @see _538_把二叉搜索树转化为累加树
 */
public class _1038_从二叉搜索树到更大和树 {
    class Solution {
        //记录累加和
        int sum = 0;
        public TreeNode bstToGst(TreeNode root) {
            traverse(root);
            return root;

        }
        private void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            traverse(root.right);
            sum += root.val;
            root.val = sum;
            traverse(root.left);
        }
    }
}
