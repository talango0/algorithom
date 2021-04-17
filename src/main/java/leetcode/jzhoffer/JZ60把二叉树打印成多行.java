package leetcode.jzhoffer;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;

public class JZ60把二叉树打印成多行 {
    /**
     * 从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。
     */
    public class Solution {
        ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
            if(pRoot == null){
                return null;
            }
            ArrayList<ArrayList<Integer>> res = new ArrayList<>();
            LinkedList<TreeNode> queue = new LinkedList<>();
            queue.offer(pRoot);
            while (!queue.isEmpty()){
                ArrayList<Integer> level = new ArrayList<>();
                int size = queue.size();
                for (int i=0; i<size; i++){
                    TreeNode node = queue.poll();
                    level.add(node.key);
                    if(node.left != null){
                        queue.addLast(node.left);
                    }
                    if(node.right != null){
                        queue.addLast(node.right);
                    }
                }
                res.add(level);
            }
            return res;
        }

    }
}
