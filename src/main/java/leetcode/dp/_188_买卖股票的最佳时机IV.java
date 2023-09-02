package leetcode.dp;
//给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
//
// 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
//
// 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
//
//
//
// 示例 1：
//输入：k = 2, prices = [2,4,1]
//输出：2
//解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
//
// 示例 2：
//输入：k = 2, prices = [3,2,6,5,0,3]
//输出：7
//解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
//     随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3
//。
//
//
//
// 提示：
//
//
// 0 <= k <= 100
// 0 <= prices.length <= 1000
// 0 <= prices[i] <= 1000
//
// Related Topics 数组 动态规划 👍 753 👎 0

/**
 * 字节
 * @author mayanwei
 * @date 2022-06-25.
 */
public class _188_买卖股票的最佳时机IV{
    class Solution {
        /**
         * <pre>
         * 股票系列问题状态定义：
         *
         * dp[i][k][0 or 1]
         * 0 <= i <= n - 1, 1 <= k <= K
         * n 为天数，大 K 为交易数的上限，0 和 1 代表是否持有股票。
         * 股票系列问题通用状态转移方程：
         *
         * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
         *               max( 今天选择 rest,        今天选择 sell       )
         *
         * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
         *               max( 今天选择 rest,         今天选择 buy         )
         * 通用 base case：
         *
         * dp[-1][...][0] = dp[...][0][0] = 0
         * dp[-1][...][1] = dp[...][0][1] = -infinity
         * 特化到 k = 1 的情况，状态转移方程和 base case 如下：
         *
         * 状态转移方程：
         * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
         * dp[i][1] = max(dp[i-1][1], -prices[i])
         *
         * base case：
         * dp[i][0] = 0;
         * dp[i][1] = -prices[i];
         * </pre>
         * @param k
         * @param prices
         * @return
         */
        public int maxProfit(int k, int[] prices) {
            int n = prices.length;
            if (k > n/2){
                // 该题的k不受限制，一次交易由买入和卖出构成，至少需要两天。
                // 所以说有效限制 k 应该是 n/2, 如果超过，就没有约束作用了，相当于k没有限制的情况。
                return maxProfit(prices);
            }
            int [][][] dp = new int[n][k+1][2];
            //k等于0是的 base case
            for (int i=0; i< n; i++) {
                dp[i][0][1] = Integer.MIN_VALUE;
                dp[i][0][0] = 0;
            }
            for (int i=0; i<n; i++){
                for (int j=k; j>=1; j--){
                    if (i-1==-1){
                        dp[i][j][0] = 0;
                        dp[i][j][1] = -prices[i];
                        continue;
                    }
                    dp[i][j][0] = Math.max(dp[i-1][j][0], dp[i-1][j][1]+prices[i]);
                    dp[i][j][1] = Math.max(dp[i-1][j][1], dp[i-1][j-1][0]-prices[i]);
                }
            }
            return dp[n-1][k][0];
        }

        public int  maxProfit(int [] prices){
            int n = prices.length;
            int dp_i_0 = 0;
            int dp_i_1 = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                int tmp = dp_i_0;
                dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
                dp_i_1 = Math.max(dp_i_1, tmp - prices[i]);
            }
            return dp_i_0;
        }

        // 第 122 题，k 无限的解法
        //private int maxProfit(int[] prices) {
        //    int n = prices.length;
        //    int[][] dp = new int[n][2];
        //    for (int i = 0; i < n; i++) {
        //        if (i - 1 == -1) {
        //            // base case
        //            dp[i][0] = 0;
        //            dp[i][1] = -prices[i];
        //            continue;
        //        }
        //        dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
        //        dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        //    }
        //    return dp[n - 1][0];
        //}

    }

    /**
     * 输入股票价格数组 prices，你最多进行 max_k 次交易，每次交易需要额外消耗 fee 的手续费，
     * 而且每次交易之后需要经过 cooldown 天的冷冻期才能进行下一次交易，请你计算并返回可以获得的最大利润。
     */
    class ALLInOneSolution{
        // 同时考虑交易次数的限制、冷冻期和手续费
        int maxProfit_all_in_one(int max_k, int[] prices, int cooldown, int fee) {
            int n = prices.length;
            if (n <= 0) {
                return 0;
            }
            if (max_k > n / 2) {
                // 交易次数 k 没有限制的情况
                return maxProfit_k_inf(prices, cooldown, fee);
            }

            int[][][] dp = new int[n][max_k + 1][2];
            // k = 0 时的 base case
            for (int i = 0; i < n; i++) {
                dp[i][0][1] = Integer.MIN_VALUE;
                dp[i][0][0] = 0;
            }

            for (int i = 0; i < n; i++)
                for (int k = max_k; k >= 1; k--) {
                    if (i - 1 == -1) {
                        // base case 1
                        dp[i][k][0] = 0;
                        dp[i][k][1] = -prices[i] - fee;
                        continue;
                    }

                    // 包含 cooldown 的 base case
                    if (i - cooldown - 1 < 0) {
                        // base case 2
                        dp[i][k][0] = Math.max(dp[i-1][k][0], dp[i-1][k][1] + prices[i]);
                        // 别忘了减 fee
                        dp[i][k][1] = Math.max(dp[i-1][k][1], -prices[i] - fee);
                        continue;
                    }
                    dp[i][k][0] = Math.max(dp[i-1][k][0], dp[i-1][k][1] + prices[i]);
                    // 同时考虑 cooldown 和 fee
                    dp[i][k][1] = Math.max(dp[i-1][k][1], dp[i-cooldown-1][k-1][0] - prices[i] - fee);
                }
            return dp[n - 1][max_k][0];
        }

        // k 无限制，包含手续费和冷冻期
        int maxProfit_k_inf(int[] prices, int cooldown, int fee) {
            int n = prices.length;
            int[][] dp = new int[n][2];
            for (int i = 0; i < n; i++) {
                if (i - 1 == -1) {
                    // base case 1
                    dp[i][0] = 0;
                    dp[i][1] = -prices[i] - fee;
                    continue;
                }

                // 包含 cooldown 的 base case
                if (i - cooldown - 1 < 0) {
                    // base case 2
                    dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
                    // 别忘了减 fee
                    dp[i][1] = Math.max(dp[i-1][1], -prices[i] - fee);
                    continue;
                }
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
                // 同时考虑 cooldown 和 fee
                dp[i][1] = Math.max(dp[i - 1][1], dp[i - cooldown - 1][0] - prices[i] - fee);
            }
            return dp[n - 1][0];
        }


    }
}
