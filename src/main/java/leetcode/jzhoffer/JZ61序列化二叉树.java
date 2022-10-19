package leetcode.jzhoffer;

import leetcode.tree.BinarySearchTree;
import common.TreeNode;
import leetcode.tree._297_二叉树的序列化和反序列化;

import java.util.*;

/**
 * 剑指 Offer 37. 序列化二叉树
 * @see _297_二叉树的序列化和反序列化
 * @see JZ61序列化二叉树
 */
public class JZ61序列化二叉树 {
    /**
     * 请实现两个函数，分别用来序列化和反序列化二叉树
     *
     * 二叉树的序列化是指：把一棵二叉树按照某种遍历方式的结果以某种格式保存为字符串，从而使得内存中建立起来的二叉树可以持久保存。
     * 序列化可以基于先序、中序、后序、层序的二叉树遍历方式来进行修改，序列化的结果是一个字符串，序列化时通过 某种符号表示空节点（#），以 , 表示一个结点值的结束（value,）。
     *
     * 二叉树的反序列化是指：根据某种遍历顺序得到的序列化字符串结果str，重构二叉树。
     *
     * 例如，我们可以把一个只有根节点为1的二叉树序列化为"1,"，然后通过自己的函数来解析回这个二叉树
     */

    public static class Solution {
        String Serialize(TreeNode root) {
            if(root == null){
                return "";
            }
            StringBuffer treeStr = new StringBuffer();
            LinkedList<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()){
                TreeNode node = queue.poll();
                if(node == null){
                    treeStr.append("#").append(",");
                    continue;
                }else {
                    treeStr.append(node.key.toString()+",");
                }

                TreeNode left = node.left;
                TreeNode right = node.right;
                if(left!=null){
                    queue.addLast(left);
                }else {
//                    queue.addLast(new TreeNode(null));
                }
                if(right != null){
                    queue.addLast(right);
                }else {
//                    queue.addLast(new TreeNode(null));
                }
            }
            return treeStr.toString();
        }
        TreeNode Deserialize(String str) {
            if(str == null){
                return null;
            }
            String[] treeItem = str.split(",");
            if(treeItem.length < 1){
                return null;
            }

            LinkedList<TreeNode> queue = new LinkedList<>();
            int i =0;
            String k = treeItem[i++];
            TreeNode root = new TreeNode(Integer.valueOf(k));
            queue.push(root);
            while (!queue.isEmpty()){
                TreeNode node = queue.poll();
                if(node!=null){
                    String l = treeItem[i++];
                    String r = treeItem[i++];
                    if(!l.equals("#")){
                        TreeNode left = new TreeNode(Integer.valueOf(l));
                        node.left = left;
                        queue.addLast(left);
                    }
                    if(!r.equals("#") ){
                        TreeNode right = new TreeNode(Integer.valueOf(r));
                        node.right = right;
                        queue.addLast(right);
                    }
                }
            }
            return root;
        }
    }

    static class Solution2{
        public String serialize(TreeNode root) {
            if(root == null) {
                return "[]";
            }
            StringBuilder res = new StringBuilder("[");
            Queue<TreeNode> queue = new LinkedList<TreeNode>() {{ add(root); }};
            while(!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if(node != null) {
                    res.append(node.key + ",");
                    queue.add(node.left);
                    queue.add(node.right);
                }else {
                    res.append("null,");
                }
            }
            res.deleteCharAt(res.length() - 1);
            res.append("]");
            return res.toString();
        }

        public TreeNode deserialize(String data) {
            if(data.equals("[]")) {
                return null;
            }
            String[] vals = data.substring(1, data.length() - 1).split(",");
            TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
            Queue<TreeNode> queue = new LinkedList<TreeNode>() {{ add(root); }};
            int i = 1;
            while(!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if(!vals[i].equals("null")) {
                    node.left = new TreeNode(Integer.parseInt(vals[i]));
                    queue.add(node.left);
                }
                i++;
                if(!vals[i].equals("null")) {
                    node.right = new TreeNode(Integer.parseInt(vals[i]));
                    queue.add(node.right);
                }
                i++;
            }
            return root;
        }

    }

    public static void main(String[] args) {
        int [] data={2,3,5,6,4,8,3,45,3,2};
        TreeNode root = new TreeNode(data[0]);

        Arrays.stream(Arrays.copyOfRange(data,1,data.length)).forEach(item->{
            BinarySearchTree.insertSearchTree(root,new TreeNode(item));
        });
        Solution2 solution2 = new Solution2();
        String serialize = solution2.serialize(root);
        System.out.println(serialize);
        TreeNode tree = solution2.deserialize(serialize);
        System.out.println(solution2.serialize(tree));
    }

}
