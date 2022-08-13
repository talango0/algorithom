package leetcode.list;
//将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
//
//
//
// 示例 1：
//
//
//输入：l1 = [1,2,4], l2 = [1,3,4]
//输出：[1,1,2,3,4,4]
//
//
// 示例 2：
//
//
//输入：l1 = [], l2 = []
//输出：[]
//
//
// 示例 3：
//
//
//输入：l1 = [], l2 = [0]
//输出：[0]
//
//
//
//
// 提示：
//
//
// 两个链表的节点数目范围是 [0, 50]
// -100 <= Node.val <= 100
// l1 和 l2 均按 非递减顺序 排列
//
//
// Related Topics 递归 链表 👍 2600 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-08-13.
 */
public class _21_合并两个有序链表{
    class Solution{
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            //虚拟头结点
            ListNode dummy = new ListNode(Integer.MIN_VALUE), p = dummy;
            ListNode p1 = list1, p2 = list2;
            while (p1 != null && p2 != null) {
                //  比较 p1 和 p2 两个指针
                //  将值较小的节点接到 P 指针
                if (p1.val > p2.val) {
                    p.next = p2;
                    p2 = p2.next;
                }
                else {
                    p.next = p1;
                    p1 = p1.next;
                }
                p = p.next;
            }

            if (p1 != null) {
                p.next = p1;
            }

            if (p2 != null) {
                p.next = p2;
            }
            return dummy.next;
        }
    }
}
