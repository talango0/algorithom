package leetcode.tree;
//设计一个找到数据流中第 k 大元素的类（class）。注意是排序后的第 k 大元素，不是第 k 个不同的元素。
//
// 请实现 KthLargest 类：
//
//
// KthLargest(int k, int[] nums) 使用整数 k 和整数流 nums 初始化对象。
// int add(int val) 将 val 插入数据流 nums 后，返回当前数据流中第 k 大的元素。
//
//
//
//
// 示例：
//
//
//输入：
//["KthLargest", "add", "add", "add", "add", "add"]
//[[3, [4, 5, 8, 2]], [3], [5], [10], [9], [4]]
//输出：
//[null, 4, 5, 5, 8, 8]
//
//解释：
//KthLargest kthLargest = new KthLargest(3, [4, 5, 8, 2]);
//kthLargest.add(3);   // return 4
//kthLargest.add(5);   // return 5
//kthLargest.add(10);  // return 5
//kthLargest.add(9);   // return 8
//kthLargest.add(4);   // return 8
//
//
//
//提示：
//
//
// 1 <= k <= 10⁴
// 0 <= nums.length <= 10⁴
// -10⁴ <= nums[i] <= 10⁴
// -10⁴ <= val <= 10⁴
// 最多调用 add 方法 10⁴ 次
// 题目数据保证，在查找第 k 大元素时，数组中至少有 k 个元素
//
//
// Related Topics 树 设计 二叉搜索树 二叉树 数据流 堆（优先队列） 👍 393 👎 0

import leetcode.arrays._215_数组中的第K个最大元素;

import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-11-02.
 * @see _215_数组中的第K个最大元素
 */
public class _703_数据流中的第K大元素{
    class KthLargest{
        int k;
        // 默认小顶堆
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();

        public KthLargest(int k, int[] nums) {
            // 将nums 装入小顶堆，保留前k大的元素
            for (int e : nums) {
                pq.offer(e);
                if (pq.size() > k) {
                    pq.poll();
                }
            }
            this.k = k;
        }

        public int add(int val) {
            // 维护小顶堆只保留前 k 大的元素
            pq.offer(val);
            if (pq.size() > k) {
                pq.poll();
            }
            // 堆顶就是第k 大元素（倒数第k小的元素）
            return pq.peek();
        }
    }

}
