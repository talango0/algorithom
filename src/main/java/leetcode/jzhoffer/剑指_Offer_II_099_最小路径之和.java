package leetcode.jzhoffer;
//给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
//
// 说明：一个机器人每次只能向下或者向右移动一步。
//
//
//
// 示例 1：
//
//
//
//
//输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
//输出：7
//解释：因为路径 1→3→1→1→1 的总和最小。
//
//
// 示例 2：
//
//
//输入：grid = [[1,2,3],[4,5,6]]
//输出：12
//
//
//
//
// 提示：
//
//
// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 200
// 0 <= grid[i][j] <= 100
//
//
//
//
//
// 注意：本题与主站 64 题相同： https://leetcode-cn.com/problems/minimum-path-sum/
//
// Related Topics 数组 动态规划 矩阵 👍 34 👎 0


import leetcode.dp._64_最小路径和;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _64_最小路径和
 */
public class 剑指_Offer_II_099_最小路径之和{
    class Solution{
        int[][] memo;

        public int minPathSum(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            // 构造备忘录，初始值全部设为 -1
            memo = new int[m][n];
            for (int[] row : memo) {
                Arrays.fill(row, -1);
            }

            return dp(grid, m - 1, n - 1);
        }

        int dp(int[][] grid, int i, int j) {
            // base case
            if (i == 0 && j == 0) {
                return grid[0][0];
            }
            if (i < 0 || j < 0) {
                return Integer.MAX_VALUE;
            }
            // 避免重复计算
            if (memo[i][j] != -1) {
                return memo[i][j];
            }
            // 将计算结果记入备忘录
            memo[i][j] = Math.min(
                    dp(grid, i - 1, j),
                    dp(grid, i, j - 1)
            ) + grid[i][j];

            return memo[i][j];
        }
    }
}
