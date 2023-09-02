package leetcode.dp;

/**
 * @author mayanwei
 * @date 2022-06-25.
 */
//给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
// 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
// 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
//
//
// 示例 1：
//输入：[7,1,5,3,6,4]
//输出：5
//解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
//     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
//
//
// 示例 2：
//输入：prices = [7,6,4,3,1]
//输出：0
//解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
//
//
//
//
// 提示：
//
//
// 1 <= prices.length <= 10⁵
// 0 <= prices[i] <= 10⁴
//
// Related Topics 数组 动态规划 👍 2409 👎 0


import org.junit.jupiter.api.Test;

/**
 * @see _122_买卖股票的最佳时机II
 * @see _123_买卖股票的最佳时期III
 * @see _188_买卖股票的最佳时机IV
 * @see _309_最佳买卖股票股票时机含冷冻期
 * @see _714_买卖股票的最佳时机含手续费
 */
public class _121_买卖股票的最佳时机{


    class Solution{
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
         *
         * 退化到 k = 1 的情况，状态转移方程和 base case 如下：
         * 状态转移方程：
         * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
         * dp[i][1] = max(dp[i-1][1], -prices[i])
         *
         * base case：
         * dp[i][0] = 0;
         * dp[i][1] = -prices[i];
         * </pre>
         * @param prices
         * @return
         */
        public int maxProfit(int[] prices) {
            if (prices == null) {
                return 0;
            }

            int[][] dp = new int[prices.length][2];
            for (int i = 0; i < prices.length; i++) {
                if (i - 1 == -1) {
                    dp[i][0] = 0;
                    dp[i][1] = -prices[i];
                    continue;
                }
                //dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k-1][1] + prices[i])
                //dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
                //            = max(dp[i-1][1][1], -prices[i])
                //解释：k = 0 的 base case，所以 dp[i-1][0][0] = 0。
                //这里k = 1不变，因此k对状态转移没有影响，可以进行进一步优化简化掉k

                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
                dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
            }
            return dp[prices.length - 1][0];
        }
    }

    /**
     * 空间优化版
     */
    class Solution2{
        public int maxProfit(int[] prices) {
            if (prices == null) {
                return 0;
            }
            int[][] dp = new int[prices.length][2];
            int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
            for (int i = 0; i < prices.length; i++) {
                dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
                dp_i_1 = Math.max(dp_i_1, -prices[i]);
            }
            return dp_i_0;
        }
    }

    @Test
    public void testSolution() {
        Solution solution = new Solution();
        solution.maxProfit(new int[]{7, 1, 5, 3, 6, 4});
    }
}
