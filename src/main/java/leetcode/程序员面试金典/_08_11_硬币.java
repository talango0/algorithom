package leetcode.程序员面试金典;
//硬币。给定数量不限的硬币，币值为25分、10分、5分和1分，编写代码计算n分有几种表示法。(结果可能会很大，你需要将结果模上1000000007)
//
//示例1:
//
// 输入: n = 5
// 输出：2
// 解释: 有两种方式可以凑成总金额:
//5=5
//5=1+1+1+1+1
//示例2:
//
// 输入: n = 10
// 输出：4
// 解释: 有四种方式可以凑成总金额:
//10=10
//10=5+5
//10=5+1+1+1+1+1
//10=1+1+1+1+1+1+1+1+1+1
//说明：
//
//注意:
//
//你可以假设：
//
//0 <= n (总金额) <= 1000000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/coin-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


import leetcode.dp._518_零钱兑换II;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-05-29.
 * @see _518_零钱兑换II
 */
public class _08_11_硬币{
    // 超时
    class Solution{
        public int waysToChange(int n) {
            int[] coins = {25, 10, 5, 1};
            return waysToChange(n, coins, 0);
        }

        private int waysToChange(int amount, int[] coins, int index) {
            // 最后一种币种
            if (index >= coins.length - 1) {
                return 1;
            }
            int coinAmount = coins[index];
            int ways = 0;
            for (int i = 0; i * coinAmount <= amount; i++) {
                int amountRemaining = amount - i * coinAmount;
                ways += waysToChange(amountRemaining, coins, index + 1);
            }
            return ways;
        }
    }

    class Solution2{
        public int waysToChange(int n) {
            int[] coins = {25, 10, 5, 1};
            int[][] map = new int[n + 1][coins.length]; // 预处理值
            return waysToChange(n, coins, 0, map);
        }

        private int waysToChange(int amount, int[] coins, int index, int[][] map) {
            if (map[amount][index] > 0) {
                return map[amount][index];
            }
            // 最后一种币种
            if (index >= coins.length - 1) {
                return 1;
            }
            int coinAmount = coins[index];
            int ways = 0;
            for (int i = 0; i * coinAmount <= amount; i++) {
                int amountRemaining = amount - i * coinAmount;
                ways += waysToChange(amountRemaining, coins, index + 1, map);
            }
            map[amount][index] = ways;
            return ways;
        }
    }

    class Solution3{
        static final int MOD = 1000000007;
        public int waysToChange(int n) {
            int[] coins = new int[]{1, 5, 10, 25};
            int[] dp = new int[n + 1];
            Arrays.fill(dp, 0);
            dp[0] = 1;
            for (int i = 0; i < 4; i++) {
                for (int j = coins[i]; j <= n; j++) {
                    if (dp[j - coins[i]] > 0) {
                        dp[j] = (dp[j - coins[i]] + dp[j]) % MOD;
                    }
                }
            }
            return dp[n];
        }
    }

    class Solution4{
        static final int MOD = 1000000007;

        public int waysToChange(int n) {
            int ans = 0;
            for (int i = 0; i * 25 <= n; ++i) {
                int rest = n - i * 25;
                int a = rest / 10;
                int b = rest % 10 / 5;
                ans = (ans + (int) ((long) (a + 1) * (a + b + 1) % MOD)) % MOD;
            }
            return ans;
        }
    }

}
