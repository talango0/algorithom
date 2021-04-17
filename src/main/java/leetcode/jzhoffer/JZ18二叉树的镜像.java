package leetcode.jzhoffer;


import common.TreeNode;

/**
 * 题目描述
 * 操作给定的二叉树，将其变换为源二叉树的镜像。
 * 输入描述:
 * 二叉树的镜像定义：源二叉树
 *     	    8
 *     	   /  \
 *     	  6   10
 *     	 / \  / \
 *     	5  7 9 11
 *     	镜像二叉树
 *     	    8
 *     	   /  \
 *     	  10   6
 *     	 / \  / \
 *     	11 9 7  5
 */
public class JZ18二叉树的镜像 {

    public class Solution {
        public void Mirror(TreeNode root) {
            if(root == null){
                return;
            }
            TreeNode left = root.left;
            TreeNode right = root.right;
            root.right = left;
            root.left = right;
            if(right!= null){
                Mirror(right);
            }
            if(left!=null){
                Mirror(left);
            }
        }
    }

}
