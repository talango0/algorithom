package leetcode.list;
//给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链
//表节点，返回 反转后的链表 。
//
//
//
// 示例 1：
//
//
//输入：head = [1,2,3,4,5], left = 2, right = 4
//输出：[1,4,3,2,5]
//
//
// 示例 2：
//
//
//输入：head = [5], left = 1, right = 1
//输出：[5]
//
//
//
//
// 提示：
//
//
// 链表中节点数目为 n
// 1 <= n <= 500
// -500 <= Node.val <= 500
// 1 <= left <= right <= n
//
//
//
//
// 进阶： 你可以使用一趟扫描完成反转吗？
//
// Related Topics 链表 👍 1365 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-08-12.
 * @see _206_反转链表
 */
public class _92_反转链表II{

    class Solution{
        // 反转区间 [m,n] (索引从1开始)，仅仅反转区间中的链表元素
        public ListNode reverseBetween(ListNode head, int m, int n) {
            // base case
            if (m == 1) {
                return reverse(head, n);
            }
            // 如果 m != 1 怎么办？
            // 如果我们把 head 的索引视为 1，那么我们是想从第 m 个元素开始反转对吧；
            // 如果把 head.next 的索引视为 1 呢？
            // 那么相对于 head.next，反转的区间应该是从第 m - 1 个元素开始的；
            // 那么对于 head.next.next 呢……
            // 前进到反转的起点触发 base case
            head.next = reverseBetween(head.next, m - 1, n - 1);
            return head;
        }

        // 这个地方和 反转整个链表有区别
        ListNode successor = null; // 后驱节点

        // 翻转链表的前 n(n<=链表长度) 个节点
        ListNode reverse(ListNode head, int n) {
            if (n == 1) {
                successor = head.next;
                return head;
            }
            // 以 head.next 为起点，需要反转前 n-1 个节点
            ListNode last = reverse(head.next, n - 1);
            head.next.next = head;
            //让反转后的head 节点
            head.next = successor;
            return last;
        }
    }

}
