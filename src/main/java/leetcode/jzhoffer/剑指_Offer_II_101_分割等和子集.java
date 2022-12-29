package leetcode.jzhoffer;
//给定一个非空的正整数数组 nums ，请判断能否将这些数字分成元素和相等的两部分。
//
//
//
// 示例 1：
//
//
//输入：nums = [1,5,11,5]
//输出：true
//解释：nums 可以分割成 [1, 5, 5] 和 [11] 。
//
// 示例 2：
//
//
//输入：nums = [1,2,3,5]
//输出：false
//解释：nums 不可以分为和相等的两部分
//
//
//
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 200
// 1 <= nums[i] <= 100
//
//
//
//
//
// 注意：本题与主站 416 题相同： https://leetcode-cn.com/problems/partition-equal-subset-
//sum/
//
// Related Topics 数学 字符串 模拟 👍 66 👎 0

import leetcode.dp._416_分割等和子集;
import leetcode.dp._518_零钱兑换II;

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _416_分割等和子集
 * @see _518_零钱兑换II
 */
public class 剑指_Offer_II_101_分割等和子集{
    class Solution{
        public boolean canPartition(int[] nums) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            // 和为奇数时，不可能分成两个和相等的集合
            if (sum % 2 != 0) {
                return false;
            }
            int n = nums.length;
            sum /= 2;
            // dp 数组定义：dp[i][j] = x 表示，对于前 i 个物品，当前背包的容量为 j 时，
            // 若 x 为true，则说明可以恰好将背包装满，
            // 若 x 为false，则说明不能恰好将背包装满
            boolean[][] dp = new boolean[n + 1][sum + 1];
            // base case
            for (int i = 0; i <= n; i++) {
                dp[i][0] = true;
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= sum; j++) {
                    // 背包容量不足，只能选择不装
                    if (j < nums[i - 1]) {
                        dp[i][j] = dp[i - 1][j];
                    }
                    // 装入或者不装入
                    else {
                        dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                    }
                }
            }
            return dp[n][sum];
        }
    }

    class Solution2{
        boolean[] visited;

        public boolean canPartition(int[] nums) {
            int n = nums.length;
            int sum = 0;
            int max = 0;
            for (int i = 0; i < n; i++) {
                sum += nums[i];
                max = Math.max(max, nums[i]);
            }
            if (sum % 2 != 0 || max > sum / 2) {
                return false;
            }
            visited = new boolean[sum / 2 + 1];
            return backTrack(nums, 0, sum / 2);
        }

        public boolean backTrack(int[] nums, int idx, int target) {
            if (idx > nums.length || target < 0 || visited[target]) {
                return false;
            }
            if (target == 0) {
                return true;
            }

            if (idx < nums.length) {
                if (backTrack(nums, idx + 1, target - nums[idx])
                        || backTrack(nums, idx + 1, target)) {
                    return true;
                }
                visited[target] = true;
            }
            return false;
        }
    }
}
