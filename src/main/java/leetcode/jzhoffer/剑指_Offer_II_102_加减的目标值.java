package leetcode.jzhoffer;

import leetcode.backtracing._494_目标和;

import java.util.HashMap;

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _494_目标和
 */
public class 剑指_Offer_II_102_加减的目标值{
    class Solution{
        /**
         * 对于每一个1，要么正号，要么负号，把所有的情况枚举出来，即可得到计算结果
         */
        public int findTargetSumWays(int[] nums, int target) {
            if (nums.length == 0) {
                return 0;
            }
            return dp(nums, 0, target);
        }

        // 备忘录
        HashMap<String, Integer> memo = new HashMap<>();

        int dp(int[] nums, int i, int remain) {
            // base case
            if (i == nums.length) {
                if (remain == 0) {
                    return 1;
                }
                return 0;
            }
            // 把它俩转成字符串
            String key = i + "," + remain;
            if (memo.containsKey(key)) {
                return memo.get(key);
            }
            // 还是穷举
            int result = dp(nums, i + 1, remain - nums[i]) +
                    dp(nums, i + 1, remain + nums[i]);
            // 计入备忘录
            memo.put(key, result);
            return result;
        }

    }
}
