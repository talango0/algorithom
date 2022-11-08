package leetcode.jzhoffer;

import leetcode.dp._322_零钱兑换;

import java.util.Arrays;
//给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回
// -1。
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
// 示例 4：
//
//
//输入：coins = [1], amount = 1
//输出：1
//
//
// 示例 5：
//
//
//输入：coins = [1], amount = 2
//输出：2
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
//
//
//
//
// 注意：本题与主站 322 题相同： https://leetcode-cn.com/problems/coin-change/
//
// Related Topics 广度优先搜索 数组 动态规划 👍 56 👎 0


/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _322_零钱兑换
 */
public class 剑指_Offer_II_103_最少的硬币数目{
    class Solution{
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
