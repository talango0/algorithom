package leetcode.jzhoffer;

import java.util.ArrayList;

/**
 * 题目描述
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下4 X 4矩阵：
 * 1 2 3 4
 * 5 6 7 8
 * 9 10 11 12
 * 13 14 15 16
 * 则依次打印出数字 1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
 * 示例1
 * 输入
 * [[1,2],[3,4]]
 * 返回值
 * [1,2,4,3]
 */
public class JZ19顺时针打印矩阵 {
    public class Solution {
        public ArrayList<Integer> printMatrix(int [][] matrix) {
            if (matrix == null) {
                return null;
            }

            int startX = 0;
            int startY = 0;
            int endY = matrix[0].length - 1;
            int endX = matrix.length - 1;

            ArrayList res = new ArrayList<Integer>();
            while ((startX <= endX) && (startY <= endY)) {
                printCircle(matrix, startX, startY, endX, endY,res);

                startX++;
                startY++;
                endX--;
                endY--;
            }
            return res;
        }
        public void printCircle(int[][] matrix, int startX, int startY, int endX, int endY, ArrayList res) {

            // only one row left
            if (startX == endX) {
                for (int i = startY; i <= endY; i++ ) {
                    res.add(matrix[startX][i]);
                }
                return;
            }

            // only one column left
            if (startY == endY) {
                for (int i = startX; i <= endX; i++ ) {
                    res.add(matrix[i][endY]);
                }
                return;
            }

            for (int i = startY; i < endY; i++ ) {
                res.add(matrix[startX][i]);
            }

            for (int i = startX; i < endX; i++ ) {
                res.add(matrix[i][endY]);
            }

            for (int i = endY; i > startY; i-- ) {
                res.add(matrix[endX][i]);
            }

            for (int i = endX; i > startX; i-- ) {
                res.add(matrix[i][startY]);
            }

        }

    }
}
