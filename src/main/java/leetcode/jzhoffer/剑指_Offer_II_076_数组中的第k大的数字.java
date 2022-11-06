package leetcode.jzhoffer;

import leetcode.arrays._215_数组中的第K个最大元素;

import java.util.PriorityQueue;
//给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
//
// 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
//
//
//
// 示例 1:
//
//
//输入: [3,2,1,5,6,4] 和 k = 2
//输出: 5
//
//
// 示例 2:
//
//
//输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
//输出: 4
//
//
//
// 提示：
//
//
// 1 <= k <= nums.length <= 10⁴
// -10⁴ <= nums[i] <= 10⁴
//
//
//
//
//
// 注意：本题与主站 215 题相同： https://leetcode-cn.com/problems/kth-largest-element-in-an-
//array/
//
// Related Topics 数组 分治 快速选择 排序 堆（优先队列） 👍 52 👎 0
/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _215_数组中的第K个最大元素
 */
public class 剑指_Offer_II_076_数组中的第k大的数字{
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
