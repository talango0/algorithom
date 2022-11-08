package leetcode.jzhoffer;
//数组的每个下标作为一个阶梯，第 i 个阶梯对应着一个非负数的体力花费值 cost[i]（下标从 0 开始）。
//
// 每当爬上一个阶梯都要花费对应的体力值，一旦支付了相应的体力值，就可以选择向上爬一个阶梯或者爬两个阶梯。
//
// 请找出达到楼层顶部的最低花费。在开始时，你可以选择从下标为 0 或 1 的元素作为初始阶梯。
//
//
//
// 示例 1：
//
//
//输入：cost = [10, 15, 20]
//输出：15
//解释：最低花费是从 cost[1] 开始，然后走两步即可到阶梯顶，一共花费 15 。
//
//
// 示例 2：
//
//
//输入：cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
//输出：6
//解释：最低花费方式是从 cost[0] 开始，逐个经过那些 1 ，跳过 cost[3] ，一共花费 6 。
//
//
//
//
// 提示：
//
//
// 2 <= cost.length <= 1000
// 0 <= cost[i] <= 999
//
//
//
//
//
// 注意：本题与主站 746 题相同： https://leetcode-cn.com/problems/min-cost-climbing-stairs/
//
//
// Related Topics 数组 动态规划 👍 52 👎 0

import leetcode.arrays._746_使用最小花费爬楼梯;

/**
 * @author mayanwei
 * @date 2022-11-04.
 * @see _746_使用最小花费爬楼梯
 */
public class 剑指_Offer_II_088_爬楼梯的最少成本{
    class Solution{
        public int minCostClimbingStairs(int[] cost) {
            int n = cost.length;
            int[] dp = new int[n + 1];
            dp[0] = dp[1] = 0;
            for (int i = 2; i <= n; i++) {
                dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
            }
            return dp[n];
        }
    }
}
