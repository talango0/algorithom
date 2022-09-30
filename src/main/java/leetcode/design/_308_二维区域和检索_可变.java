package leetcode.design;
//给你一个 2D 矩阵 matrix，请计算出从左上角 (row1, col1) 到右下角 (row2, col2) 组成的矩形中所有元素的和。
//
//
//上述粉色矩形框内的，该矩形由左上角 (row1, col1) = (2, 1) 和右下角 (row2, col2) = (4, 3) 确定。其中，所包括的元素总和 sum = 8。
//
//示例：
//给定 matrix = [
//  [3, 0, 1, 4, 2],
//  [5, 6, 3, 2, 1],
//  [1, 2, 0, 1, 5],
//  [4, 1, 0, 1, 7],
//  [1, 0, 3, 0, 5]
//]
//sumRegion(2, 1, 4, 3) -> 8
//update(3, 2, 2)
//sumRegion(2, 1, 4, 3) -> 10
//
//注意:
//矩阵 matrix 的值只能通过 update 函数来进行修改
//你可以默认 update 函数和 sumRegion 函数的调用次数是均匀分布的
//你可以默认 row1 ≤ row2，col1 ≤ col2

import leetcode.arrays._304_二维区域和检索_矩阵不可变;

/**
 * @author mayanwei
 * @date 2022-09-28.
 * @see _304_二维区域和检索_矩阵不可变
 */
public class _308_二维区域和检索_可变{
    /**
     * 思路：
     * 使用线段树，tree 数组记录从 (0,0) -> (i,j) 的累加和，nums 记录 matrix 的值
     * <p>
     * 从一维到二维我们需要分析如果某个位置发生变化，将会影响 tree 数组哪些位置
     * <p>
     * 在一维中，如果原数组 matrix[i] （假设 i为4，数组长度为 12）发生变化，那么tree[00100(4)] tree[01000(8)]
     * 需要发生变化。
     * <p>
     * 扩展到二维，我们把需要发生变化的行和列进行组合，即为全部要发生变化的位置。
     */
    class NumMatrix{
        int[][] tree;
        int[][] nums;
        int n, m;

        public NumMatrix(int[][] matrix) {
            if (matrix == null && matrix.length == 0 || matrix[0].length == 0) {
                return;
            }
            this.nums = matrix;
            n = matrix.length;
            m = matrix[0].length;
            tree = new int[n + 1][m + 1];
            nums = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    update(i, j, matrix[i][j]);
                }
            }
        }

        private void update(int row, int col, int val) {
            if (n == 0 || m == 0) {
                return;
            }
            int add = val - nums[row][col];
            nums[row][col] = val;
            for (int i = row + 1; i <= n; i += i & (-i)) {
                for (int j = col + 1; j <= m; j += j & (-j)) {
                    tree[i][j] += add;
                }
            }
        }

        public int sumRegin(int row1, int col1, int row2, int col2) {
            if (n == 0 || m == 0) {
                return 0;
            }
            return sum(row2, col2) - sum(row1 - 1, col2) - sum(row2, col1 - 1) + sum(row1 - 1, col1 - 1);
        }

        private int sum(int row, int col) {
            int sum = 0;
            for (int i = row + 1; i > 0; i -= i & (-1)) {
                for (int j = col + 1; j > 0; j -= j & (-j)) {
                    sum += tree[i][j];
                }
            }
            return sum;
        }
    }

}
