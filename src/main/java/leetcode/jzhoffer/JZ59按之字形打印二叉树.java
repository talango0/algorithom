package leetcode.jzhoffer;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;

public class JZ59按之字形打印二叉树{

    static class Solution{
        public ArrayList<ArrayList<Integer> >  zigzagLevelOrder(TreeNode treeNode){
            if(treeNode == null){
                return null;
            }
            int level = 0;
            LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
            queue.offer(treeNode);
            ArrayList<ArrayList<Integer> >  result = new ArrayList<>();
            while(!queue.isEmpty()){
                ArrayList<Integer> levelRes = new ArrayList<Integer>();
                int size = queue.size();
                for(int i=0; i<size; i++){
                    TreeNode node;
                    if(level%2 == 0){
                        node = queue.pollLast();
                        if(node.left != null){
                            queue.offerFirst(node.left);
                        }
                        if(node.right != null){
                            queue.offerFirst(node.right);
                        }
                    }else{
                        node = queue.poll();
                        if(node.right != null){
                            queue.offer(node.right);
                        }
                        if(node.left != null){
                            queue.offer(node.left);
                        }

                    }
                    levelRes.add(node.key);
                }
                level++;
                result.add(levelRes);
            }
            return result;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(1);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode((7));
        root.right.right = new TreeNode(8);

        Solution solution = new Solution();
        ArrayList<ArrayList<Integer>> lists = solution.zigzagLevelOrder(root);
        System.out.println(lists.toString());//[5, 6, 1, 7, 8]

    }
}
