package leetcode.arrays;
//给定两个以 升序排列 的整数数组 nums1 和 nums2,以及一个整数 k。
//
//定义一对值(u,v)，其中第一个元素来自nums1，第二个元素来自 nums2。
//
//请找到和最小的 k个数对(u1,v1), (u2,v2) ... (uk,vk)。
//
//
//
//示例 1:
//输入: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
//输出: [1,2],[1,4],[1,6]
//解释: 返回序列中的前 3 对数：
//     [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
//
//示例 2:
//输入: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
//输出: [1,1],[1,1]
//解释: 返回序列中的前 2 对数：
//    [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
//
//示例 3:
//输入: nums1 = [1,2], nums2 = [3], k = 3
//输出: [1,3],[2,3]
//解释: 也可能序列中所有的数对都被返回:[1,3],[2,3]
//
//
//提示:
//
//1 <= nums1.length, nums2.length <= 105
//-109 <= nums1[i], nums2[i] <= 109
//nums1 和 nums2 均为升序排列
//1 <= k <= 1000

import leetcode.list._23_合并K个升序链表;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-10-10.
 * @see _23_合并K个升序链表
 */
public class _373_查找和最小的K对数字{
    class Solution{
        /**
         * <pre>
         * nums1 = [1,7,11], nums2 = [2,4,6]
         * 组合出的所有数对儿这就可以抽象成三个有序链表：
         * [1, 2] -> [1, 4] -> [1, 6]
         * [7, 2] -> [7, 4] -> [7, 6]
         * [11, 2] -> [11, 4] -> [11, 6]
         * 这三个链表中每个元素（数对之和）是递增的，所以就可以按照 23. 合并K个升序链表 的思路来合并，取出前 k 个作为答案即可。
         * </pre>
         */
        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            // 存储三元组 (num1[i], num2[i], i)
            // i 记录nums2 元素的索引位置，用于生产下一个节点
            PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> {
                // 按照数组对的元素和升序排序
                return (a[0] + a[1]) - (b[0] + b[1]);
            });
            // 初始化优先级队列
            for (int i = 0; i < nums1.length; i++) {
                pq.offer(new int[]{nums1[i], nums2[0], 0});
            }
            List<List<Integer>> res = new ArrayList<>();
            // 执行合并多个有序链表的逻辑
            while (!pq.isEmpty() && k > 0) {
                int[] cur = pq.poll();
                k--;
                // 链表中的下一个节点加入优先级队列
                int next_index = cur[2] + 1;
                if (next_index < nums2.length) {
                    pq.add(new int[]{cur[0], nums2[next_index], next_index});
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
