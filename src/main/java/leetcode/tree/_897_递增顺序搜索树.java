package leetcode.tree;
//给你一棵二叉搜索树的
// root ，请你 按中序遍历 将其重新排列为一棵递增顺序搜索树，使树中最左边的节点成为树的根节点，并且每个节点没有左子节点，只有一个右子节点。
//
//
//
// 示例 1：
//
//
//输入：root = [5,3,6,2,4,null,8,1,null,null,null,7,9]
//输出：[1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]
//
//
// 示例 2：
//
//
//输入：root = [5,1,7]
//输出：[1,null,5,null,7]
//
//
//
//
// 提示：
//
//
// 树中节点数的取值范围是 [1, 100]
// 0 <= Node.val <= 1000
//
//
// Related Topics 栈 树 深度优先搜索 二叉搜索树 二叉树 👍 306 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-10-31.
 * @see _114_二叉树展开为链表
 * @see
 */
public class _897_递增顺序搜索树{
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
