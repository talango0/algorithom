package leetcode.arrays;

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
         * nums1 = [1,7,11], nums2 = [2,4,6]
         * 组合出的所有数对儿这就可以抽象成三个有序链表：
         * [1, 2] -> [1, 4] -> [1, 6]
         * [7, 2] -> [7, 4] -> [7, 6]
         * [11, 2] -> [11, 4] -> [11, 6]
         * 这三个链表中每个元素（数对之和）是递增的，所以就可以按照 23. 合并K个升序链表 的思路来合并，取出前 k 个作为答案即可。
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
