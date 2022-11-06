package leetcode.dp;
//给你一个字符串 s 和一个字符串数组 dictionary ，找出并返回 dictionary 中最长的字符串，该字符串可以通过删除 s 中的某些字符得到。
//
//
// 如果答案不止一个，返回长度最长且字母序最小的字符串。如果答案不存在，则返回空字符串。
//
//
//
// 示例 1：
//
//
//输入：s = "abpcplea", dictionary = ["ale","apple","monkey","plea"]
//输出："apple"
//
//
// 示例 2：
//
//
//输入：s = "abpcplea", dictionary = ["a","b","c"]
//输出："a"
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 1000
// 1 <= dictionary.length <= 1000
// 1 <= dictionary[i].length <= 1000
// s 和 dictionary[i] 仅由小写英文字母组成
//
//
// Related Topics 数组 双指针 字符串 排序 👍 320 👎 0

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author mayanwei
 * @date 2022-11-04.
 */
public class _542_01_矩阵{
    class Solution{
        /**
         * BFS
         * 时间复杂度 O(mn)
         * 空间复杂度 O(mn)
         */
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        final int INFINITY = Integer.MAX_VALUE / 2;

        public int[][] updateMatrix(int[][] mat) {
            int m = mat.length, n = mat[0].length;
            int[][] distances = new int[m][n];
            Queue<int[]> queue = new ArrayDeque<>();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (mat[i][j] == 0) {
                        queue.offer(new int[]{i, j});
                    }
                    else {
                        distances[i][j] = INFINITY;
                    }
                }
            }
            while (!queue.isEmpty()) {
                int[] cell = queue.poll();
                int row = cell[0], col = cell[1];
                for (int[] dir : dirs) {
                    int newRow = row + dir[0], newCol = col + dir[1];
                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && distances[newRow][newCol] == INFINITY) {
                        distances[newRow][newCol] = distances[row][col] + 1;
                        queue.offer(new int[]{newRow, newCol});
                    }
                }
            }
            return distances;
        }
    }

    class Solution2{
        /**
         * 动态规划
         * 用 distances 表示结果数组，对于 0 <= i < m 和 0 <= j < n,如果 mit[i][j] = 0,则distances[i][j] = 0
         * 如果 mat[i][j] = 1, 则 distance[i][j] 为矩阵的第 i 行第 j 列到最近的 0 的距离。
         * <p>
         * 矩阵中的每个1都可以经过竖直方向移动x次和水平方向移动y到达最近的0(其中x和y时非负整数)，竖直方可以向上或向下移动，
         * 水平方向可以向左或向右移动，因此可以使用动态规划计算。
         * 结果数组中的元素时动态规划的状态，动态规划的边界情况时当 mat[i][j] = 0 时 distantces[i][j] = 0,
         * 当mat[i][j]时，动态规划的转移方程为
         * distances[i][j] = min(distances[i-1][j], distances[i+ 1][j], distances[i][j-1], distance[i][j+1]) + 1
         */
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        final int INFINITY = Integer.MAX_VALUE / 2;

        public int[][] updateMatrix(int[][] mat) {
            int m = mat.length, n = mat[0].length;
            int[][] distances = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (mat[i][j] == 1) {
                        distances[i][j] = INFINITY;
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (mat[i][j] == 1) {
                        if (i > 0) {
                            distances[i][j] = Math.min(distances[i][j], distances[i - 1][j] + 1);
                        }
                        if (j > 0) {
                            distances[i][j] = Math.min(distances[i][j], distances[i][j - 1] + 1);
                        }
                    }
                }
            }
            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    if (mat[i][j] == 1) {
                        if (i < m - 1) {
                            distances[i][j] = Math.min(distances[i][j], distances[i + 1][j] + 1);
                        }
                        if (j < n - 1) {
                            distances[i][j] = Math.min(distances[i][j], distances[i][j + 1] + 1);
                        }
                    }
                }
            }
            return distances;
        }
    }
}
