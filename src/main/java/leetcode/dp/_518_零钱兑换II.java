package leetcode.dp;
//给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
//
// 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
//
// 假设每一种面额的硬币有无限个。
//
// 题目数据保证结果符合 32 位带符号整数。
//
//
//
//
//
//
// 示例 1：
//
//
//输入：amount = 5, coins = [1, 2, 5]
//输出：4
//解释：有四种方式可以凑成总金额：
//5=5
//5=2+2+1
//5=2+1+1+1
//5=1+1+1+1+1
//
//
// 示例 2：
//
//
//输入：amount = 3, coins = [2]
//输出：0
//解释：只用面额 2 的硬币不能凑成总金额 3 。
//
//
// 示例 3：
//
//
//输入：amount = 10, coins = [10]
//输出：1
//
//
//
//
// 提示：
//
//
// 1 <= coins.length <= 300
// 1 <= coins[i] <= 5000
// coins 中的所有值 互不相同
// 0 <= amount <= 5000
//
//
// Related Topics 数组 动态规划 👍 894 👎 0

import java.util.HashSet;
import java.util.Set;

/**
 * @see _322_零钱兑换
 */
public class _518_零钱兑换II{


    // 完全背包问题，跟0-1背包的问题最大的区别是每个物品的数量是有限的
    class Solution0{
        int change(int amount, int[] coins) {
            int n = coins.length;
            int[][] dp = new int[n + 1][amount + 1];
            // base case
            for (int i = 0; i <= n; i++) {
                dp[i][0] = 1;
            }

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= amount; j++) {
                    if (j - coins[i - 1] >= 0) {
                        dp[i][j] = dp[i - 1][j]
                                + dp[i][j - coins[i - 1]];
                    }
                    else {
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }
            return dp[n][amount];
        }
    }

    // 时间复杂度 O(N*amount)，空间复杂度 O(amount)。
    class Solution{
        public int change(int amount, int[] coins) {
            int[] dp = new int[amount + 1];
            dp[0] = 1;
            for (int coin : coins) {
                for (int i = coin; i <= amount; i++) {
                    dp[i] += dp[i - coin];
                }
            }
            return dp[amount];
        }
    }

    @Deprecated
    class Solution1{
        private int res = 0;
        Set<Integer> numCoinSet = new HashSet<>();

        public int change(int amount, int[] coins) {

            fun(amount, coins, 0);
            return res;
        }

        private void fun(int amount, int[] coins, int coinCount) {
            if (amount < 0) {
                return;
            }
            else if (amount == 0) {
                if (!numCoinSet.contains(coinCount)) {
                    numCoinSet.add(coinCount);
                    res++;
                }
            }
            else {
                coinCount = coinCount + 1;
                for (int coin : coins) {
                    if (amount < coin) {
                        continue;
                    }
                    fun(amount - coin, coins, coinCount);
                }
            }
        }
    }
}
