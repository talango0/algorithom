package leetcode.arrays;
//给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
// 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
// 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
//
//
// 示例 1:
//输入: [3,2,1,5,6,4], k = 2
//输出: 5
//
//
// 示例 2:
//输入: [3,2,3,1,2,4,5,5,6], k = 4
//输出: 4
//
// 提示：
//
//
// 1 <= k <= nums.length <= 10⁵
// -10⁴ <= nums[i] <= 10⁴
//
//
// Related Topics 数组 分治 快速选择 排序 堆（优先队列） 👍 1936 👎 0

import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-11-02.
 */
public class _215_数组中的第K个最大元素{
    class Solution {
        public int findKthLargest(int[] nums, int k) {
            // 小顶堆，堆顶是最小元素
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (int e : nums) {
                // 每个元素都要过一遍二叉堆
                pq.offer(e);
                // 堆中元素多于 k 个时，删除堆顶元素
                if (pq.size() > k) {
                    pq.poll();
                }
            }
            // pq 中剩下的是 nums 中 k 个最大元素，
            // 堆顶是最小的那个，即第 k 个最大元素
            return pq.peek();
        }
    }
}
