package leetcode.arrays;

//给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
//
//
//
//示例 1：
//
//
//输入：matrix = [[1,1,1],[1,0,1],[1,1,1]]
//输出：[[1,0,1],[0,0,0],[1,0,1]]
//示例 2：
//
//
//输入：matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
//输出：[[0,0,0,0],[0,4,5,0],[0,3,1,0]]
//
//
//提示：
//
//m == matrix.length
//n == matrix[0].length
//1 <= m, n <= 200
//-231 <= matrix[i][j] <= 231 - 1
//
//
//进阶：
//
//一个直观的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
//一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
//你能想出一个仅使用常量空间的解决方案吗？

/**
 * @see _289_生命游戏
 */
public class _73_矩阵置零 {
    class Solution {
        public void setZeroes(int[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            boolean[] row = new boolean[m];
            boolean[] col = new boolean[n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        row[i] = col[j] = true;
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (row[i] || col[j]) {
                        matrix[i][j] = 0;
                    }
                }
            }
        }
    }

    /**
     * 空间优化算法
     * <p>
     * 时间复杂度 O(mn)
     * <p>
     * 空间复杂度 O(1)
     */
    class Solution1 {
        /**
         * 只使用一个标记变量记录第一列是否原本存在 0。这样，第一列的第一个元素即可以标记第一行是否出现 0。
         * 但为了防止每一列的第一个元素被提前更新，我们需要从最后一行开始，倒序地处理矩阵元素。
         */
        public void setZeroes(int[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            boolean flagCol0 = false;
            for (int i = 0; i < m; i++) {
                if (matrix[i][0] == 0) {
                    flagCol0 = true;
                }
                for (int j = 1; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        matrix[i][0] = matrix[0][j] = 0;
                    }
                }
            }
            for (int i = m - 1; i >= 0; i--) {
                for (int j = 1; j < n; j++) {
                    if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                        matrix[i][j] = 0;
                    }
                }
                if (flagCol0) {
                    matrix[i][0] = 0;
                }
            }
        }
    }
}
