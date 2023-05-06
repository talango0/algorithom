package leetcode.arrays;
//给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
//
// 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
//
//
//
// 示例 1：
//
//
//输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
//输出：[[7,4,1],[8,5,2],[9,6,3]]
//
//
// 示例 2：
//
//
//输入：matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
//输出：[[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
//
//
//
//
// 提示：
//
//
// n == matrix.length == matrix[i].length
// 1 <= n <= 20
// -1000 <= matrix[i][j] <= 1000
//
//
//
// Related Topics 数组 数学 矩阵 👍 1343 👎 0


/**
 * @author mayanwei
 * @date 2022-07-01.
 *
 * 矩阵的花式遍历
 * @see _54_螺旋矩阵
 * @see _59_螺旋矩阵2
 */
public class _48_旋转矩阵{
    /**
     * <pre>
     * ┌───────┐  ┌───────┐
     * │ 0 1 2 │  │ 6 3 0 │
     * │ 3 4 5 │─▶│ 7 4 1 │
     * │ 6 7 8 │  │ 8 5 2 │
     * └───────┘  └───────┘
     *       │      ▲
     *       ▼      │
     *       ┌───────┐
     *       │ 0 3 6 │
     *       │ 1 4 7 │
     *       │ 2 5 8 │
     *       └───────┘
     * </pre>
     */
    class Solution {
        //将二维矩阵原地顺时针旋转 90
        public void rotate(int[][] matrix) {
            int n = matrix.length;
            //先沿对角线镜像对称二维矩阵
            for (int i = 0;i < n; i++) {
                for (int j = i; j<n; j++) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
            // 然后反转二维矩阵的每一行
            for (int [] row : matrix) {
                reverse(row);
            }
        }

        //反转一维矩阵
        private void reverse(int [] num) {
            int i = 0, j = num.length-1;
            while(i<j){
                int temp = num[i];
                num[i] = num[j];
                num[j] = temp;
                i++;
                j--;
            }
        }

        //将二维矩阵原地逆时针旋转 90
        public void rotate2(int[][] matrix) {
            int n = matrix.length;
            //先沿对角线镜像对称二维矩阵
            for (int i = 0;i < n; i++) {
                for (int j = 0; j<n-i; j++) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[n-1-j][n-1-i];
                    matrix[n-1-j][n-1-i] = temp;
                }
            }
            // 然后反转二维矩阵的每一行
            for (int [] row : matrix) {
                reverse(row);
            }
        }


    }
}
