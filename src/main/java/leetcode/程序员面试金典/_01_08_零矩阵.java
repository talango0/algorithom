package leetcode.程序员面试金典;

import java.util.Arrays;

public class _01_08_零矩阵{
    /**
     * 编写一种算法，若M × N矩阵中某个元素为0，则将其所在的行与列清零。
     *
     * ```
     * 输入：
     * [
     *   [1,1,1],
     *   [1,0,1],
     *   [1,1,1]
     * ]
     * 输出：
     * [
     *   [1,0,1],
     *   [0,0,0],
     *   [1,0,1]
     * ]
     * ```
     */


    /**
     * 思路
     * 用 v[0..m,0..n] 记录是否被访问过，如果访问过置为1，未访问默认为0
     * 如果matrix[i][j]为0，则将matrix[i][0..n] 和 matrix[0..m][j] 设置为0
     */
    static class Solution1 {
        public void setZeroes(int[][] matrix) {
            int m = matrix.length;
            if(m<1){
                return;
            }
            int n = matrix[0].length;
            int[][] v = new int[m][n];
            for(int i = 0; i<m; i++){
                for(int j = 0; j<n; j++){
                    if(v[i][j] != 1 && matrix[i][j] == 0){
                        for(int k= 0; k<m; k++){
                            setZeroes(matrix, i, j, k, j, v);

                        }
                        for(int k = 0; k<n; k++){
                            setZeroes(matrix, i, j, i, k, v);
                        }
                    }
                }
            }
        }

        private void setZeroes(int[][] matrix, int i, int j, int m, int n, int[][] v) {
            if(m == i && n == j){
                v[i][j] = 1;
            }
            if(matrix[m][n] != 0){
                v[m][n] = 1;
                matrix[m][n] = 0;
            }
        }
    }


    static class Solution{
        public void setZeroes(int [][] matrix){
            boolean isFirstRowHaveZero = false;
            boolean isFirstColHaveZero = false;
            for(int i = 0; i < matrix.length; i++) {
                if (matrix[i][0] == 0) {
                    isFirstColHaveZero = true;
                }
            }

            for(int j = 0; j < matrix[0].length; j++) {
                if (matrix[0][j] == 0) {
                    isFirstRowHaveZero = true;
                }
            }

            for(int i = 1; i < matrix.length; i++) {
                for(int j = 1; j < matrix[i].length; j++) {
                    if (matrix[i][j] == 0) {
                        matrix[0][j] = 0;
                        matrix[i][0] = 0;
                    }
                }
            }

            for(int i = 1; i < matrix.length; i++) {
                for(int j = 1; j < matrix[i].length; j++) {
                    if (matrix[0][j] == 0 || matrix[i][0] == 0) {
                        matrix[i][j] = 0;
                    }
                }
            }

            for(int i = 0; i < matrix.length; i++) {
                if (isFirstColHaveZero) {
                    matrix[i][0] = 0;
                }
            }

            for(int j = 0; j < matrix[0].length; j++) {
                if (isFirstRowHaveZero) {
                    matrix[0][j] = 0;
                }
            }

        }
    }

    public static void main(String[] args) {
        int [][] matrix1 = {{1,1,1},{1,0,1},{1,1,1}};
        int [][] matrix2 = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        int [][] matrix = {{0},{1}};

        Solution solution = new Solution();
        solution.setZeroes(matrix);
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

}
