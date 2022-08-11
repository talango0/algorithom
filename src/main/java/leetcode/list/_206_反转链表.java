package leetcode.list;
//给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
//
//
//
//
//
//
//
// 示例 1：
//
//
//输入：head = [1,2,3,4,5]
//输出：[5,4,3,2,1]
//
//
// 示例 2：
//
//
//输入：head = [1,2]
//输出：[2,1]
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
// 链表中节点的数目范围是 [0, 5000]
// -5000 <= Node.val <= 5000
//
//
//
//
// 进阶：链表可以选用迭代或递归方式完成反转。你能否用两种方法解决这道题？
//
// Related Topics 递归 链表 👍 2689 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-08-11.
 */
public class _206_反转链表{

    public class ListNode{
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 迭代解法
     */
    class Solution{
        public ListNode reverseList(ListNode head) {
            ListNode pre, cur, nxt;
            pre = null;
            cur = head;
            nxt = head;
            while (cur != null) {
                // 反转
                nxt = cur.next;
                cur.next = pre;
                // 继续向后迭代
                pre = cur;
                cur = nxt;
            }
            return pre;
        }
    }

    /**
     * 递归解法
     */
    class Solution1 {
        public ListNode reverseList(ListNode head) {
            return reverse(head);
        }

        public ListNode reverse(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode last = reverse(head.next);
            head.next.next = head;
            head.next = null;
            return last;
        }
    }
}
