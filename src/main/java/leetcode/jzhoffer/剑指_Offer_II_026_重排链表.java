package leetcode.jzhoffer;

import leetcode.list.ListNode;
import leetcode.list._143_重排链表;

/**
 * @author mayanwei
 * @date 2022-10-29.
 * @see _143_重排链表
 */
public class 剑指_Offer_II_026_重排链表{
    class Solution{
        /**
         * 寻找链表中点 + 链表逆序 + 合并链表
         * 1. 找到原链表的中点
         * 2. 将原链表的右半端反转
         * 3. 将原链表的两端合并
         * 时间复杂度 O(n)
         * 空间复杂度 O(1)
         */
        public void reorderList(ListNode head) {
            if (head == null) {
                return;
            }
            ListNode mid = middleNode(head);
            ListNode l1 = head;
            ListNode l2 = mid.next;
            mid.next = null;
            l2 = reverseList(l2);
            mergeList(l1, l2);
        }

        public ListNode middleNode(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;
            while (fast.next != null && fast.next.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            return slow;
        }

        public ListNode reverseList(ListNode head) {
            ListNode prev = null;
            ListNode curr = head;
            while (curr != null) {
                ListNode nextTemp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextTemp;
            }
            return prev;
        }

        public void mergeList(ListNode l1, ListNode l2) {
            ListNode l1_tmp;
            ListNode l2_tmp;
            while (l1 != null && l2 != null) {
                l1_tmp = l1.next;
                l2_tmp = l2.next;

                l1.next = l2;
                l1 = l1_tmp;

                l2.next = l1;
                l2 = l2_tmp;
            }
        }
    }
}
