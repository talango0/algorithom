package leetcode.tree;
//给你二叉搜索树的根节点 root ，该树中的 恰好 两个节点的值被错误地交换。请在不改变其结构的情况下，恢复这棵树 。
//
//
//
// 示例 1：
//
//
//输入：root = [1,3,null,null,2]
//输出：[3,1,null,null,2]
//解释：3 不能是 1 的左孩子，因为 3 > 1 。交换 1 和 3 使二叉搜索树有效。
//
//
// 示例 2：
//
//
//输入：root = [3,1,4,null,null,2]
//输出：[2,1,4,null,null,3]
//解释：2 不能在 3 的右子树中，因为 2 < 3 。交换 2 和 3 使二叉搜索树有效。
//
//
//
// 提示：
//
//
// 树上节点的数目在范围 [2, 1000] 内
// -2³¹ <= Node.val <= 2³¹ - 1
//
//
//
//
// 进阶：使用 O(n) 空间复杂度的解法很容易实现。你能想出一个只使用 O(1) 空间的解决方案吗？
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 👍 737 👎 0

/**
 * @author mayanwei
 * @date 2022-06-12.
 */
public class _99_恢复二叉搜索树{
    class Solution {
        public void recoverTree(TreeNode root) {
            if (root == null) {
                return;
            }
            traverse(root);
            int tmp = first.val;
            first.val = secode.val;
            secode.val = tmp;
        }
        //分别记录这两个被交换的节点
        TreeNode first = null, secode = null;
        TreeNode prev = new TreeNode(Integer.MIN_VALUE);

        public void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            traverse(root.left);
            //中序遍历代码位置，找出那两个点
            if (root.val < prev.val) {
                //root 不符合有序性
                if(first == null){
                    first = prev;
                }
                //第二个节点是root
                secode = root;
            }
            prev = root;
            traverse(root.right);
        }
    }
}
