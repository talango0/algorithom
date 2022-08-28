package leetcode.dp;
//在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
//
//示例 1：
//
//
//输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
//输出：4
//示例 2：
//
//
//输入：matrix = [["0","1"],["1","0"]]
//输出：1
//示例 3：
//
//输入：matrix = [["0"]]
//输出：0
//提示：
//
//m == matrix.length
//n == matrix[i].length
//1 <= m, n <= 300
//matrix[i][j] 为 '0' 或 '1'
//Related Topics
//
//👍 1246, 👎 0

/**
 * @author mayanwei
 * @date 2022-08-27.
 */
public class _221_最大正方形{
    class Solution{
        //思路：
        // 关键是观察出一个全是 1 的正方形有什么特点，如何根据小的正方形推到出大的正方形（状态转移方程）
        // 当 maxtrix[i][j] = 1,且它的左边、上边、左上边都存在正方形时， matrix[i][j] 才能狗作为一个更大的正方形的右下角
        // 所以我们可以这样定义一个二维 dp 数组：
        // 以 matrix[i][j]为右下角元素的最大的全为1正方形矩阵变长位 dp[i][j]
        // 有了这个定义，状态转移方程是：
        // if (matrix[i][j] == 1)
        //      // 类似 水桶效应 ，最长边长取决于边长最短的那个正方形
        //      dp[i][j] = min(dp[i-1][j], dp[i-1][j-1], dp[i][j-1]) + 1
        // else
        //      dp[][] = 0;
        // 最终想要的答案就是最长边长 max(dp[..][..]) 的平方
        public int maximalSquare(char[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            // 定义：以maxtrix[i][j] 为右下角元素的全为 1 正方形举证的最大边长为 dp[i][j]。
            int[][] dp = new int[m][n];
            // base case ,第一行和第一列的正方形边长
            for (int i = 0; i < m; i++) {
                dp[i][0] = matrix[i][0] - '0';
            }
            for (int j = 0; j < n; j++) {
                dp[0][j] = matrix[0][j] - '0';
            }
            // 进行状态转移
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    if (matrix[i][j] == '0') {
                        // 值为 0 不可能是正方形的右下角
                        continue;
                    }
                    dp[i][j] = Math.min(Math.min(
                                    dp[i - 1][j],
                                    dp[i][j - 1]),
                            dp[i - 1][j - 1]
                    ) + 1;
                }
            }
            int len = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    len = Math.max(len, dp[i][j]);
                }
            }
            return len * len;
        }
    }
}
