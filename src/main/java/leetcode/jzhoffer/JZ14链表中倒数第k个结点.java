package leetcode.jzhoffer;


import common.ListNode;

/**
 *
 * 题目描述
 * 输入一个链表，输出该链表中倒数第k个结点。
 * 示例1
 * 输入
 * 1,{1,2,3,4,5}
 * 返回值
 * {5}
 */
public class JZ14链表中倒数第k个结点 {

    static class Solution {
        public ListNode FindKthToTail(ListNode head, int k) {

            if(head == null || k == 0){
                return null;
            }
            //用p,q 分别指向链表头节点，q首先遍历k个节点，p接着和q一起遍历节点，当q遍历到最后一个节点时，此时p也是倒数第k个节点。
            ListNode q = head;
            for(int i= 1; i<k; i++){
                if(q != null && q.next != null){
                    q = q.next;
                }else if(i<k){
                    return null;
                }
            }
            ListNode p = head;
            while(q != null && q.next != null){
                p = p.next;
                q = q.next;
            }
            return p;
        }
    }
}
