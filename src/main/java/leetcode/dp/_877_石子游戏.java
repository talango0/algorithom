package leetcode.dp;
//Alice 和 Bob 用几堆石子在做游戏。一共有偶数堆石子，排成一行；每堆都有 正 整数颗石子，数目为 piles[i] 。
//
// 游戏以谁手中的石子最多来决出胜负。石子的 总数 是 奇数 ，所以没有平局。
//
// Alice 和 Bob 轮流进行，Alice 先开始 。 每回合，玩家从行的 开始 或 结束 处取走整堆石头。 这种情况一直持续到没有更多的石子堆为止，此
//时手中 石子最多 的玩家 获胜 。
//
// 假设 Alice 和 Bob 都发挥出最佳水平，当 Alice 赢得比赛时返回 true ，当 Bob 赢得比赛时返回 false 。
//
//
//
// 示例 1：
//
//
//输入：piles = [5,3,4,5]
//输出：true
//解释：
//Alice 先开始，只能拿前 5 颗或后 5 颗石子 。
//假设他取了前 5 颗，这一行就变成了 [3,4,5] 。
//如果 Bob 拿走前 3 颗，那么剩下的是 [4,5]，Alice 拿走后 5 颗赢得 10 分。
//如果 Bob 拿走后 5 颗，那么剩下的是 [3,4]，Alice 拿走后 4 颗赢得 9 分。
//这表明，取前 5 颗石子对 Alice 来说是一个胜利的举动，所以返回 true 。
//
//
// 示例 2：
//
//
//输入：piles = [3,7,2,3]
//输出：true
//
//
//
//
// 提示：
//
//
// 2 <= piles.length <= 500
// piles.length 是 偶数
// 1 <= piles[i] <= 500
// sum(piles[i]) 是 奇数
//
//
// Related Topics 数组 数学 动态规划 博弈 👍 441 👎 0
/**
 * @author mayanwei
 * @date 2022-07-31.
 */
public class _877_石子游戏{
    class Solution {
        class Pair{
            int fir;
            int sec;
            public Pair(int first, int second){
                this.fir = first;
                this.sec = second;
            }
        }
        public boolean stoneGame(int[] piles) {
            int n = piles.length;
            // 初始化 dp 数组
            Pair[][] dp = new Pair[n][n];
            for (int i = 0; i < n; i++)
                for (int j = i; j < n; j++)
                    dp[i][j] = new Pair(0, 0);
            // 填入 base case
            for (int i = 0; i < n; i++) {
                dp[i][i].fir = piles[i];
                dp[i][i].sec = 0;
            }

            // 倒着遍历数组
            for (int i = n - 2; i >= 0; i--) {
                for (int j = i + 1; j < n; j++) {
                    // 先手选择最左边或最右边的分数
                    int left = piles[i] + dp[i+1][j].sec;
                    int right = piles[j] + dp[i][j-1].sec;
                    // 套用状态转移方程
                    // 先手肯定会选择更大的结果，后手的选择随之改变
                    if (left > right) {
                        dp[i][j].fir = left;
                        dp[i][j].sec = dp[i+1][j].fir;
                    } else {
                        dp[i][j].fir = right;
                        dp[i][j].sec = dp[i][j-1].fir;
                    }
                }
            }
            Pair res = dp[0][n-1];
            return res.fir > res.sec;
        }
    }
}
