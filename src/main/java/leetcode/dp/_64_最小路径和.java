package leetcode.dp;
//给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
//
// 说明：每次只能向下或者向右移动一步。
//
//
//
// 示例 1：
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
// Related Topics 数组 动态规划 矩阵 👍 1300 👎 0


/**
 * @author mayanwei
 * @date 2022-07-25.
 */
public class _64_最小路径和{
    class Solution {
        // dp[i][j] 表示从 grid[0][0] 到 grid[i][j] 的距离之和, 设置初始值均为0
        // dp[i][j] = min{dp[i][j-1], dp[i-1][j]}+ grid[i][j] 其中 0 <= i <= m-1, 0<=j<n-1
        // base case dp[0][0] = grid[0][0];
        public int minPathSum(int[][] grid) {
            if (grid == null) {
                return 0;
            }
            int m = grid.length;
            int n = grid[0].length;
            int [][] dp = new int[m][n];
            /**base case */
            dp[0][0] = grid[0][0];
            for (int i = 1; i<m; i++) {
                dp[i][0] = dp[i-1][0] + grid[i][0];
            }
            for (int j = 1; j<n; j++) {
                dp[0][j] = dp[0][j-1] + grid[0][j];
            }

            for (int i = 1; i<m; i++) {
                for(int j = 1; j<n; j++) {
                    dp[i][j] = Math.min(dp[i][j-1], dp[i-1][j]) + grid[i][j];
                }
            }
            return dp[m-1][n-1];
        }
    }
}
