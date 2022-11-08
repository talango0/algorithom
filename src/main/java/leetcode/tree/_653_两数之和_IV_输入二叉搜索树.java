package leetcode.tree;

import java.util.ArrayList;
//给定一个二叉搜索树 root 和一个目标结果 k，如果二叉搜索树中存在两个元素且它们的和等于给定的目标结果，则返回 true。
//
//
//
// 示例 1：
//
//
//输入: root = [5,3,6,2,4,null,7], k = 9
//输出: true
//
//
// 示例 2：
//
//
//输入: root = [5,3,6,2,4,null,7], k = 28
//输出: false
//
//
//
//
// 提示:
//
//
// 二叉树的节点个数的范围是 [1, 10⁴].
// -10⁴ <= Node.val <= 10⁴
// 题目数据保证，输入的 root 是一棵 有效 的二叉搜索树
// -10⁵ <= k <= 10⁵
//
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉搜索树 哈希表 双指针 二叉树 👍 439 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * @author mayanwei
 * @date 2022-11-01.
 */
public class _653_两数之和_IV_输入二叉搜索树{
    class Solution{
        /**
         * 思路：
         * BST 中序遍历有序这个性质外加双指针
         */
        public boolean findTarget(TreeNode root, int k) {
            // 转化为有序数组
            traverse(root);
            int i = 0, j = inOrders.size() - 1;
            while (i < j) {
                int sum = inOrders.get(i) + inOrders.get(j);
                if (sum < k) {
                    // sum 调大一点
                    i++;
                }
                else if (sum > k) {
                    // sum 调小一点
                    j--;
                }
                else {
                    return true;
                }
            }
            return false;
        }

        ArrayList<Integer> inOrders = new ArrayList<>();

        // 返回中序遍历结果
        void traverse(TreeNode root) {
            ArrayList<Integer> res = new ArrayList<>();
            if (root == null) {
                return;
            }
            traverse(root.left);
            inOrders.add(root.val);
            traverse(root.right);
        }
    }
}
