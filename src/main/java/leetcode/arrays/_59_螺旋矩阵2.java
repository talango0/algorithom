package leetcode.arrays;
//给你一个正整数 n ，生成一个包含 1 到 n² 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
//
//
//
// 示例 1：
//
//
//输入：n = 3
//输出：[[1,2,3],[8,9,4],[7,6,5]]
//
//
// 示例 2：
//
//
//输入：n = 1
//输出：[[1]]
//
//
//
//
// 提示：
//
//
// 1 <= n <= 20
//
// Related Topics 数组 矩阵 模拟 👍 747 👎 0

/**
 * @author mayanwei
 * @date 2022-07-01.
 */
public class _59_螺旋矩阵2{
    class Solution {
        public int[][] generateMatrix(int n) {
            int [][] maxtrix = new int [n][n];
            int upper_bound = 0, lower_bound = n-1;
            int left_bound = 0, right_bound = n-1;
            //要填入矩阵的数据
            int num = 1;
            //当 num == n*n 表示填充完毕
            while (num <= n*n) {
                if (upper_bound <= lower_bound) {
                    //在顶部从左向右遍历
                    for (int j = left_bound; j<= right_bound; j++) {
                        maxtrix[upper_bound][j] = num++;
                    }
                    //上边界向下移动
                    upper_bound ++;
                }

                if (left_bound <= right_bound) {
                    //在右部从上往下移动
                    for (int i = upper_bound; i<=lower_bound; i++) {
                        maxtrix[i][right_bound] = num++;
                    }
                    //由边界向左移动
                    right_bound --;
                }

                if (upper_bound <= lower_bound) {
                    //底部从左往右遍历
                    for (int j = right_bound;j>=left_bound; j--) {
                        maxtrix[lower_bound][j] = num++;
                    }
                    //下边界向上移动
                    lower_bound --;
                }

                if (left_bound <= right_bound) {
                    //左边从下往上移动
                    for (int i = lower_bound; i>= upper_bound; i--) {
                        maxtrix[i][left_bound] = num++;
                    }
                    //左边界向右移动
                    left_bound ++;
                }
            }
            return maxtrix;
        }
    }
}
