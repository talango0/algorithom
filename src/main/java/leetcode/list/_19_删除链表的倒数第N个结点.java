package leetcode.list;
//给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
//
//
//
// 示例 1：
//
//
//输入：head = [1,2,3,4,5], n = 2
//输出：[1,2,3,5]
//
//
// 示例 2：
//
//
//输入：head = [1], n = 1
//输出：[]
//
//
// 示例 3：
//
//
//输入：head = [1,2], n = 1
//输出：[1]
//
//
//
//
// 提示：
//
//
// 链表中结点的数目为 sz
// 1 <= sz <= 30
// 0 <= Node.val <= 100
// 1 <= n <= sz
//
//
//
//
// 进阶：你能尝试使用一趟扫描实现吗？
//
// Related Topics 链表 双指针 👍 2165 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-08-12.
 */
public class _19_删除链表的倒数第N个结点{
    class Solution{
        public ListNode removeNthFromEnd(ListNode head, int n) {
            // 在链表头部接一个虚拟节点 dummy 是为了避免删除倒数第一个元素时出现空指针异常，
            // 在头部加入dummy 节点并不影响倒数第 k 个元素是什么
            ListNode dummy = new ListNode(-1);
            dummy.next = head;
            // 删除到出第 n 个，要找到倒数第 n+1 个节点
            ListNode x = findFromEnd(dummy, n + 1);
            x.next = x.next.next;
            return dummy.next;
        }

        // 返回链表的倒数第 k 个节点
        public ListNode findFromEnd(ListNode head, int k) {
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
            return p2;
        }
    }
}
