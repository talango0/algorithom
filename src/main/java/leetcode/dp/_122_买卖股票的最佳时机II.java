package leetcode.dp;
//给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
// 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
// 返回 你能获得的 最大 利润 。
//
//
//
// 示例 1：
//输入：prices = [7,1,5,3,6,4]
//输出：7
//解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
//    随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3 。
//     总利润为 4 + 3 = 7 。
//
// 示例 2：
//输入：prices = [1,2,3,4,5]
//输出：4
//解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
//    总利润为 4 。
//
// 示例 3：
//输入：prices = [7,6,4,3,1]
//输出：0
//解释：在这种情况下, 交易无法获得正利润，所以不参与交易可以获得最大利润，最大利润为 0 。
//
//
//
// 提示：
//
//
// 1 <= prices.length <= 3 * 10⁴
// 0 <= prices[i] <= 10⁴
//
// Related Topics 贪心 数组 动态规划 👍 1744 👎 0


/**
 * @author mayanwei
 * @date 2022-06-25.
 */
public class _122_买卖股票的最佳时机II{
    //股票系列问题状态定义：
    //
    //dp[i][k][0 or 1]
    //0 <= i <= n - 1, 1 <= k <= K
    //n 为天数，大 K 为交易数的上限，0 和 1 代表是否持有股票。
    //股票系列问题通用状态转移方程和 base case：
    //
    //状态转移方程：
    //dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
    //dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
    //
    //base case：
    //dp[-1][...][0] = dp[...][0][0] = 0
    //dp[-1][...][1] = dp[...][0][1] = -infinity
    //特化到 k 无限制的情况，状态转移方程如下：
    //
    //dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
    //dp[i][1] = max(dp[i-1][1], dp[i-1][0] - prices[i])
    class Solution0{
         public int maxProfit(int[] prices) {
             int n = prices.length;
             int [][] dp = new int[n][2];
             for (int i = 0; i < n; i++) {
                 if (i-1 == -1) {
                     dp[i][0] = 0;
                     dp[i][1] = -prices[i];
                     continue;
                 }
                 dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
                 dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);
             }
             return dp[n-1][0];
         }
    }

    class Solution {

        /**
         * 空间优化版本
         */
        public int maxProfit(int[] prices) {
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
    }
}
