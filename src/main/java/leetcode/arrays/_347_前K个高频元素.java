package leetcode.arrays;
//给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
//
//
//
// 示例 1:
//
//
//输入: nums = [1,1,1,2,2,3], k = 2
//输出: [1,2]
//
//
// 示例 2:
//
//
//输入: nums = [1], k = 1
//输出: [1]
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 10⁵
// k 的取值范围是 [1, 数组中不相同的元素的个数]
// 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的
//
//
//
//
// 进阶：你所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。
//
// Related Topics 数组 哈希表 分治 桶排序 计数 快速选择 排序 堆（优先队列） 👍 1330 👎 0

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-09-25.
 */
public class _347_前K个高频元素{
    /**
     * 利用堆的思想，建立一个小顶堆，然后遍历出现次数数组
     * 1. 如果堆的元素个数小于 k，就可以直接插入堆中。
     * 2. 如果堆的元素个数等于 k， 则检查堆顶于当前出现次数的大小，如果堆顶更大，说明至少有k个数子的出现次数比当前值大，故舍弃当前值，否则，就弹出堆顶，并将当前值插入堆中。
     * 遍历完成后，堆中的元素就代表了出现次数数组中前k大的值。
     */
    class Solution{
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> occurences = new HashMap<Integer, Integer>();
            for (int num : nums) {
                occurences.put(num, occurences.getOrDefault(num, 0) + 1);
            }
            // int [] 的第一个元素代表数组的值，第二个元素代表了该值出现的次数。
            PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>(){
                @Override
                public int compare(int[] m, int[] n) {
                    return m[1] - n[1];
                }
            });
            for (Map.Entry<Integer, Integer> entry : occurences.entrySet()) {
                int num = entry.getKey(), count = entry.getValue();
                if (queue.size() == k) {
                    if (queue.peek()[1] < count) {
                        queue.poll();
                        queue.offer(new int[]{num, count});
                    }
                }
                else {
                    queue.offer(new int[]{num, count});
                }
            }
            int[] ret = new int[k];
            for (int i = 0; i < k; i++) {
                ret[i] = queue.poll()[0];
            }
            return ret;
        }
    }

    //用计数排序的方法解决这道题
    class Solution2{
        public int[] topKFrequent(int[] nums, int k) {
            // nums 中的元素 -> 该元素出现的频率
            HashMap<Integer, Integer> valToFreq = new HashMap<>();
            for (int v : nums) {
                valToFreq.put(v, valToFreq.getOrDefault(v, 0) + 1);
            }

            // 频率 -> 这个频率有哪些元素
            ArrayList<Integer>[] freqToVals = new ArrayList[nums.length + 1];
            for (int val : valToFreq.keySet()) {
                int freq = valToFreq.get(val);
                if (freqToVals[freq] == null) {
                    freqToVals[freq] = new ArrayList<>();
                }
                freqToVals[freq].add(val);
            }

            int[] res = new int[k];
            int p = 0;
            // freqToVals 从后往前存储着出现最多的元素
            for (int i = freqToVals.length - 1; i > 0; i--) {
                ArrayList<Integer> valList = freqToVals[i];
                if (valList == null) {
                    continue;
                }
                for (int j = 0; j < valList.size(); j++) {
                    // 将出现次数最多的 k 个元素装入 res
                    res[p] = valList.get(j);
                    p++;
                    if (p == k) {
                        return res;
                    }
                }
            }

            return null;
        }
    }
}