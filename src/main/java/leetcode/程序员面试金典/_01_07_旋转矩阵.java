package leetcode.程序员面试金典;

import java.util.Arrays;

public class _01_07_旋转矩阵{
//    给你一幅由 N × N 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。
//    不占用额外内存空间能否做到？
//    示例 1:
//    给定 matrix =
//        [
//        [1,2,3],
//        [4,5,6],
//        [7,8,9]
//        ],
//
//    原地旋转输入矩阵，使其变为:
//            [
//            [7,4,1],
//            [8,5,2],
//            [9,6,3]
//            ]


    static class Solution1{
        public void rotate(int [][] matrix){
            int n = matrix.length;
            int [][] temp_matrix = new int[n][n];
            for(int i = 0;i<n;i++){
                for (int j = 0;j<n;j++){
                    temp_matrix[j][n-i-1] = matrix[i][j];
                }
            }
            for(int i = 0;i<n;i++){
               System.arraycopy(temp_matrix[i],0,matrix[i],0,matrix[i].length);
            }
        }
    }
    static class Solution{
        public void rotate(int [][] matrix){
            int n = matrix.length;
            for(int i = 0;i<n/2;i++){
                for (int j = i; j<n-1-i; j++){
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[n-1-j][i];
                    matrix[n-1-j][i] = matrix[n-1-i][n-1-j];
                    matrix[n-1-i][n-1-j] = matrix[j][n-1-i];
                    matrix[j][n-1-i] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int [][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        Solution solution = new Solution();
        solution.rotate(matrix);
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }

    }
}
