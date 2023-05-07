package leetcode.程序员面试金典;
//面试题 04.04. 检查平衡性
//实现一个函数，检查二叉树是否平衡。在这个问题中，平衡树的定义如下：任意一个节点，其两棵子树的高度差不超过 1。
//
//示例 1:
//给定二叉树 [3,9,20,null,null,15,7]
//    3
//   / \
//  9  20
//    /  \
//   15   7
//返回 true 。
//
//示例 2:
//给定二叉树 [1,2,2,3,3,null,null,4,4]
//      1
//     / \
//    2   2
//   / \
//  3   3
// / \
//4   4
//返回 false 。

import leetcode.tree.TreeNode;

/**
 * @author mayanwei
 * @date 2023-05-07.
 */
public class _04_04_检查平衡性{
    class Solution {
        boolean ans;
        public boolean isBalanced(TreeNode root) {
            if(root == null) return true;
            ans = true;
            getHeight(root);
            return ans;
        }

        private int getHeight(TreeNode root){
            if(root == null) return 0;
            int left = getHeight(root.left);
            int right = getHeight(root.right);
            if(Math.abs(left - right) > 1) ans = false;
            return 1 + Math.max(left,right);
        }
    }
}
