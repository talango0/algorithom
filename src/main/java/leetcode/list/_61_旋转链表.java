package leetcode.list;
//给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
//
// 示例 1：
//输入：head = [1,2,3,4,5], k = 2
//输出：[4,5,1,2,3]
//
//
// 示例 2：
//输入：head = [0,1,2], k = 4
//输出：[2,0,1]
//
//
//
//
// 提示：
//
//
// 链表中节点的数目在范围 [0, 500] 内
// -100 <= Node.val <= 100
// 0 <= k <= 2 * 10⁹
//
//
// Related Topics 链表 双指针 👍 833 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-10-02.
 */
public class _61_旋转链表{
    class Solution{
        /**
         * 重整操作的步骤
         * 1. newHead 是新链表的头部，它应该是原链表的倒数第 k 个节点，即 slow.next;
         * 2. slow 需要跟 slow.next 断开；
         * 3. fast 是老链表的结尾，将 fast.next 设置为老链表的开头，实现首尾相连。
         * <p>
         * 时间复杂度：O(N)
         * 空间复杂度：O(1)
         */
        public ListNode rotateRight(ListNode head, int k) {
            if (head == null || k == 0) {
                return head;
            }
            int l = 0;
            ListNode fast = head, slow = head;
            while (fast != null) {
                l += 1;
                fast = fast.next;
            }
            k %= l;
            if (k == 0) {
                return head;
            }
            // 快慢指针找到倒数第k个节点
            fast = head;
            while (fast.next != null) {
                if (k-- < 1) {
                    slow = slow.next;
                }
                fast = fast.next;
            }
            ListNode newHead = slow.next;
            slow.next = null;
            fast.next = head;
            return newHead;
        }
    }
}
