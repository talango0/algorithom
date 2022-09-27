package leetcode.arrays;

import leetcode.tree._437_路径总和III;

import java.util.HashMap;
import java.util.Map;

//给你一个整数数组 nums 和一个整数k ，请你统计并返回 该数组中和为k的连续子数组的个数。
//
//
//
//示例 1：
//
//输入：nums = [1,1,1], k = 2
//输出：2
//示例 2：
//
//输入：nums = [1,2,3], k = 3
//输出：2
//
//
//提示：
//
//1 <= nums.length <= 2 * 104
//-1000 <= nums[i] <= 1000
//-107 <= k <= 107
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/subarray-sum-equals-k
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @see _303_区域和检索_数组不可变
 * @see _437_路径总和III
 */
public class _560_和为K的子数组 {

    class Solution {
        public int subarraySum(int[] nums, int k) {
            int[] preSum = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                preSum[i + 1] = preSum[i] + nums[i];
            }

            HashMap<Integer, Integer> map = new HashMap<>();
            int ans = 0;
            for (int i = 0; i < preSum.length; i++) {
                ans += map.getOrDefault(preSum[i] - k, 0);
                map.put(preSum[i], map.getOrDefault(preSum[i], 0) + 1);
            }
            return ans;
        }
    }


    class Solution2 {
        public int subarraySum(int[] nums, int k) {
            if (nums == null) {
                return 0;
            }

            // 前缀和
            // 含义 前缀和为 preSum 的连续数组个数
            Map<Long, Integer> preSumCount = new HashMap<>();
            preSumCount.put(0l, 1);
            long preSum = 0;
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                preSum += (long) nums[i];
                if (preSumCount.containsKey(preSum - k)) {
                    count += preSumCount.get(preSum - k);
                }
                preSumCount.put(preSum, preSumCount.getOrDefault(preSum, 0) + 1);
            }
            return count;
        }
    }
}
