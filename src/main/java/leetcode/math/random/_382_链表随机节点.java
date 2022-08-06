package leetcode.math.random;
//给你一个单链表，随机选择链表的一个节点，并返回相应的节点值。每个节点 被选中的概率一样 。
//
// 实现 Solution 类：
//
//
// Solution(ListNode head) 使用整数数组初始化对象。
// int getRandom() 从链表中随机选择一个节点并返回该节点的值。链表中所有节点被选中的概率相等。
//
//
//
//
// 示例：
//
//
//输入
//["Solution", "getRandom", "getRandom", "getRandom", "getRandom", "getRandom"]
//[[[1, 2, 3]], [], [], [], [], []]
//输出
//[null, 1, 3, 2, 2, 3]
//
//
//解释
//Solution solution = new Solution([1, 2, 3]);
//solution.getRandom(); // 返回 1
//solution.getRandom(); // 返回 3
//solution.getRandom(); // 返回 2
//solution.getRandom(); // 返回 2
//solution.getRandom(); // 返回 3
//// getRandom() 方法应随机返回 1、2、3中的一个，每个元素被返回的概率相等。
//
//
//
// 提示：
//
//
// 链表中的节点数在范围 [1, 10⁴] 内
// -10⁴ <= Node.val <= 10⁴
// 至多调用 getRandom 方法 10⁴ 次
//
//
//
//
// 进阶：
//
//
// 如果链表非常大且长度未知，该怎么处理？
// 你能否在不使用额外空间的情况下解决此问题？
//
//
// Related Topics 水塘抽样 链表 数学 随机化 👍 305 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import leetcode.arrays._528_按权重随机选择;
import leetcode.arrays.random.searchremove._380_常数时间插入删除和获取随机元素;
import leetcode.arrays.random.searchremove._710_黑名单中的随机数;

import java.util.Random;

/**
 * @see _380_常数时间插入删除和获取随机元素
 * @see _710_黑名单中的随机数
 * @see _528_按权重随机选择
 * @see _398_随机数索引
 * @author mayanwei
 * @date 2022-08-06.
 */
public class _382_链表随机节点{
    /**
     * Definition for singly-linked list.
     */
    class ListNode{
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

    class Solution{
        ListNode head;

        public Solution(ListNode head) {
            this.head = head;
        }

        public int getRandom() {
            Random r = new Random();
            int i = 0, res = 0;
            ListNode p = head;
            // while 循环遍历链表
            while (p != null) {
                i++;
                // 生成一个[0,i)之间的整数
                // 这个整数等于 0 的概率就是 1/i
                if (0 == r.nextInt(i)) {
                    res = p.val;
                }
                p = p.next;
            }
            return res;
        }
    }


    /* 返回链表中 k 个随机节点的值 */
    int[] getRandom(ListNode head, int k) {
        Random r = new Random();
        int[] res = new int[k];
        ListNode p = head;

        // 前 k 个元素先默认选上
        for (int j = 0; j < k && p != null; j++) {
            res[j] = p.val;
            p = p.next;
        }

        int i = k;
        // while 循环遍历链表
        while (p != null) {
            // 生成一个 [0, i) 之间的整数
            int j = r.nextInt(++i);
            // 这个整数小于 k 的概率就是 k/i
            if (j < k) {
                res[j] = p.val;
            }
            p = p.next;
        }
        return res;
    }

}
