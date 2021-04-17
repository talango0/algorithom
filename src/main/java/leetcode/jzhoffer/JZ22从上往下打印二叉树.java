package leetcode.jzhoffer;


import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 题目描述
 * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
 * 层序遍历
 * 示例1
 * 输入
 * {5,4,#,3,#,2,#,1}
 * 返回值
 * [5,4,3,2,1]
 */
public class JZ22从上往下打印二叉树 {

    public class Solution1 {
        public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
            if( root == null ) {
                return new ArrayList();
            }
            Queue<TreeNode> queue = new LinkedList();
            ArrayList<Integer> res = new ArrayList<Integer>();
            queue.offer(root);
            while (!queue.isEmpty()){
                TreeNode nowNode = queue.poll();
                res.add(visit(nowNode));
                TreeNode left =  nowNode.left;
                TreeNode right = nowNode.right;
                if(left != null) {
                    queue.offer(left);
                }
                if(right != null) {
                    queue.offer(right);
                }
            }
            return res;
        }
        public Integer visit(TreeNode treeNode){
            if(treeNode!=null){
                return treeNode.key;
            }
            return null;
        }
    }


    public class Solution2 {
        public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
            ArrayList<Integer> list = new ArrayList<>();
            if (root==null)return list;
            //用链表生成队列
            LinkedList<TreeNode> queue= new LinkedList<>();
            queue.add(root);

            while(!queue.isEmpty()){
                list.add(queue.pop().key);
                if (root.left!=null)
                    queue.add(root.left);

                if (root.right!=null)
                    queue.add(root.right);

                if (!queue.isEmpty())
                    root=queue.peek();
            }
            return list;
        }
    }
}
