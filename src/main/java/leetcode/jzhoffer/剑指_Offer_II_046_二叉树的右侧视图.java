package leetcode.jzhoffer;

import leetcode.tree.TreeNode;
import leetcode.tree._199_二叉树的右视图;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-10-30.
 * @see _199_二叉树的右视图
 */
public class 剑指_Offer_II_046_二叉树的右侧视图{
    class Solution {
        // DFS
        List<Integer> res = new ArrayList<>();
        // 记录递归的层数
        int depth = 0;

        public List<Integer> rightSideView(TreeNode root) {
            traverse(root);
            return res;
        }

        // 二叉树遍历函数
        void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            // 前序遍历位置
            depth++;
            if (res.size() < depth) {
                // 这一层还没有记录值
                // 说明root 就是右侧视图的第一个节点
                res.add(root.val);
            }
            // 注意，这里反过来，先遍历右子树再遍历左子树
            // 这样首先遍历的一定是右侧节点
            traverse(root.right);
            traverse(root.left);
            // 后序遍历位置
            depth--;
        }

    }
}
