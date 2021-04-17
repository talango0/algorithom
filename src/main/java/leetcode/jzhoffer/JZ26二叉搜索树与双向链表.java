package leetcode.jzhoffer;

import leetcode.tree.BinarySearchTree;
import common.TreeNode;

import java.util.ArrayList;

public class JZ26二叉搜索树与双向链表 {
    /*
题目描述
输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。
     */

    public static class Solution {

        /*
        二叉搜索树，中序遍历
         */
        private ArrayList<TreeNode> list = new ArrayList<>();
        public TreeNode Convert(TreeNode pRootOfTree) {
            if(pRootOfTree == null){
                return null;
            }
            inVisit(pRootOfTree);
            for(int i = 0; i< list.size()-1; i++){
                list.get(i).right = list.get(i+1);
                list.get(i+1).left = list.get(i);
            }
            return list.get(0);
        }

        private void inVisit(TreeNode pRootOfTree) {
            if(pRootOfTree == null){
                return;
            }
            inVisit(pRootOfTree.left);
            list.add(pRootOfTree);
            inVisit(pRootOfTree.right);

        }

    }

    public static void main(String[] args) {
        int [] num = {4,2,5,1,3,6};
        TreeNode root = new TreeNode(num[0]);
        for(int i = 1; i<num.length; i++){
            BinarySearchTree.insertSearchTree(root,new TreeNode(num[i]));
        }
        Solution solution = new Solution();
        solution.Convert(root);

    }
}
