package leetcode.dp;
//给你一个 n x n 的 方形 整数数组 matrix ，请你找出并返回通过 matrix 的下降路径 的 最小和 。
//
// 下降路径 可以从第一行中的任何元素开始，并从每一行中选择一个元素。在下一行选择的元素和当前行所选元素最多相隔一列（即位于正下方或者沿对角线向左或者向右的第
//一个元素）。具体来说，位置 (row, col) 的下一个元素应当是 (row + 1, col - 1)、(row + 1, col) 或者 (row + 1
//, col + 1) 。
//
//
//
// 示例 1：
//
//
//
//
//输入：matrix = [[2,1,3],[6,5,4],[7,8,9]]
//输出：13
//解释：如图所示，为和最小的两条下降路径
//
//
// 示例 2：
//
//
//
//
//输入：matrix = [[-19,57],[-40,-5]]
//输出：-59
//解释：如图所示，为和最小的下降路径
//
//
//
//
// 提示：
//
//
// n == matrix.length == matrix[i].length
// 1 <= n <= 100
// -100 <= matrix[i][j] <= 100
//
//
// Related Topics 数组 动态规划 矩阵 👍 185 👎 0

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-08-04.
 */
public class _931_下降路径最小和{
    class Solution {
        int [][] memo;
        public int minFallingPathSum(int[][] matrix) {
            int n = matrix.length;
            int res = Integer.MAX_VALUE;
            memo = new int[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(memo[i], Integer.MAX_VALUE);
            }
            // 终点可能再最后一行的任意一列
            for (int j = 0; j < n; j++) {
                res = Math.min(res, dp(matrix, n-1, j));
            }
            return res;
        }
        // 定义：从第一行（maxtrix[0][...]）向下落下，落到位置 matrix[i][j] 的最小路径和为 dp(matrix, i, j)
        private int dp(int[][] matrix, int i, int j) {
            // 非法索引检查
            if (i<0 || j<0 || i >= matrix.length || j >= matrix[0].length) {
                return Integer.MAX_VALUE;
            }
            // base case
            if (i == 0) {
                return matrix[i][j];
            }
            if (memo[i][j] != Integer.MAX_VALUE) {
                return memo[i][j];
            }
            // 状态转移
            memo[i][j] = matrix[i][j] + min(
                    dp(matrix, i-1, j-1),
                    dp(matrix, i-1, j),
                    dp(matrix, i-1, j+1)
            );
            return memo[i][j];
        }
        private int min(int a, int b, int c) {
            return Math.min(a, Math.min(b, c));
        }
    }
}
