package leetcode.jzhoffer;

import leetcode.tree.BinarySearchTree;
import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class JZ62二叉搜索树的第k个结点 {
    /**
     * 给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）    中，按结点数值大小顺序第三小结点的值为4。
     */


    public static class Solution {
        TreeNode KthNode(TreeNode  pRoot, int k){
            if(pRoot == null||k <=0 ){
                return null;
            }
            List<TreeNode> visitList = new ArrayList<>();
            inVist(pRoot, k, visitList);
            if(visitList.size()>=k){
                return  visitList.get(k-1);
            }
            return null;
        }

        private void inVist(TreeNode pRoot, int k, List<TreeNode> visitList) {
            if(pRoot == null || visitList.size() == k){
                return;
            }else{
                inVist(pRoot.left, k, visitList);
                visitList.add(pRoot);
                inVist(pRoot.right, k, visitList);
            }
        }
    }

    public static void main(String[] args) {
        int [] data = {8,6,10,5,7,9,11};
        TreeNode root = new TreeNode (data[0]);
        for(int i = 1; i<data.length; i++){
            BinarySearchTree.insertSearchTree(root, new TreeNode(data[i]));
        }

//        BinarySearchTree.inOrderTreeWalk(root);
        Solution solution = new Solution();

        System.out.println(solution.KthNode(root,0));
    }


}
