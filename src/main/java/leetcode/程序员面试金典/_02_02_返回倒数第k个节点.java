package leetcode.程序员面试金典;
//实现一种算法，找出单向链表中倒数第 k 个节点。返回该节点的值。
//
//注意：本题相对原题稍作改动
//
//示例：
//
//输入： 1->2->3->4->5 和 k = 2
//输出： 4
//说明：
//
//给定的 k保证是有效的。
//
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/kth-node-from-end-of-list-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.list.ListNode;
import leetcode.list._19_删除链表的倒数第N个结点;

/**
 * @author mayanwei
 * @date 2023-06-26.
 * @see _19_删除链表的倒数第N个结点
 */
public class _02_02_返回倒数第k个节点{
    class Solution {
        public int kthToLast(ListNode head, int k) {
            ListNode p1 = head;
            // p1 先走 k 步
            for (int i = 0; i < k; i++) {
                p1 = p1.next;
            }
            ListNode p2 = head;
            // p1 和 p2 同时走 n-k 步
            while (p1 != null) {
                p2 = p2.next;
                p1 = p1.next;
            }
            // 现在 p2 执行第 n-k 个节点
            return p2.val;
        }
    }
}
