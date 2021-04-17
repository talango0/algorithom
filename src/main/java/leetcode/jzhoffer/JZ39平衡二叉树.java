package leetcode.jzhoffer;

import common.TreeNode;

public class JZ39平衡二叉树 {

    /**
     题目描述
    输入一棵二叉树，判断该二叉树是否是平衡二叉树。
    在这里，我们只需要考虑其平衡性，不需要考虑其是不是排序二叉树
     */

    public class Solution {
        public boolean IsBalanced_Solution(TreeNode root) {
            return isBalanceTree(root);
        }

        /**
         * 分析，如果一棵树是平衡二叉树，
         * 则    |height(lTree) - height(rTree)| <= 1

         */

        private boolean isBalanceTree(TreeNode root) {
            if(root == null){
                return true;
            }
            return  Math.abs(height(root.left) - height(root.right)) <= 1;
        }

        private int height(TreeNode tree) {
            if(tree == null){
                return 1;
            }else{
                if (1+ height(tree.left)> 1+height(tree.right)){
                    return 1 + height(tree.left);
                }else {
                    return 1 + height(tree.right);
                }
            }
        }
    }
}
