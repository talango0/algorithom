package leetcode.jzhoffer;

import leetcode.dp._542_01_矩阵;

/**
 * @author mayanwei
 * @date 2022-11-04.
 * @see _542_01_矩阵
 */
public class 剑指_Offer_II_107_矩阵中的距离{
    class Solution {
        public int[][] updateMatrix(int[][] mat) {
            int m = mat.length, n = mat[0].length;
            for(int i = 0; i < m; i++){
                for(int j = 0; j < n; j++){
                    if(mat[i][j] == 1){
                        mat[i][j] = m + n + 1;
                        if(i > 0){
                            mat[i][j] = Math.min(mat[i-1][j]+1, mat[i][j]);
                        }
                        if(j > 0){
                            mat[i][j] = Math.min(mat[i][j-1]+1, mat[i][j]);
                        }
                    }
                }
            }
            for(int i = m-1; i >= 0; i--){
                for(int j = n-1; j >= 0; j--){
                    if(mat[i][j] > 0){
                        if(i < m-1 && mat[i+1][j]+1 < mat[i][j]){
                            mat[i][j] = mat[i+1][j]+1;
                        }
                        if(j < n-1 && mat[i][j+1]+1 < mat[i][j]){
                            mat[i][j] = mat[i][j+1]+1;
                        }
                    }
                }
            }
            return mat;
        }
    }

    class Solution2 {
        public int[][] updateMatrix(int[][] mat) {
            int m = mat.length;
            int n = mat[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    mat[i][j] *= 10000;
                }
            }
            for (int i = 1; i < m; i++) {
                mat[i][0] = Math.min(mat[i - 1][0] + 1, mat[i][0]);
            }
            for (int i = 1; i < n; i++) {
                mat[0][i] = Math.min(mat[0][i - 1] + 1, mat[0][i]);
            }
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    mat[i][j] = Math.min(mat[i][j], Math.min(mat[i - 1][j], mat[i][j - 1]) + 1);
                }
            }
            for (int i = m -2; i >= 0; i--) {
                mat[i][n-1] = Math.min(mat[i + 1][n-1] + 1, mat[i][n-1]);
            }
            for (int i = n - 2; i >= 0; i--) {
                mat[m-1][i] = Math.min(mat[m-1][i + 1] + 1, mat[m-1][i]);
            }
            for (int i = m - 2; i >= 0; i--) {
                for (int j = n - 2; j >= 0 ; j--) {
                    mat[i][j] = Math.min(mat[i][j], Math.min(mat[i + 1][j], mat[i][j + 1]) + 1);
                }
            }
            return mat;
        }
    }
}
