package leetcode.backtracing;
//给你一个整数数组 nums 和一个整数 target 。
//
// 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
// 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
// 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
//
// 示例 1：
//输入：nums = [1,1,1,1,1], target = 3
//输出：5
//解释：一共有 5 种方法让最终目标和为 3 。
//-1 + 1 + 1 + 1 + 1 = 3
//+1 - 1 + 1 + 1 + 1 = 3
//+1 + 1 - 1 + 1 + 1 = 3
//+1 + 1 + 1 - 1 + 1 = 3
//+1 + 1 + 1 + 1 - 1 = 3
//
//
// 示例 2：
//输入：nums = [1], target = 1
//输出：1
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 20
// 0 <= nums[i] <= 1000
// 0 <= sum(nums[i]) <= 1000
// -1000 <= target <= 1000
//
//
// Related Topics 数组 动态规划 回溯 👍 1326 👎 0

import leetcode.dp._518_零钱兑换II;

import java.util.HashMap;

/**
 * @author mayanwei
 * @date 2022-08-03.
 * @see _518_零钱兑换II
 */
public class _494_目标和{

    /**
     * <pre>
     * {@code
     * def backtrack(路径, 选择列表):
     *     if 满足结束条件:
     *         result.add(路径)
     *         return
     *
     *     for 选择 in 选择列表:
     *         做选择
     *         backtrack(路径, 选择列表)
     *         撤销选择
     * }
     * 到具体问题：关键就是搞清楚什么是「选择」，而对于这道题，「选择」不是明摆着的吗？对于每个数字 nums[i]，
     * 我们可以选择给一个正号 + 或者一个负号 -，然后利用回溯模板穷举出来所有可能的结果，数一数到底有几种组合能够凑出 target 不就行了嘛？
     * {@code
     * def backtrack(nums, i):
     *     if i == len(nums):
     *         if 达到 target:
     *             result += 1
     *         return
     *
     *     for op in { +1, -1 }:
     *         选择 op * nums[i]
     *         # 穷举 nums[i + 1] 的选择
     *         backtrack(nums, i + 1)
     *         撤销选择
     * }
     *
     * </pre>
     */
    // O(2^n)
    class Solution{
        int res = 0;

        public int findTargetSumWays(int[] nums, int target) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            backTrace(nums, 0, target);
            return res;
        }

        public void backTrace(int[] nums, int i, int remain) {
            // base case
            if (i == nums.length) {
                if (remain == 0) {
                    // 说明恰好凑出了 target
                    res++;
                }
                return;
            }

            // 给 numss[i] 选择 '+'
            remain += nums[i];
            backTrace(nums, i + 1, remain);
            // 撤销选择
            remain -= nums[i];

            // 给 nums[i] 选择 '-'
            remain -= nums[i];
            backTrace(nums, i + 1, remain);
            // 撤销选择
            remain += nums[i];
        }
    }


    //利用备忘录减少重叠子问题
    class Solution2{
        public int findTargetSumWays(int[] nums, int target) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            return dp(nums, 0, target);
        }

        HashMap<String, Integer> memo = new HashMap<>();

        private int dp(int[] nums, int i, int remain) {
            // base case
            if (i == nums.length) {
                if (remain == 0) {
                    return 1;
                }
                return 0;
            }

            String key = i + "," + remain;
            // 避免重复计算
            if (memo.containsKey(key)) {
                return memo.get(key);
            }
            // 穷举
            int res = dp(nums, i + 1, remain - nums[i]) +
                    dp(nums, i + 1, remain + nums[i]);

            // 计入memo
            memo.put(key, res);
            return res;
        }


        // 利用动态规划求解
        // 转化为背包问题
        class Solution{
            // sum(A) - sum(B) = target
            // sum(A) = target + sum(B)
            // sum(A) + sum(A) = target + sum(B) + sum(A)
            // 2 * sum(A) = target + sum(nums)
            // 因此，可以推出 sum(A) = (target + sum(nums)) / 2，也就是把原问题转化成：nums 中存在几个子集 A，
            // 使得 A 中元素的和为 (target + sum(nums)) / 2
            // 有一个背包，容量为 sum，现在给你 N 个物品，第 i 个物品的重量为 nums[i - 1]（注意 1 <= i <= N），
            // 每个物品只有一个，请问你有几种不同的方法能够恰好装满这个背包

            // 类似的子集划分问题就是 经典背包问题：子集划分，现在实现这么一个函数：
            /* 计算 nums 中有几个子集的和为 sum */
            int subsets(int[] nums, int sum) {
                int n = nums.length;
                int[][] dp = new int[n + 1][sum + 1];
                // base case
                // 可能有些看过前文 0-1 背包问题 和 完全背包问题 这两篇背包问题的文章之后会有疑问，
                // 为什么 base case 不是 dp[..][0] = 1 呢？即背包容量为 0 时，只有「什么都不装」这一种装法。
                // 这里不能这样初始化，是因为本题 nums 数组中的元素是可能为 0 的，那么背包容量为 0 时，
                // 「什么都不装」可能就不是唯一的装法了，而需要在状态转移的过程中具体去计算。
                dp[0][0] = 1;
                for (int i = 1; i <= n; i++) {
                    for (int j = 0; j <= sum; j++) {
                        if (j >= nums[i - 1]) {
                            // 两种选择的结果之和
                            dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i - 1]];
                        }
                        else {
                            // 背包空间不足
                            dp[i][j] = dp[i - 1][j];
                        }
                    }
                }
                return dp[n][sum];
            }

            public int findTargetSumWays(int[] nums, int target) {
                if (nums == null || nums.length == 0) {
                    return 0;
                }
                int sum = 0;
                for (int n : nums) {
                    sum += n;
                }

                //这两种情况，不可能存在合法的子集划分
                if (sum < Math.abs(target) || (sum + target) % 2 == 1) {
                    return 0;
                }
                return subsets(nums, (sum + target) / 2);
            }
        }

        class Solution1{
            int subsets(int[] nums, int sum) {
                int n = nums.length;
                int[] dp = new int[sum + 1];
                // base case
                dp[0] = 1;
                for (int i = 1; i <= n; i++) {
                    //因为二维压缩到一维的根本原理是，dp[j] 和 dp[j-nums[i-1]] 还没被新结果覆盖的时候，
                    // 相当于二维 dp 中的 dp[i-1][j] 和 dp[i-1][j-nums[i-1]]。
                    //那么，我们就要做到：在计算新的 dp[j] 的时候，dp[j] 和 dp[j-nums[i-1]] 还是上一轮外层 for 循环的结果。
                    //如果你从前往后遍历一维 dp 数组，dp[j] 显然是没问题的，但是 dp[j-nums[i-1]] 已经不是上一轮外层 for 循环的结果了，
                    // 这里就会使用错误的状态，当然得不到正确的答案。
                    for (int j = sum; j >= 0; j--) {
                        if (j >= nums[i - 1]) {
                            // 两种选择的结果之和
                            dp[j] = dp[j] + dp[j - nums[i - 1]];
                        }
                        else {
                            // 背包空间不足
                            dp[j] = dp[j];
                        }
                    }
                }
                return dp[sum];
            }

            public int findTargetSumWays(int[] nums, int target) {
                if (nums == null || nums.length == 0) {
                    return 0;
                }
                int sum = 0;
                for (int n : nums) {
                    sum += n;
                }

                //这两种情况，不可能存在合法的子集划分
                if (sum < Math.abs(target) || (sum + target) % 2 == 1) {
                    return 0;
                }
                return subsets(nums, (sum + target) / 2);
            }
        }
    }
}
