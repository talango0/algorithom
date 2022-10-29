package leetcode.jzhoffer;

import leetcode.list.ListNode;
import leetcode.list._19_删除链表的倒数第N个结点;

/**
 * @author mayanwei
 * @date 2022-10-29.
 * @see _19_删除链表的倒数第N个结点
 */
public class 剑指_Offer_II_021_删除链表的倒数第n个结点{
    class Solution{
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(-1);
            dummy.next = head;
            // 删除第 n 个，先要找到第 n + 1 个节点
            ListNode x = findFromEnd(dummy, n + 1);
            x.next = x.next.next;
            return dummy.next;
        }

        private ListNode findFromEnd(ListNode head, int k) {
            ListNode p1 = head;
            // p1 先走 k 步
            for (int i = 0; i < k; i++) {
                p1 = p1.next;
            }

            ListNode p2 = head;
            // p1 和 p2 同时走 n-k 步
            while (p1 != null) {
                p2 = p2.next;
                p1 = p1.next;
            }
            // p2 现在指向了第 n-k 个节点
            return p2;
        }
    }
}
