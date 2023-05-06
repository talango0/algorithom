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

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author mayanwei
 * @date 2022-10-27.
 * @see _2_两数相加
 */
public class _445_两数相加II{

    class Solution2{
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


    class Solution3{
        /**
         * 时间复杂度 O(max(m,n))
         * 空间复杂度 O(m + n)
         */
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            // 把链表转入栈中
            Deque<Integer> stk1 = new ArrayDeque<Integer>();
            Deque<Integer> stk2 = new ArrayDeque<Integer>();
            while (l1 != null) {
                stk1.push(l1.val);
                l1 = l1.next;
            }
            while (l2 != null) {
                stk2.push(l2.val);
                l2 = l2.next;
            }
            // 接下来基本上是复用在第 2 题的代码逻辑
            // 注意新节点要直接插入到 dummy 后面

            // 虚拟头结点（构建新链表时的常用技巧）
            ListNode dummy = new ListNode(-1);
            // 记录进位
            int carry = 0;

            // 开始执行加法，两条链表走完且没有进位时才能结束循环
            ListNode p = dummy;
            while (!stk1.isEmpty() || !stk2.isEmpty() || carry != 0) {
                // 先加上上次的进位
                int val = carry;
                val += stk1.isEmpty() ? 0 :stk1.pop();
                val += stk2.isEmpty() ? 0 :stk2.pop();
                // 处理进位情况
                carry = val / 10;
                val %= 10;
                // 构建新节点，直接接在 dummy 后面
                ListNode curNode = new ListNode(val);
                curNode.next = p.next;
                p.next = curNode;
            }
            // 返回结果链表的头结点（去除虚拟头结点）
            return dummy.next;
        }
    }
}
