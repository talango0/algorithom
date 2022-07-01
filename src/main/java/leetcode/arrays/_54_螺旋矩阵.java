package leetcode.arrays;
//给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
//
//
//
// 示例 1：
//
//
//输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
//输出：[1,2,3,6,9,8,7,4,5]
//
//
// 示例 2：
//
//
//输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
//输出：[1,2,3,4,8,12,11,10,9,5,6,7]
//
//
//
//
// 提示：
//
//
// m == matrix.length
// n == matrix[i].length
// 1 <= m, n <= 10
// -100 <= matrix[i][j] <= 100
//
// Related Topics 数组 矩阵 模拟 👍 1130 👎 0

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-07-01.
 *
 * @see _59_螺旋矩阵2
 */
public class _54_螺旋矩阵{

    class Solution {
        public List<Integer> spiralOrder(int[][] matrix) {
            if(matrix == null){
                return new ArrayList<Integer>(0);
            }
            int m = matrix.length, n = matrix[0].length;
            int upper_bound = 0, lower_bound = m-1;
            int left_bound = 0, right_bound = n-1;
            List<Integer> res = new ArrayList<Integer>(m*n);
            // 如果 res.size() == m*n ，表示遍历结束
            while(res.size() < m*n){
                if (upper_bound <= lower_bound) {
                    //从顶部往右遍历
                    for (int j = left_bound;j<=right_bound; j++) {
                        res.add(matrix[upper_bound][j]);
                    }
                    //上边界下移动
                    upper_bound ++;
                }

                if (left_bound <= right_bound) {
                    //在右侧从上向下遍历
                    for (int i = upper_bound; i<=lower_bound; i++) {
                        res.add(matrix[i][right_bound]);
                    }
                    //由边界左移
                    right_bound --;
                }

                if (upper_bound <= lower_bound) {
                    //底部从左向右遍历
                    for (int j = right_bound; j>= left_bound; j--) {
                        res.add(matrix[lower_bound][j]);
                    }
                    //下边界上移
                    lower_bound --;
                }

                if (left_bound <= right_bound) {
                    //左侧由下向上遍历
                    for (int i = lower_bound; i >= upper_bound; i--){
                        res.add(matrix[i][left_bound]);
                    }
                    left_bound ++;
                }
            }
            return res;
        }
    }
}
