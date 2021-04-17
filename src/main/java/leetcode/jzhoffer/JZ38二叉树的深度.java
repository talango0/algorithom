package leetcode.jzhoffer;

import common.TreeNode;

public class JZ38二叉树的深度 {
    /*
    题目描述
    输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。
     */

    public class Solution {
        public int TreeDepth(TreeNode root) {
            if(root == null){
                return 0;
            }else {
                int lh = height(root.left);
                int rh = height(root.right);

                return lh>rh?lh:rh;
            }

        }

        private int height(TreeNode node) {
            if(node == null){
                return 1;
            }
            if(1+height(node.left) > 1+height(node.right)){
                return 1 + height(node.left);
            }else {
                return 1 + height(node.right);
            }
        }
    }
}
