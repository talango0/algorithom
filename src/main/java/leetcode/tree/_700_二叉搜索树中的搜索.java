package leetcode.tree;
//给定二叉搜索树（BST）的根节点 root 和一个整数值 val。
//
// 你需要在 BST 中找到节点值等于 val 的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 null 。
//
//
//
// 示例 1:
//
//
//
//
//输入：root = [4,2,7,1,3], val = 2
//输出：[2,1,3]
//
//
// Example 2:
//
//
//输入：root = [4,2,7,1,3], val = 5
//输出：[]
//
//
//
//
// 提示：
//
//
// 数中节点数在 [1, 5000] 范围内
// 1 <= Node.val <= 10⁷
// root 是二叉搜索树
// 1 <= val <= 10⁷
//
// Related Topics 树 二叉搜索树 二叉树 👍 303 👎 0

public class _700_二叉搜索树中的搜索 {
    class Solution {
        /**迭代版 */
        public TreeNode searchBST0(TreeNode root, int val) {
            TreeNode x = root;
            if (x == null) {
                return null;
            }
            while (x != null) {
                if(x.val == val) {
                    return x;
                }
                else if (x.val < val) {
                    x = x.right;
                }
                else if (x.val > val) {
                    x = x.left;
                }
            }
            return null;
        }
        /**递归版 */
        public TreeNode searchBST(TreeNode root, int val) {
            //base case
            if (root == null) {
                return null;
            }
            //去左子树找
            if (root.val > val) {
                return searchBST(root.left, val);
            }
            //去右子树找
            if (root.val < val) {
                return searchBST(root.right, val);
            }
            return root;
        }
    }
}
