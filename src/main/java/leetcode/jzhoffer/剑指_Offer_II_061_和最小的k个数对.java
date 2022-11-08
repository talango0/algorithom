package leetcode.jzhoffer;
//给定两个以升序排列的整数数组 nums1 和 nums2 , 以及一个整数 k 。
//
// 定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。
//
// 请找到和最小的 k 个数对 (u1,v1), (u2,v2) ... (uk,vk) 。
//
//
//
// 示例 1:
//
//
//输入: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
//输出: [1,2],[1,4],[1,6]
//解释: 返回序列中的前 3 对数：
//    [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
//
//
// 示例 2:
//
//
//输入: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
//输出: [1,1],[1,1]
//解释: 返回序列中的前 2 对数：
//     [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
//
//
// 示例 3:
//
//
//输入: nums1 = [1,2], nums2 = [3], k = 3
//输出: [1,3],[2,3]
//解释: 也可能序列中所有的数对都被返回:[1,3],[2,3]
//
//
//
//
// 提示:
//
//
// 1 <= nums1.length, nums2.length <= 10⁴
// -10⁹ <= nums1[i], nums2[i] <= 10⁹
// nums1, nums2 均为升序排列
// 1 <= k <= 1000
//
//
//
//
//
// 注意：本题与主站 373 题相同：https://leetcode-cn.com/problems/find-k-pairs-with-smallest-
//sums/
//
// Related Topics 数组 堆（优先队列） 👍 51 👎 0

import leetcode.arrays._373_查找和最小的K对数字;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-11-03.
 * @see _373_查找和最小的K对数字
 */
public class 剑指_Offer_II_061_和最小的k个数对{
    class Solution{
        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            // 三元组 (num1[i], num2[i], i),
            // i 记录nums2 元素的索引位置，用于生产下一个节点
            PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> {
                return (a[0] + a[1]) - (b[0] + b[1]);
            });
            // 初始化优先级队列
            for (int i = 0; i < nums1.length; i++) {
                pq.offer(new int[]{nums1[i], nums2[0], 0});
            }
            List<List<Integer>> res = new ArrayList<>();
            while (!pq.isEmpty() && k > 0) {
                int[] cur = pq.poll();
                k--;
                int nextIndex = cur[2] + 1;
                if (nextIndex < nums2.length) {
                    pq.offer(new int[]{cur[0], nums2[nextIndex], nextIndex});
                }
                List<Integer> pair = new ArrayList<>();
                pair.add(cur[0]);
                pair.add(cur[1]);
                res.add(pair);
            }
            return res;
        }
    }
}
