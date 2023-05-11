package leetcode.tree;

//给你输入一棵存在于二叉树中的两个节点p和q，请你返回它们的最近公共祖先，函数签名如下：
//
//Node lowestCommonAncestor(Node p, Node q);
//由于节点中包含父节点的指针，所以二叉树的根节点就没必要输入了。
//
//这道题其实不是公共祖先的问题，而是单链表相交的问题，你把parent指针想象成单链表的next指针，题目就变成了：
//
//给你输入两个单链表的头结点p和q，这两个单链表必然会相交，请你返回相交点。

public class _1650_二叉树的最近公共祖先3{
    class Node{
        int v;
        Node l;
        Node r;
        Node p;
    }

    // 思路，其实就是单链表相交问题
    class Solution{
        Node lowestCommonAncestor(Node p, Node q) {
            //双指针技巧
            Node a = p, b = q;
            while (a != b) {
                //a 走一步，如果走到根节点，转到q节点
                if (a == null) {
                    a = q;
                }
                else {
                    a = a.p;
                }
                //b 走一步，如果走到根节点，转到p节点
                if (b == null) {
                    b = p;
                }
                else {
                    b = b.p;
                }
            }
            return a;
        }
    }

}
