package leetcode.程序员面试金典;
//编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。
//
//示例1:
//
// 输入：[1, 2, 3, 3, 2, 1]
// 输出：[1, 2, 3]
//示例2:
//
// 输入：[1, 1, 1, 1, 2]
// 输出：[1, 2]
//提示：
//
//链表长度在[0, 20000]范围内。
//链表元素在[0, 20000]范围内。
//进阶：
//
//如果不得使用临时缓冲区，该怎么解决？
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/remove-duplicate-node-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.list.ListNode;

import java.util.HashSet;

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _02_01_移除重复节点{

    /**
     * 直接迭代访问整个链表，将每个节点加入散列表中。若发现有重复元素，则将该节点从链表中移除，然后继续迭代。
     * 只需扫描上一次链表就搞定，时间复杂度为O(n)
     */
    class Solution {
        public ListNode removeDuplicateNodes(ListNode head) {
            HashSet<Integer> set = new HashSet<>();
            ListNode previous = null;
            ListNode p = head;
            while (p!= null) {
                if (!set.contains(p.val)) {
                    set.add(p.val);
                    previous = p;
                } else {
                    previous.next = p.next;
                }
                p = p.next;
            }
            return head;
        }
    }

    /**
     * 进阶：不用额外的缓冲区，可以用两个指针进行迭代，current迭代访问整个链表，runner用于检查后续节点是否重复
     * 时间复杂度 O(n^2) 空间复杂度 O(1)
     */
    class Solution1 {
        public ListNode removeDuplicateNodes(ListNode head) {
            ListNode current = head;
            while (current != null) {
                // 删除所有其余相同值的节点
                ListNode runner = current;
                while (runner.next != null) {
                    if (runner.next.val == current.val) {
                        runner.next = runner.next.next;
                    }
                    else {
                        runner = runner.next;
                    }
                }
                current = current.next;
            }
            return head;
        }
    }
}
