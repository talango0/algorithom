package leetcode.dp;
//给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
//
// 子数组 是数组中的一个连续部分。
//
//
//
// 示例 1：
//
//
//输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
//输出：6
//解释：连续子数组[4,-1,2,1] 的和最大，为6 。
//
//
// 示例 2：
//
//
//输入：nums = [1]
//输出：1
//
//
// 示例 3：
//
//
//输入：nums = [5,4,-1,7,8]
//输出：23
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 10⁵
// -10⁴ <= nums[i] <= 10⁴
//
//
//
//
// 进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。
//
// Related Topics 数组 分治 动态规划 👍 5189 👎 0

import leetcode.arrays._918_环形子数组;

/**
 * @author mayanwei
 * @date 2022-08-03.
 * @see _918_环形子数组
 */
public class _53_最大子数组和{
    class Solution {
        /**
         * <pre>
         * 如果遍历所有的子数组组合[i,j]， 然后对每个子数组求和，那会是O(N^3)的时间复杂度，肯定不符合要求。
         *
         * 对于数组上的优化问题，常用方法有双指针和动态规划
         * 这里双指针很难用上，可以用动态规划的思路，寻找递推关系。
         *
         * 我们一般定义 dp 数组是这样的： nums[0..1] 中的最大子数组和 为 dp[i]。
         * 这种情况不能利用数学归纳法根据 dp[i] 推出 dp[i+1],因为子数组一定是连续的，当前的dp数组定义，并不能保证 nums[0..i]
         * 中最大子数组与 nums[i+1] 是相邻的，也就没办法推导出 dp[i+1].
         * 重新定义 dp[i] 表示以 nums[i] 为结尾的最大子数组和。这种定义下，不能直接返回 dp[n-1] 而需要遍历这个dp数组。
         *
         * 选择：要么与前面相邻子数组连接，形成一个更大的子数组；要么不与前面的子数组连接，自成一派，自己作为一个子数组。
         * 如何选择，最大数组和，选择结果更大的那个 dp[i] = Math.max(nums[i], nums[i] + dp[i - 1])。
         *
         * 时间复杂度：O(n) 空间复杂度 O(n)
         * </pre>
         */

        public int maxSubArray(int[] nums) {
            int n = nums.length;
            if (n == 0) {
                return 0;
            }
            // 定义： dp[i] 记录以 nums[i] 为结尾的 最大子数组和
            int [] dp = new int[n];
            // base case
            // 第一个元素前面没有子数组
            dp[0] = nums[0];
            // 状态转移方程
            for (int i = 1; i < n; i++) {
                dp[i] = Math.max( nums[i], nums[i] + dp[i-1] );
            }
            // 得到 nums 的最大子数组
            int res = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                res = Math.max(res, dp[i]);
            }
            return res;
        }
    }

    class Solution1 {
        // 空间优化版本 dp[i] 只以来于前面的一个状态
        public int maxSubArray(int[] nums) {
            int n = nums.length;
            if (n == 0) {
                return 0;
            }
            // base case
            int dp_0 = nums[0];
            int dp_1 = 0, res = dp_0;
            // 状态转移方程
            for (int i = 1; i < n; i++) {
                // dp[i] = Math.max( nums[i], nums[i] + dp[i-1] );
                dp_1 = Math.max(nums[i], nums[i] + dp_0);
                dp_0 = dp_1;
                // 顺便计算最大的结果
                res = Math.max(res, dp_1);
            }
            return res;
        }
    }


    class Solution2 {
        // 前缀和数组 preSum 就是 nums 元素的累加和 preSum[i+1] - preSum[j] 其实就是数组 nums[j..i] 之和.（根据 preSum数组实现，索引0占位符，所以 i 有一位偏移）
        // 反过来，以 nums[i] 为结尾的最大子数组之和是多少？其实就是 preSum[i+1]-preSum[0..i];
        public int maxSubArray(int[] nums) {
            int n = nums.length;
            if (n == 0) {
                return 0;
            }

            int [] preSum = new int[n+1];
            // 构造nums 前缀和
            preSum[0] = 0;
            for (int i = 1; i <= n; i++) {
                preSum[i] = preSum[i-1] + nums[i-1];
            }

            int res = Integer.MIN_VALUE;
            int minVal = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                // 维护 minValVal 是 preSum[0..i] 的最小值
                minVal = Math.min(minVal, preSum[i]);
                // 以 nums[i] 结尾的最大子数组和就是 preSum[i+1] - min(preSum[0..i])
                res = Math.max(res, preSum[i+1]-minVal);
            }
            return res;
        }
    }
}
