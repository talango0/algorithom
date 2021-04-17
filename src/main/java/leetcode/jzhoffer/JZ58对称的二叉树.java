package leetcode.jzhoffer;

import common.TreeNode;

/**
 * @author mayanwei
 */
public class JZ58对称的二叉树 {

    /**
     * 题目描述
     * 请实现一个函数，用来判断一棵二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
     */
    boolean isSymmetrical(TreeNode pRoot){
        if(pRoot == null){
            return true;
        }
        return isSymmetrical( pRoot,  pRoot);
    }
    boolean isSymmetrical(TreeNode lNode, TreeNode rNode){
        if(lNode == null && rNode == null){
            return true;
        }
        if((lNode != null && rNode == null ) || (lNode == null && rNode!=null)){
            return false;
        }

         return  lNode.key.equals(rNode.key)
                 && isSymmetrical(lNode.left, rNode.right)
                 && isSymmetrical(lNode.right, rNode.left);  //[2,3,3,4,5,null,4]

    }


}
