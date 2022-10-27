package leetcode.jzhoffer;

import leetcode.list.ListNode;
import leetcode.list._206_反转链表;

/**
 * 注意：本题与主站 206 题相同： https://leetcode-cn.com/problems/reverse-linked-list/
 * @see _206_反转链表
 */
public class 剑指_Offer_II_024_反转链表 {
    class Solution {
        public ListNode reverseList(ListNode head) {
            return reverse(head);
        }

        public ListNode reverse(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode last = reverse(head.next);
            head.next.next = head;
            head.next = null;
            return last;
        }
    }
}
