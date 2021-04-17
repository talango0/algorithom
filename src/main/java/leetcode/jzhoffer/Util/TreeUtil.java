package leetcode.jzhoffer.Util;

import common.TreeNode;

/**
 * @author mayanwei
 */
public class TreeUtil {

    public static void preVisit(TreeNode treeNode){
        if(treeNode == null){
            return;
        }
        visit(treeNode);
        preVisit(treeNode.left);
        preVisit(treeNode.right);
    }


    public static void inVisit(TreeNode treeNode){
        if(treeNode == null){
            return;
        }
        inVisit(treeNode.left);
        visit(treeNode);
        inVisit(treeNode.right);
    }

    public static void postVisit(TreeNode treeNode){
        if(treeNode == null){
            return;
        }
        postVisit(treeNode.left);
        postVisit(treeNode.right);
        visit(treeNode);
    }
    public static void visit(TreeNode treeNode){
        System.out.print(treeNode.key+";");;
    }

    public static void main(String[] args) {
       TreeNode  root = new TreeNode(1);
       root.left=new TreeNode(2);
       root.right=new TreeNode(4);
       root.left.left = new TreeNode(3);
       TreeUtil.preVisit(root);
       System.out.println();
       TreeUtil.inVisit(root);
       System.out.println();
       TreeUtil.postVisit(root);
    }
}
