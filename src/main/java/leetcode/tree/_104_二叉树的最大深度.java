package leetcode.tree;
//给定一个二叉树，找出其最大深度。
//
// 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
//
// 说明: 叶子节点是指没有子节点的节点。
//
// 示例：
//给定二叉树 [3,9,20,null,null,15,7]，
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
//
// 返回它的最大深度 3 。
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 1267 👎 0

/**
 * @author mayanwei
 * @date 2022-06-12.
 */
public class _104_二叉树的最大深度{
    class Solution{
        private Integer maxDepth = 0;

        public int maxDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            traverse(root, 0);
            return maxDepth;
        }

        private void traverse(TreeNode root, int height) {
            if (root == null) {
                maxDepth = Math.max(maxDepth, height);
                return;
            }
            height++;
            traverse(root.left, height);
            traverse(root.right, height);
        }
    }

    class Solution2{
        // 记录最大深度
        int res = 0;
        // 记录遍历到的节点的深度
        int depth = 0;

        // 主函数
        int maxDepth(TreeNode root) {
            traverse(root);
            return res;
        }

        // 二叉树遍历框架
        void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            // 前序位置
            depth++;
            if (root.left == null && root.right == null) {
                // 到达叶子节点，更新最大深度
                res = Math.max(res, depth);
            }
            traverse(root.left);
            traverse(root.right);
            // 后序位置
            depth--;
        }
    }

    /**
     * 分解思维
     */
    class Solution3 {
        // 定义：输入根节点，返回这棵二叉树的最大深度
        int maxDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            // 利用定义，计算左右子树的最大深度
            int leftMax = maxDepth(root.left);
            int rightMax = maxDepth(root.right);
            // 整棵树的最大深度等于左右子树的最大深度取最大值，
            // 然后再加上根节点自己
            int res = Math.max(leftMax, rightMax) + 1;

            return res;
        }

    }


}
