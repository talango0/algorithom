package leetcode.list;
//给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
//
// 示例 1：
//输入：head = [1,2,3,4]
//输出：[2,1,4,3]
//
//
// 示例 2：
//输入：head = []
//输出：[]
//
//
// 示例 3：
//输入：head = [1]
//输出：[1]
//
// 提示：
//
//
// 链表中节点的数目在范围 [0, 100] 内
// 0 <= Node.val <= 100
//
//
// Related Topics 递归 链表 👍 1588 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-10-04.
 */
public class _24_两两交换链表中的节点{
    class Solution{
        // 定义：输入以 head 开头的单链表，将这个单链表中的每两个元素翻转，
        // 返回翻转后的链表头结点
        public ListNode swapPairs(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode first = head;
            ListNode second = head.next;

            first.next = second.next;
            second.next = first;
            first.next = swapPairs(first.next);
            return second;
        }
    }
}
