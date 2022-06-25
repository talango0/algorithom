package leetcode.dp;
//给定一个整数数组 prices，其中第 prices[i] 表示第 i 天的股票价格 。
//
// 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
//
//
// 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
//
//
// 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
//
//
//
// 示例 1:
//
//
//输入: prices = [1,2,3,0,2]
//输出: 3
//解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
//
// 示例 2:
//
//
//输入: prices = [1]
//输出: 0
//
//
//
//
// 提示：
//
//
// 1 <= prices.length <= 5000
// 0 <= prices[i] <= 1000
//
// Related Topics 数组 动态规划 👍 1241 👎 0


/**
 * @author mayanwei
 * @date 2022-06-25.
 */
public class _309_最佳买卖股票股票时机含冷冻期{
    class Solution {
         public int maxProfit0(int[] prices) {
             int n = prices.length;
             int [][] dp = new int [n][2];
             for (int i = 0; i < n; i++) {
                 if(i - 1 == -1){
                     dp[i][0] = 0;
                     dp[i][1] = -prices[i];
                     continue;
                 }
                 if (i - 2 == -1) {
                     dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
                     dp[i][1] = Math.max(dp[i-1][1], -prices[i]);
                     continue;
                 }
                 dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
                 dp[i][1] = Math.max(dp[i-1][1], dp[i-2][0] - prices[i]);
             }
             return dp[n-1][0];
         }

        /**空间优化版本 */
        public int maxProfit(int[] prices) {
            int n = prices.length;
            int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
            int dp_pre_0 =0, tmp = 0;
            for (int i = 0; i < n; i++) {
                tmp = dp_i_0;
                dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
                dp_i_1 = Math.max(dp_i_1, dp_pre_0 - prices[i]);
                dp_pre_0 = tmp;
            }
            return dp_i_0;
        }
    }
}
