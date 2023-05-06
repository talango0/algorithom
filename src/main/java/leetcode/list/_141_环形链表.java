package leetcode.list;
//给你一个链表的头节点 head ，判断链表中是否有环。
//
// 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到
//链表中的位置（索引从 0 开始）。注意：pos 不作为参数进行传递 。仅仅是为了标识链表的实际情况。
//
// 如果链表中存在环 ，则返回 true 。 否则，返回 false 。
//
//
//
// 示例 1：
//输入：head = [3,2,0,-4], pos = 1
//输出：true
//解释：链表中有一个环，其尾部连接到第二个节点。
//
//
// 示例 2：
//
//
//
//
//输入：head = [1,2], pos = 0
//输出：true
//解释：链表中有一个环，其尾部连接到第一个节点。
//
//
// 示例 3：
//
//
//
//
//输入：head = [1], pos = -1
//输出：false
//解释：链表中没有环。
//
//
//
//
// 提示：
//
//
// 链表中节点的数目范围是 [0, 10⁴]
// -10⁵ <= Node.val <= 10⁵
// pos 为 -1 或者链表中的一个 有效索引 。
//
//
//
//
// 进阶：你能用 O(1)（即，常量）内存解决此问题吗？
//
// Related Topics 哈希表 链表 双指针 👍 1576 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import leetcode.arrays._373_查找和最小的K对数字;
import leetcode.arrays._378_有序矩阵中第K小的元素;
import leetcode.design._355_设计推特;
import leetcode.math._313_超级丑数;

/**
 * @author mayanwei
 * @date 2022-08-12.
 * @see _141_环形链表
 * @see _142_环形链表II
 * @see _160_相交链表
 * @see _19_删除链表的倒数第N个结点
 * @see _21_合并两个有序链表
 * @see _313_超级丑数
 * @see _355_设计推特
 * @see _378_有序矩阵中第K小的元素
 * @see _86_分隔链表
 * @see _876_链表的中间结点
 * @see _23_合并K个升序链表
 * @see _373_查找和最小的K对数字
 */
public class _141_环形链表{
    public class Solution {
        public boolean hasCycle(ListNode head) {
            if ( head == null ) {
                return false;
            }
            ListNode fast = head, slow  = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast) {
                    return true;
                }
            }
            return false;
        }
    }
}
