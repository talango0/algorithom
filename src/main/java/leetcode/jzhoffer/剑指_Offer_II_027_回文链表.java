package leetcode.jzhoffer;

import leetcode.list.ListNode;
import leetcode.list._234_回文链表;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author mayanwei
 * @date 2022-10-28.
 * @see _234_回文链表
 */
public class 剑指_Offer_II_027_回文链表{

    class Solution {
        public  boolean  isPalindrome(ListNode head) {
            return isPalindrome2(head);
        }

        public  boolean  isPalindrome2(ListNode head) {
            if(head == null || head.next == null) return true;

            ListNode pre = null;
            ListNode cur = null;

            ListNode fast = head;
            ListNode slow = head;
            while(fast != null && fast.next != null) {
                cur = slow;

                fast = fast.next.next;
                slow = slow.next;

                cur.next = pre;
                pre = cur;
            }
            if(fast != null) {
                slow = slow.next;
            }
            while(slow != null) {
                if(slow.val != cur.val) return false;
                slow = slow.next;
                cur = cur.next;
            }
            return true;
        }

        public boolean isPalindrome1(ListNode head) {
            Deque<ListNode> deque = new ArrayDeque<>();
            int count = 0 ;
            while(head != null){
                deque.push(head);
                head = head.next;
                count ++;
            }

            for(int i = 0 ; i < count >> 1 ; i ++){
                int a = deque.pop().val ;
                int b = deque.pollLast().val;
                if(a != b ) return false;
            }
            return true;

        }
    }
}
