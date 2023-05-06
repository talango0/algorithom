package leetcode.list;
//给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。返回 已排序的链表 。
//
//
//
// 示例 1：
//
//
//输入：head = [1,1,2]
//输出：[1,2]
//
//
// 示例 2：
//
//
//输入：head = [1,1,2,3,3]
//输出：[1,2,3]
//
//
//
//
// 提示：
//
//
// 链表中节点数目在范围 [0, 300] 内
// -100 <= Node.val <= 100
// 题目数据保证链表已经按升序 排列
//
// Related Topics 链表 👍 813 👎 0


import common.ListNode;
import leetcode.arrays._26_删除有序数组中的重复项;

//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @see _26_删除有序数组中的重复项
 */
public class _83_删除排序链表中的重复元素 {
    class Solution {
        public ListNode deleteDuplicates(ListNode head) {
            if (head == null) return head;
            ListNode fast = head, slow = head;
            while (fast != null) {
                if (fast.val != slow.val) {
                    //nums[slow] = nums[fast]
                    slow.next = fast;
                    //slow++
                    slow = slow.next;
                }
                //fast++
                fast = fast.next;
            }
            //断开与后面重复元素的连接
            slow.next = null;
            return head;
        }
    }
}
