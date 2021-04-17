package leetcode.list;

import java.util.PriorityQueue;

public class _23_合并K个升序链表 {


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


//leetcode submit region begin(Prohibit modification and deletion)


    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode( int val ) {
            this.val = val;
        }

        ListNode( int val, ListNode next ) {
            this.val = val;
            this.next = next;
        }
    }


    /**
     * 思路，采用优先级队列｜堆
     * 时间复杂度 O(nlogk)
     * 空间复杂度 O(k)
     */

    class Solution {
        class Node implements Comparable<Node>{
            int val;
            ListNode listNode;
            @Override
            public int compareTo(Node node){
                return this.val - node.val;
            }
            Node(int val, ListNode listNode){
                this.val = val;
                this.listNode = listNode;
            }
        }
        public ListNode mergeKLists(ListNode[] lists) {
            if(lists == null || lists.length == 0){
                return null;
            }
            PriorityQueue<Node> queue = new PriorityQueue<Node>();
            for(ListNode listNode : lists){
                if(listNode != null){
                    queue.offer(new Node(listNode.val, listNode));
                }
            }
            ListNode res = new ListNode(Integer.MAX_VALUE);
            ListNode tail = res;
            while(!queue.isEmpty()){
                Node node = queue.poll();
                tail.next = node.listNode;
                tail = tail.next;
                if(node.listNode.next != null){
                    queue.offer(new Node(node.listNode.next.val,node.listNode.next));
                }
            }
            return res.next;

        }

    }
//leetcode submit region end(Prohibit modification and deletion)

}
