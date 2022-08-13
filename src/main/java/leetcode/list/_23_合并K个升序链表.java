package leetcode.list;

//给你一个链表数组，每个链表都已经按升序排列。
//
// 请你将所有链表合并到一个升序链表中，返回合并后的链表。
//
//
//
// 示例 1：
//
// 输入：lists = [[1,4,5],[1,3,4],[2,6]]
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
// 输入：lists = []
//输出：[]
//
//
// 示例 3：
//
// 输入：lists = [[]]
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
// Related Topics 堆 链表 分治算法
// 👍 1045 👎 0


import java.util.PriorityQueue;

public class _23_合并K个升序链表 {

    /**
     * 思路，采用优先级队列｜堆
     * 时间复杂度 O(nlogk)
     * 空间复杂度 O(k)
     */
    class Solution {
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0) {
                return null;
            }
            // 虚拟头结点
            ListNode dummy = new ListNode(Integer.MIN_VALUE), p = dummy;
            // 优先级队列，最小堆
            PriorityQueue<ListNode> pq = new PriorityQueue<ListNode>((o1,o2)->{
                return o1.val - o2.val;
            });
            // 将 k 个链表的头结点加入最小堆
            for (ListNode node: lists) {
                if (node!=null) {
                    pq.offer(node);
                }
            }
            ListNode node = null;
            while (!pq.isEmpty()) {
                // 获取最小节点，接到结果链表中
                node = pq.poll();
                p.next = node;
                // p 指针不断前进
                p = p.next;
                if (node.next != null){
                    pq.offer(node.next);
                }
            }
            return dummy.next;
        }
    }

}
