package leetcode.dp;

//
//
// 一些恶魔抓住了公主（P）并将她关在了地下城的右下角。地下城是由 M x N 个房间组成的二维网格。我们英勇的骑士（K）最初被安置在左上角的房间里，他必须穿
//过地下城并通过对抗恶魔来拯救公主。
//
// 骑士的初始健康点数为一个正整数。如果他的健康点数在某一时刻降至 0 或以下，他会立即死亡。
//
// 有些房间由恶魔守卫，因此骑士在进入这些房间时会失去健康点数（若房间里的值为负整数，则表示骑士将损失健康点数）；其他房间要么是空的（房间里的值为 0），要么
//包含增加骑士健康点数的魔法球（若房间里的值为正整数，则表示骑士将增加健康点数）。
//
// 为了尽快到达公主，骑士决定每次只向右或向下移动一步。
//
// 编写一个函数来计算确保骑士能够拯救到公主所需的最低初始健康点数。
//
// 例如，考虑到如下布局的地下城，如果骑士遵循最佳路径 右 -> 右 -> 下 -> 下，则骑士的初始健康点数至少为 7。
//
//
//
// -2 (K) -3  3
// -5    -10  1
// 10     30  -5 (P)
//
// 说明:
//
// 骑士的健康点数没有上限。
// 任何房间都可能对骑士的健康点数造成威胁，也可能增加骑士的健康点数，包括骑士进入的左上角房间以及公主被监禁的右下角房间。
// Related Topics 数组 动态规划 矩阵 👍 643 👎 0

import java.util.Arrays;

/**
 * 字节
 *
 * @author mayanwei
 * @date 2022-07-25.
 */
public class _174_地下城游戏{
    class Solution{
        // 定义: 从grid[i][j] 到达终点（右下角）所需的最少生命值是 dp(grid, i, j)
        // int res = min(dp(i+1, j), dp(i, j+1)) - grid[i][j];
        // dp[i][j] = res <= 0 ? 1:res
        public int calculateMinimumHP(int[][] dungeon) {
            if (dungeon == null) {
                return -1;
            }
            int m = dungeon.length;
            int n = dungeon[0].length;
            // 备忘录中都初始化为-1
            memo = new int[m][n];
            for (int[] row : memo) {
                Arrays.fill(row, -1);
            }
            return dp(dungeon, 0, 0);

        }

        //备忘录，消除重叠子问题
        private int[][] memo;

        private int dp(int[][] grid, int i, int j) {
            int m = grid.length;
            int n = grid[0].length;
            // base case
            if (i == m - 1 && j == n - 1) {
                return grid[i][j] >= 0 ? 1 :-grid[i][j] + 1;
            }
            if (i == m || j == n) {
                return Integer.MAX_VALUE;
            }
            // 避免重复计算
            if (memo[i][j] != -1) {
                return memo[i][j];
            }
            // 状态转移方程
            int res = Math.min(
                    dp(grid, i + 1, j),
                    dp(grid, i, j + 1)
            ) - grid[i][j];
            memo[i][j] = res <= 0 ? 1 :res;
            return memo[i][j];
        }
    }
}
