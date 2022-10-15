package leetcode.jzhoffer;

import leetcode.dfs._329_矩阵中的最长递增路径;
import org.junit.jupiter.api.Test;

/**
 * @author mayanwei
 * @date 2022-10-15.
 * @see _329_矩阵中的最长递增路径
 */
public class 剑指_Offer_II_112_最长递增路径{
    class Solution{
        private int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        private Integer res;
        private Integer rows;
        private Integer cols;
        private int [][] memo;

        public int longestIncreasingPath(int[][] matrix) {
            if (matrix == null || matrix.length == 0) {
                return 0;
            }
            rows = matrix.length;
            cols = matrix[0].length;
            res = Integer.MIN_VALUE;
            memo = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    res = Math.max(res, dfs(matrix, i, j, memo));
                }
            }
            return res.equals(Integer.MIN_VALUE) ? 0 :res;
        }

        private int dfs(int[][] matrix, int row, int col, int [][] memo) {
            if (memo[row][col] != 0) {
                return memo[row][col];
            }
            memo[row][col] ++;
            for (int[] dir : dirs) {
                int newRow = row + dir[0], newCol = col + dir[1];
                if (newRow >= 0 && newCol >= 0 && newRow < rows && newCol < cols && matrix[newRow][newCol] > matrix[row][col]) {
                    memo[row][col] = Math.max(memo[row][col], dfs(matrix, newRow, newCol, memo) + 1);
                }
            }
            return memo[row][col];

        }
    }
    @Test
    public void test(){
        int [][] arr = new int[][]{{9,9,4},{6,6,8},{2,1,1}};
        Solution solution = new Solution();
        System.out.println(solution.longestIncreasingPath(arr));
    }
}
