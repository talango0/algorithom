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

/**
 * @see _518_零钱兑换II
 */
public class _322_零钱兑换{
    // 1. 先确定状态
    // 2. 然后确定 dp 函数的定义
    // 3. 然后确定 选择 并择优

    /**
     * dp[n] = 0 if n = 0
     * dp[n] = -1 if n < 0
     * dp[n] = min{dp[n-coin]+1, dp[n]} coin in coins, n > 0
     */
    class Solution{
        public int coinChange(int[] coins, int amount) {
            //初始化数组，并赋值为 amount+1（表示无群大，因为最多amount个，均由1组成）
            int[] dp = new int[amount + 1];
            Arrays.fill(dp, amount + 1);
            dp[0] = 0;
            for (int i = 1; i <= amount; i++) {
                for (int coin : coins) {
                    if (i < coin) {
                        continue;
                    }
                    dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
                }
            }
            return dp[amount] == amount + 1 ? -1 :dp[amount];
        }
    }

    class Solution2{
        /**
         * 思路：
         * <p>
         * 1、确定 base case，显然目标金额 amount 为 0 时算法返回 0，
         * 因为不需要任何硬币就已经凑出目标金额了。
         * <p>
         * 2、确定「状态」，也就是原问题和子问题中会变化的变量。由于硬币数量无限，硬币的面额也是题目给定的，
         * 只有目标金额会不断地向 base case 靠近，所以唯一的「状态」就是目标金额 amount。
         * <p>
         * 3、确定「选择」，也就是导致「状态」产生变化的行为。目标金额为什么变化呢，因为你在选择硬币，
         * 你每选择一枚硬币，就相当于减少了目标金额。所以说所有硬币的面值，就是你的「选择」。
         * <p>
         * 4、明确 dp 函数/数组的定义：输入一个目标金额 n，返回凑出目标金额 n 的最少硬币数量。
         * <p>
         * 按照 dp 函数的定义描述「选择」，得到最终答案 dp(amount)。
         */
        int[] memo;

        public int coinChange(int[] coins, int amount) {
            memo = new int[amount + 1];
            // dp数组全部都初始化为特殊值
            Arrays.fill(memo, -666);
            return dp(coins, amount);
        }

        int dp(int[] coins, int amount) {
            if (amount == 0) {
                return 0;
            }
            if (amount < 0) {
                return -1;
            }

            // 查找备忘录，防止重复计算
            if (memo[amount] != -666) {
                return memo[amount];
            }
            int res = Integer.MAX_VALUE;
            for (int coin : coins) {
                // 计算子问题的结果
                int subProblem = dp(coins, amount - coin);
                if (subProblem == -1) {
                    continue;
                }
                res = Math.min(res, subProblem + 1);
            }
            // 把计算结果存放在备忘录
            memo[amount] = (res == Integer.MAX_VALUE) ? -1 :res;
            return memo[amount];
        }
    }
}
