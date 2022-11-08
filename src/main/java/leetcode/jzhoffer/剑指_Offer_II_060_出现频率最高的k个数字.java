package leetcode.jzhoffer;
//给定一个整数数组 nums 和一个整数 k ，请返回其中出现频率前 k 高的元素。可以按 任意顺序 返回答案。
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
// 进阶：所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。
//
//
//
//
// 注意：本题与主站 347 题相同：https://leetcode-cn.com/problems/top-k-frequent-elements/
//
// Related Topics 数组 哈希表 分治 桶排序 计数 快速选择 排序 堆（优先队列） 👍 41 👎 0

import leetcode.arrays._347_前K个高频元素;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author mayanwei
 * @date 2022-11-03.
 * @see _347_前K个高频元素
 */
public class 剑指_Offer_II_060_出现频率最高的k个数字{
    class Solution {
        public int[] topKFrequent(int[] nums, int k) {

            Arrays.sort(nums);
            Queue<int[]> queue = new PriorityQueue<>((a, b)->{
                return b[1] - a[1];
            });

            for(int i = 0; i < nums.length; ){
                int j = i;
                while(j < nums.length && nums[i] == nums[j]) {
                    j++;
                }
                queue.offer(new int[]{nums[i], j - i});
                i = j;
            }

            int[] result = new int[k];
            while(k-- != 0){
                result[k] = queue.poll()[0];
            }

            return result;
        }
    }
}
