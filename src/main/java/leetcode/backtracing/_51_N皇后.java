package leetcode.backtracing;
//按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
// n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
// 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
// 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
//
//
// 示例 1：
//输入：n = 4
//输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
//解释：如上图所示，4 皇后问题存在两个不同的解法。
//
//
// 示例 2：
//输入：n = 1
//输出：[["Q"]]
//
// 提示：
// 1 <= n <= 9
// Related Topics 数组 回溯 👍 1438 👎 0

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 字节
 * @author mayanwei
 * @date 2022-08-04.
 * @see
 */
public class _51_N皇后{
    class Solution {
        List<List<String>> res = new LinkedList<>();
        public List<List<String>> solveNQueens(int n) {
            char [][] arr = new char[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(arr[i], '.');
            }
            backtrace(arr, 0);
            return res;
        }

        // 填充第row 行数据
        private void backtrace(char [][] arr, int row) {
            if (row == arr.length) {
                List<String> rows = new ArrayList<>();
                for (int i = 0; i < arr.length; i++) {
                    rows.add(new String(arr[i]));
                }
                res.add(rows);
            }
            int n = arr.length;
            for (int j = 0; j < n; j++) {
                if (isValid(arr, row, j)) {
                    // 做出选择
                    arr[row][j] = 'Q';
                    backtrace(arr, row+1);
                    // 撤销选择
                    arr[row][j] = '.';
                }
            }
        }
        private boolean isValid(char [][] arr,int row,int col) {
            // 判断同列是否有 'Q'
            for (int i = row-1; i>= 0; i--) {
                if(arr[i][col] == 'Q') {
                    return false;
                }
            }
            // 判断左上
            for (int i = row-1, j = col-1;i>=0 && j>= 0; i--,j--) {
                if (arr[i][j] == 'Q') {
                    return false;
                }
            }

            // 判断右上
            for (int i = row-1, j = col+1; i>=0 && j<=arr.length-1; i--, j++) {
                if (arr[i][j] == 'Q') {
                    return false;
                }
            }
            return true;
        }

    }
}
