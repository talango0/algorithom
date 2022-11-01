package leetcode.jzhoffer;

import leetcode.tree.TreeNode;
import leetcode.tree._653_两数之和_IV_输入二叉搜索树;

import java.util.ArrayList;

/**
 * @author mayanwei
 * @date 2022-11-01.
 * @see _653_两数之和_IV_输入二叉搜索树
 */
public class 剑指_Offer_II_056_二叉搜索树中两个节点之和{
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
