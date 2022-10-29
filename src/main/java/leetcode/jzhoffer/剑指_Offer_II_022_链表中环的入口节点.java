package leetcode.jzhoffer;

import leetcode.list.ListNode;
import leetcode.list._142_环形链表II;

/**
 * @author mayanwei
 * @date 2022-10-29.
 * @see _142_环形链表II
 */
public class 剑指_Offer_II_022_链表中环的入口节点{
    public class Solution {
        public ListNode detectCycle(ListNode head) {
            ListNode fast, slow;
            fast = slow = head;
            while (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
                if (fast == slow) {
                    break;
                }
            }
            // 上面的代码类似 hasCycle 函数
            if (fast == null || fast.next == null) {
                // fast 遇到空指针说明没有环
                return null;
            }
            // 重新指向头结点
            slow = head;
            // 快慢指针同步前进，相交点就是环起点
            while (slow != fast) {
                fast = fast.next;
                slow = slow.next;
            }
            return slow;
        }
    }
}
