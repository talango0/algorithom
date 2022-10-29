package leetcode.list;
//给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
//
// 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
//
//
//
// 示例1：
//
//
//
//
//输入：l1 = [7,2,4,3], l2 = [5,6,4]
//输出：[7,8,0,7]
//
//
// 示例2：
//
//
//输入：l1 = [2,4,3], l2 = [5,6,4]
//输出：[8,0,7]
//
//
// 示例3：
//
//
//输入：l1 = [0], l2 = [0]
//输出：[0]
//
//
//
//
// 提示：
//
//
// 链表的长度范围为 [1, 100]
// 0 <= node.val <= 9
// 输入数据保证链表代表的数字无前导 0
//
//
//
//
// 进阶：如果输入链表不能翻转该如何解决？
//
// Related Topics 栈 链表 数学 👍 561 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * @author mayanwei
 * @date 2022-10-27.
 * @see _2_两数相加
 */
public class _445_两数相加II{

    class Solution{
        ListNode dummy = new ListNode();
        int carry = 0;

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            if (l1 == null) {
                int val = carry;
                val += l2.val;
                carry = val / 10;
                carry = carry % 10;
                ListNode node = new ListNode(val);
                node.next = dummy.next;
                dummy.next = node;
                return dummy.next;
            }
            if (l2 == null) {
                int val = carry;
                val += l1.val;
                carry = val / 10;
                carry = carry % 10;
                ListNode node = new ListNode(val);
                node.next = dummy.next;
                dummy.next = node;
                return dummy.next;
            }
            if (l1.next == null && l2.next == null) {
                int val = 0;
                val = l1.val + l2.val;
                carry = val / 10;
                carry = carry % 10;
                ListNode node = new ListNode(val);
                node.next = dummy.next;
                dummy.next = node;
                return dummy.next;
            }
            ListNode node = addTwoNumbers(l1.next, l2.next);
            node.next = dummy.next;
            dummy.next = node;
            return dummy.next;

        }
    }


    class Solution2 {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            l1 = reverse(l1);
            l2 = reverse(l2);

            return reverse(addList(l1, l2));
        }

        private ListNode addList(ListNode l1, ListNode l2) {
            if (l1 == null) {
                return l2;
            }
            if (l2 == null) {
                return l1;
            }

            int carry = 0;
            // sum Node -> next
            ListNode dummy = new ListNode(0);
            ListNode pre = dummy;
            while (l1 != null && l2 != null) {
                int total = l1.val + l2.val + carry;
                int number = total % 10;
                carry = total / 10;
                ListNode node = new ListNode(number);
                dummy.next = node;
                dummy = dummy.next;
                l1 = l1.next;
                l2 = l2.next;
            }

            while (l1 != null) {
                int total = l1.val + carry;
                int number = total % 10;
                ListNode node = new ListNode(number);
                carry = total / 10;
                dummy.next = node;
                dummy = dummy.next;
                l1 = l1.next;
            }

            while (l2 != null) {
                int total = l2.val + carry;
                int number = total % 10;
                ListNode node = new ListNode(number);
                carry = total / 10;
                dummy.next = node;
                dummy = dummy.next;
                l2 = l2.next;
            }

            if (carry != 0) {
                ListNode node = new ListNode(carry);
                dummy.next = node;
            }
            return pre.next;

        }

        public ListNode reverse(ListNode head) {
            if (head == null) {
                return head;
            }
            ListNode prev = head;
            ListNode current = head.next;
            prev.next = null;
            while (current != null) {
                ListNode next = current.next;
                current.next = prev;
                prev = current;
                current = next;
            }
            return prev;
        }
    }
}
