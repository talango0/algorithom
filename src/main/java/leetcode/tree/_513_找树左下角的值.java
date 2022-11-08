package leetcode.tree;
//给定一个二叉树的 根节点 root，请找出该二叉树的 最底层 最左边 节点的值。
//
// 假设二叉树中至少有一个节点。
//
//
//
// 示例 1:
//
//
//
//
//输入: root = [2,1,3]
//输出: 1
//
//
// 示例 2:
//
//
//
//
//输入: [1,2,3,4,null,5,6,null,null,7]
//输出: 7
//
//
//
//
// 提示:
//
//
// 二叉树的节点个数的范围是 [1,10⁴]
//
// -2³¹ <= Node.val <= 2³¹ - 1
//
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 395 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * @author mayanwei
 * @date 2022-10-30.
 * @see
 */
public class _513_找树左下角的值{
    class Solution {
        // 记录二叉树的最大深度
        int maxDepth = 0;
        // 记录 traverse 递归遍历到的深度
        int depth = 0;
        TreeNode res = null;

        public int findBottomLeftValue(TreeNode root) {
            traverse(root);
            return res.val;
        }

        void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            // 前序遍历位置
            depth++;
            if (depth > maxDepth) {
                // 到最大深度时第一次遇到的节点就是左下角的节点
                maxDepth = depth;
                res = root;
            }
            traverse(root.left);
            traverse(root.right);
            // 后序遍历的位置
            depth--;
        }
    }
}
