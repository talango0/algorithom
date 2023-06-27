package leetcode.程序员面试金典;
//给定一个链表，如果它是有环链表，实现一个算法返回环路的开头节点。若环不存在，请返回 null。
//
//如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，
// 我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
// 注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
//
//
//
//示例 1：
//
//
//
//输入：head = [3,2,0,-4], pos = 1
//输出：tail connects to node index 1
//解释：链表中有一个环，其尾部连接到第二个节点。
//示例 2：
//
//
//
//输入：head = [1,2], pos = 0
//输出：tail connects to node index 0
//解释：链表中有一个环，其尾部连接到第一个节点。
//示例 3：
//
//
//
//输入：head = [1], pos = -1
//输出：no cycle
//解释：链表中没有环。
//
//
//进阶：
//
//你是否可以不用额外空间解决此题？
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/linked-list-cycle-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.list.ListNode;

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _02_08_环路检测{
    public class Solution{
        public ListNode detectCycle(ListNode head) {
            ListNode fast, slow;
            fast = slow = head;
            while (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
                if (fast == slow) {
                    break;
                }
            }
            // 上面的代码类似 hasCycle 函数
            if (fast == null || fast.next == null) {
                // fast 遇到空指针说明没有环
                return null;
            }
            // 重新指向头结点
            slow = head;
            // 快慢指针同步前进，相交点就是环起点
            while (slow != fast) {
                fast = fast.next;
                slow = slow.next;
            }
            return slow;
        }
    }
}
