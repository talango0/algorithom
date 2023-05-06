package leetcode.list;
//给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
//
//
// 示例 1：
//输入：head = [4,2,1,3]
//输出：[1,2,3,4]
//
//
// 示例 2：
//输入：head = [-1,5,3,4,0]
//输出：[-1,0,3,4,5]
//
//
// 示例 3：
//
//
//输入：head = []
//输出：[]
//
//
//
//
// 提示：
//
//
// 链表中节点的数目在范围 [0, 5 * 10⁴] 内
// -10⁵ <= Node.val <= 10⁵
//
//
//
//
// 进阶：你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
//
// Related Topics 链表 双指针 分治 排序 归并排序 👍 1776 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import leetcode.arrays.MergeSort;

/**
 * @author mayanwei
 * @date 2022-09-09.
 * @see MergeSort
 */
public class _148_排序链表{
    class Solution{
        public ListNode sortList(ListNode head) {
            return mergeSort(head);
        }

        // 归并排序
        private ListNode mergeSort(ListNode head) {
            // 如果没有结点/只有一个结点，无需排序，直接返回
            if (head == null || head.next == null) {
                return head;
            }
            // 快慢指针找出中位点
            ListNode slowp = head, fastp = head.next.next, l, r;
            while (fastp != null && fastp.next != null) {
                slowp = slowp.next;
                fastp = fastp.next.next;
            }
            // 对右半部分进行归并排序
            r = mergeSort(slowp.next);
            // 链表判断结束的标志：末尾节点.next==null
            slowp.next = null;
            // 对左半部分进行归并排序
            l = mergeSort(head);
            return mergeList(l, r);
        }

        // 合并链表
        private ListNode mergeList(ListNode l, ListNode r) {
            // 临时头节点
            ListNode tmpHead = new ListNode(-1);
            ListNode p = tmpHead;
            while (l != null && r != null) {
                if (l.val < r.val) {
                    p.next = l;
                    l = l.next;
                }
                else {
                    p.next = r;
                    r = r.next;
                }
                p = p.next;
            }
            p.next = l == null ? r :l;
            return tmpHead.next;
        }
    }
}
