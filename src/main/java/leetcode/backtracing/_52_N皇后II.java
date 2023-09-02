package leetcode.backtracing;
//n皇后问题 研究的是如何将 n个皇后放置在 n × n 的棋盘上，并且使皇后彼此之间不能相互攻击。
//给你一个整数 n ，返回 n 皇后问题 不同的解决方案的数量。
//
//示例 1：
//输入：n = 4
//输出：2
//解释：如上图所示，4 皇后问题存在两个不同的解法。
//
//示例 2：
//输入：n = 1
//输出：1
//
//提示：
//
//1 <= n <= 9
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/n-queens-ii
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-07-19.
 */
public class _52_N皇后II{
    class Solution{
        char[][] board;
        int res = 0;

        public int totalNQueens(int n) {
            board = new char[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(board[i], '.');
            }
            backtrace(0);
            return res;
        }

        public void backtrace(int row) {
            if (row == board.length) {
                res++;
                return;
            }
            for (int col = 0; col < board[row].length; col++) {
                if (isValid(board, row, col)) {
                    board[row][col] = 'Q';
                    backtrace(row + 1);
                    board[row][col] = '.';
                }
            }
        }

        private boolean isValid(char[][] board, int row, int col) {
            // 判断同列是否有 'Q'
            for (int i = row - 1; i >= 0; i--) {
                if (board[i][col] == 'Q') {
                    return false;
                }
            }
            // 判断左上
            for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
                if (board[i][j] == 'Q') {
                    return false;
                }
            }

            // 判断右上
            for (int i = row - 1, j = col + 1; i >= 0 && j <= board.length - 1; i--, j++) {
                if (board[i][j] == 'Q') {
                    return false;
                }
            }
            return true;
        }
    }


}
