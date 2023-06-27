package leetcode.程序员面试金典;
//编写一个函数，检查输入的链表是否是回文的。
//
//
//
//示例 1：
//
//输入： 1->2
//输出： false 
//示例 2：
//
//输入： 1->2->2->1
//输出： true 
//
//
//进阶：
//你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/palindrome-linked-list-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.list.ListNode;

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _02_06_回文链表{
    /**
     * 递归，后序遍历
     */
    class Solution{
        // 左侧指针
        ListNode left;

        public boolean isPalindrome(ListNode head) {
            left = head;
            return traverse(head);
        }

        boolean traverse(ListNode right) {
            if (right == null) {
                return true;
            }
            // 前序遍历代码
            boolean res = traverse(right.next);
            // 后序遍历代码
            res = res && (right.val == left.val);
            left = left.next;
            return res;
        }
    }

    /**
     * 快慢指针
     */
    class Solution2{
        public boolean isPalindrome(ListNode head) {
            if (head == null || head.next == null) return true;

            ListNode pre = null;
            ListNode cur = null;

            ListNode fast = head;
            ListNode slow = head;
            while (fast != null && fast.next != null) {
                cur = slow;

                fast = fast.next.next;
                slow = slow.next;

                cur.next = pre;
                pre = cur;
            }
            if (fast != null) {
                slow = slow.next;
            }
            while (slow != null) {
                if (slow.val != cur.val) return false;
                slow = slow.next;
                cur = cur.next;
            }
            return true;
        }

    }
}
