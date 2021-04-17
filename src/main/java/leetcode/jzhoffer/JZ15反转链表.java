package leetcode.jzhoffer;

import common.ListNode;

/**
 * 题目描述
 * 输入一个链表，反转链表后，输出新链表的表头。
 * 示例1
 * 输入
 * {1,2,3}
 * 返回值
 * {3,2,1}
 */
public class JZ15反转链表 {

    class Solution {
        public ListNode ReverseList(ListNode head) {
            if(head == null||head.next==null){
                return head;
            }
            ListNode res = new ListNode(head.val);
            head = head.next;
            ListNode tmp = null;
            while(head != null){
                tmp = res;
                res = head;
                head = head.next;
                res.next = tmp;
            }
            return res;
        }
    }


    static class Solution2 {
        public ListNode ReverseList(ListNode head) {
            if(head==null) {
                return null;
            }
            ListNode newHead=null;
            ListNode cur=head;

            ListNode pre=null;
            while(cur!=null) {
                ListNode curNext=cur.next;
                if(curNext==null) {
                    newHead=cur;
                }
                cur.next=pre;
                pre=cur;
                cur=curNext;
            }
            return newHead;

        }
    }
}
