package leetcode.list;
//给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
//
// 请你将两个数相加，并以相同形式返回一个表示和的链表。
//
// 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
//
//
//
// 示例 1：
//
//
//输入：l1 = [2,4,3], l2 = [5,6,4]
//输出：[7,0,8]
//解释：342 + 465 = 807.
//
//
// 示例 2：
//
//
//输入：l1 = [0], l2 = [0]
//输出：[0]
//
//
// 示例 3：
//
//
//输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
//输出：[8,9,9,9,0,0,0,1]
//
//
//
//
// 提示：
//
//
// 每个链表中的节点数在范围 [1, 100] 内
// 0 <= Node.val <= 9
// 题目数据保证列表表示的数字不含前导零
//
//
// Related Topics 递归 链表 数学 👍 8823 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-10-27.
 */
public class _2_两数相加{
    class Solution{
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            // 在两条链表上的指针
            ListNode p1 = l1, p2 = l2;
            // 虚拟头节点（构建新链表时常用技巧）
            ListNode dummy = new ListNode(-1);

            // 指针 p 负责构建新链表
            ListNode p = dummy;
            // 记录进位
            int carry = 0;
            // 开始执行加法两条链表走完且没有进位时才能结束循环
            while (p1 != null || p2 != null || carry > 0) {
                // 先加上上次的进位
                int val = carry;
                if (p1 != null) {
                    val += p1.val;
                    p1 = p1.next;
                }
                if (p2 != null) {
                    val += p2.val;
                    p2 = p2.next;
                }
                // 处理进位情况
                carry = val / 10;
                val = val % 10;
                // 构建新节点
                p.next = new ListNode(val);
                p = p.next;
            }
            // 返回结果链表的头节点（取出虚拟头结点）
            return dummy.next;
        }
    }
}
