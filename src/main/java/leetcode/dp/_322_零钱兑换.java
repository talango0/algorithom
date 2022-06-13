package leetcode.dp;
//给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
//
// 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
//
// 你可以认为每种硬币的数量是无限的。
//
//
//
// 示例 1：
//
//
//输入：coins = [1, 2, 5], amount = 11
//输出：3
//解释：11 = 5 + 5 + 1
//
// 示例 2：
//
//
//输入：coins = [2], amount = 3
//输出：-1
//
// 示例 3：
//
//
//输入：coins = [1], amount = 0
//输出：0
//
//
//
//
// 提示：
//
//
// 1 <= coins.length <= 12
// 1 <= coins[i] <= 2³¹ - 1
// 0 <= amount <= 10⁴
//
// Related Topics 广度优先搜索 数组 动态规划 👍 1980 👎 0


import java.util.Arrays;

//总结：先确定最优子问题，并状态转移方程，然后群举，然后根据备忘录进行优化
public class _322_零钱兑换 {
    // 1. 先确定状态
    // 2. 然后确定 dp 函数的定义
    // 3. 然后确定 选择 并择优

    /**
     * dp[n] = 0 if n = 0
     * dp[n] = -1 if n < 0
     * dp[n] = min{dp[n-coin]+1, dp[n]} coin in coins, n > 0
     */
    class Solution {
        public int coinChange(int[] coins, int amount) {
            //初始化数组，并赋值为 amount+1（表示无群大，因为最多amount个，均由1组成）
            int [] dp = new int[amount+1];
            Arrays.fill(dp, amount+1);
            dp[0] = 0;
            for (int i=1; i<= amount; i++) {
                for (int coin : coins) {
                    if (i<coin) {continue;}
                    dp[i] = Math.min(dp[i], 1+dp[i-coin]);
                }
            }
            return dp[amount] == amount+1 ? -1 : dp[amount];
        }
    }
}
