package leetcode.jzhoffer;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ConstructorBTree {


    public static class Solution {
//        public TreeNode reConstructBinaryTree(int [] pre, int [] in) {
//            TreeNode root;
//            if(pre.length == 0 && in.length == 0){
//                return null;
//            }else{
//                root = new TreeNode(pre[0]);
//                for(int i = 0; i<in.length; i++){
//                    if(pre[0] == in[i]){
//                        root.left = constructBinaryT ree(Arrays.copyOfRange(pre, 1, i + 1)
//                                , Arrays.copyOfRange(in, 0, i));
//
//                        root.right = constructBinaryTree(Arrays.copyOfRange(pre, i+1, pre.length)
//                                ,Arrays.copyOfRange(in, i+1, in.length));
//                        break;
//                    }
//                }
//
//            }
//            return root;
//        }
//        public TreeNode constructBinaryTree(int [] pre,int [] in ){
//            if(pre.length == 1){
//                return  new TreeNode(pre[0]);
//            }
//            TreeNode treeNode = null;
//            if(pre.length>1){
//                treeNode = new TreeNode(pre[0]);
//                for(int i = 0;i<in.length;i++){
//                    if(pre[0] == in[i]){
//                        treeNode.left = constructBinaryTree(Arrays.copyOfRange(pre, 1, i+1)
//                                ,Arrays.copyOfRange(in, 0, i));
//                        treeNode.right = constructBinaryTree(Arrays.copyOfRange(pre, i+1, pre.length)
//                                ,Arrays.copyOfRange(in, i+1, in.length));
//                        break;
//                    }
//                }
//            }
//            return treeNode;
//        }

        public TreeNode reConstructBinaryTree(int [] pre, int [] in) {
            TreeNode root=reConstructBinaryTree(pre,0,pre.length-1,in,0,in.length-1);
            return root;
        }
        //前序遍历 {1,2,4,7,3,5,6,8} 和中序遍历序列 {4,7,2,1,5,3,8,6}
        private TreeNode reConstructBinaryTree(int [] pre,int startPre,int endPre,int [] in,int startIn,int endIn) {

            if(startPre>endPre||startIn>endIn) {
                return null;
            }
            TreeNode root=new TreeNode(pre[startPre]);

            for(int i=startIn;i<=endIn;i++){
                if(in[i]==pre[startPre]){
                    root.left=reConstructBinaryTree(pre,startPre+1,startPre+i-startIn,in,startIn,i-1);
                    root.right=reConstructBinaryTree(pre,i-startIn+startPre+1,endPre,in,i+1,endIn);
                    break;
                }
            }

            return root;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int [] pre = {1,2,4,3,5,6};
        int [] in =  {4,2,1,5,3,6};

        TreeNode treeNode =  solution.reConstructBinaryTree(pre, in);
        preVisit(treeNode);
    }

    public static TreeNode preVisit(TreeNode treeNode){
        if(treeNode != null){
            System.out.print(treeNode.key);
        }
        if(treeNode.left != null){
            preVisit(treeNode.left);
        }
        if(treeNode.right != null){
            preVisit((treeNode.right));
        }
        return null;
    }


    //访问节点元素
    public Integer visit(TreeNode treeNode){
        if(treeNode!=null){
            return (int) treeNode.key;
        }
        return null;
    }


    public ArrayList<Integer> levelVisitBTree(TreeNode treeNode){
        if( treeNode == null ) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList();
        ArrayList<Integer> res = new ArrayList<Integer>();
        queue.offer(treeNode);
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

    private void levelVisit(TreeNode treeNode, Queue<TreeNode> queue, ArrayList<Integer> res) {
        TreeNode treeNodeTemp;
        treeNodeTemp = new TreeNode(treeNode.key);
        res.add((int)treeNode.key);
        queue.offer(treeNodeTemp);

        while (queue.size()>0){
            TreeNode node = queue.poll();
            Integer item = visit(queue.poll());
            if(item != null){
                res.add(item);
            }
        }
    }

}

