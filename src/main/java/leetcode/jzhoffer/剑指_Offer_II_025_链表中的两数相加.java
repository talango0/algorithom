package leetcode.jzhoffer;
//给定两个 非空链表 l1和 l2来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
//
//可以假设除了数字 0 之外，这两个数字都不会以零开头。
//
//
//
//示例1：
//
//
//
//输入：l1 = [7,2,4,3], l2 = [5,6,4]
//输出：[7,8,0,7]
//示例2：
//
//输入：l1 = [2,4,3], l2 = [5,6,4]
//输出：[8,0,7]
//示例3：
//
//输入：l1 = [0], l2 = [0]
//输出：[0]
//
//
//提示：
//
//链表的长度范围为 [1, 100]
//0 <= node.val <= 9
//输入数据保证链表代表的数字无前导 0
//
//
//进阶：如果输入链表不能修改该如何处理？换句话说，不能对列表中的节点进行翻转。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/lMSNwu
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.list.ListNode;
import leetcode.list._445_两数相加II;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author mayanwei
 * @date 2022-10-27.
 * @see _445_两数相加II
 */
public class 剑指_Offer_II_025_链表中的两数相加{
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
                // 构建新节点，直接接在 dummy 后面，采用头插法
                ListNode curNode = new ListNode(val);
                curNode.next = p.next;
                p.next = curNode;
            }
            // 返回结果链表的头结点（去除虚拟头结点）
            return dummy.next;
        }
    }
}
