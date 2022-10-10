package leetcode.jzhoffer;
//给定一个链表数组，每个链表都已经按升序排列。
//
// 请将所有链表合并到一个升序链表中，返回合并后的链表。
//
//
//
// 示例 1：
//
//
//输入：lists = [[1,4,5],[1,3,4],[2,6]]
//输出：[1,1,2,3,4,4,5,6]
//解释：链表数组如下：
//[
//  1->4->5,
//  1->3->4,
//  2->6
//]
//将它们合并到一个有序链表中得到。
//1->1->2->3->4->4->5->6
//
//
// 示例 2：
//
//
//输入：lists = []
//输出：[]
//
//
// 示例 3：
//
//
//输入：lists = [[]]
//输出：[]
//
//
//
//
// 提示：
//
//
// k == lists.length
// 0 <= k <= 10^4
// 0 <= lists[i].length <= 500
// -10^4 <= lists[i][j] <= 10^4
// lists[i] 按 升序 排列
// lists[i].length 的总和不超过 10^4
//
//
//
//
//
// 注意：本题与主站 23 题相同： https://leetcode-cn.com/problems/merge-k-sorted-lists/
//
// Related Topics 链表 分治 堆（优先队列） 归并排序 👍 58 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import common.ListNode;
import leetcode.list._141_环形链表;

import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-10-10.
 * @see  _141_环形链表
 */
public class 剑指_Offer_II_078_合并排序链表{
    class Solution{
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null) {
                return null;
            }
            int k = lists.length;
            PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>((o1, o2) -> {
                return o1.val < o2.val ? -1 :(o1.val == o2.val ? 0 :1);
            });
            for (ListNode node : lists) {
                if (node != null) {
                    queue.offer(node);
                }
            }
            ListNode dummy = new ListNode(-1), p = dummy;
            while (!queue.isEmpty()) {
                ListNode node = queue.poll();
                p.next = node;
                p = p.next;
                if (node.next != null) {
                    queue.offer(node.next);
                }
            }
            return dummy.next;
        }
    }
}
