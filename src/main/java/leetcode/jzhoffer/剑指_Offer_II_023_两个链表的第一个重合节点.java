package leetcode.jzhoffer;

import leetcode.list.ListNode;
import leetcode.list._160_相交链表;

/**
 * @author mayanwei
 * @date 2022-10-29.
 * @see _160_相交链表
 */
public class 剑指_Offer_II_023_两个链表的第一个重合节点{
    public class Solution{
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            // p1 指向 A链表头节点，p2 指向 B 链表头结点
            ListNode p1 = headA, p2 = headB;
            while (p1 != p2) {
                // p1 走一步，如果走到A链表末尾，转向 B 链表
                if (p1 == null) {
                    p1 = headB;
                }
                else {
                    p1 = p1.next;
                }
                // p2 走一步，如果走到B链末尾，转向 A 链表
                if (p2 == null) {
                    p2 = headA;
                }
                else {
                    p2 = p2.next;
                }
            }
            return p1;
        }
    }
}
