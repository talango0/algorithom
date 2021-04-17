package leetcode.tree;

import common.TreeNode;

import java.util.*;

/**
 * 题目：按照z字形层次遍历二叉树（以根节点所在层为第1层，则第二层的变量从右边节点开始直到最左边节点，第三层遍历则是从最左边开始到最右边）
 * 思路：z字形层次遍历是对层次遍历加上了一个限制条件（即相邻层，从左到右的遍历顺序相反），因此还是可以采用队列来实现，只不过节点接入队列时需要考虑加入的顺序
 * @author mayanwei
 */
public class ZigzagLevelOrder {
//    static public class Solution {
//        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
//            List<List<Integer>> result = new ArrayList<List<Integer>>();
//            if(root == null){
//                return result;
//            }
//            LinkedList<TreeNode> queue = new LinkedList<>();
//            int depth = 0;
//            queue.offer(root);
//            while(!queue.isEmpty()){
//                int size = queue.size();
//                List<Integer> tmp = new ArrayList<>();
//                for(int i = 0; i < size; i++){
//                    TreeNode node = null;
//                    //因为是走z字形，所有相邻两层的节点处理是相反的
//                    if(depth%2 == 0){
//                        node = queue.pollLast();//获取链表最后一个节点
//                        if(node.left != null){
//                            queue.offerFirst(node.left);
//                        }
//                        if(node.right != null){
//                            queue.offerFirst(node.right);
//                        }
//
//                    }else{
//                        node = queue.poll();//获取链表第一个节点
//                        if(node.right != null){
//                            queue.offer(node.right);
//                        }
//                        if(node.left != null){
//                            queue.offer(node.left);
//                        }
//                    }
//                    tmp.add((Integer) node.value);
//                }
//                depth++;
//                result.add(tmp);
//            }
//            return result;
//        }
//    }
static class Solution{
    public List<List<Integer>> zigzagLevelOrder(TreeNode treeNode){
        if(treeNode == null){
            return null;
        }
        int level = 0;
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(treeNode);
        List result = new ArrayList<List<Integer>>();
        while(!queue.isEmpty()){
            List levelRes = new ArrayList<Integer>();
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
            result.addAll(levelRes);
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
        List<List<Integer>> lists = solution.zigzagLevelOrder(root);
        System.out.println(lists.toString());//[5, 6, 1, 7, 8]

    }
}
