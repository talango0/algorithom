package leetcode.jzhoffer;

import common.TreeLinkNode;

public class JZ57二叉树的下一个结点 {
    /**
     * 题目描述
     * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
     */

    public class Solution {
        public TreeLinkNode GetNext(TreeLinkNode pNode){
            if(pNode == null){
                return null;
            }

            //1.
            if(pNode.right != null){
                TreeLinkNode pRight = pNode.right;
                while (pRight.left!=null){
                    pRight = pRight.left;
                }
                return pRight;
            }

            //2.
            if(pNode.parent != null && pNode.parent.left == pNode){
                return pNode.parent;
            }

            //3.
            if(pNode.parent != null){
                TreeLinkNode pParent = pNode.parent;
                while (pParent.parent!=null && pParent.parent.right == pParent){
                    pParent = pParent.parent;
                }
                return pParent.parent;
            }
            return null;
        }
    }
}
