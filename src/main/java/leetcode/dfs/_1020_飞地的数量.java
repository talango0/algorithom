package leetcode.dfs;
//给你一个大小为 m x n 的二进制矩阵 grid ，其中 0 表示一个海洋单元格、1 表示一个陆地单元格。
//
// 一次 移动 是指从一个陆地单元格走到另一个相邻（上、下、左、右）的陆地单元格或跨过 grid 的边界。
//
// 返回网格中 无法 在任意次数的移动中离开网格边界的陆地单元格的数量。
//
//
//
// 示例 1：
//
//
//输入：grid = [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
//输出：3
//解释：有三个 1 被 0 包围。一个 1 没有被包围，因为它在边界上。
//
//
// 示例 2：
//
//
//输入：grid = [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
//输出：0
//解释：所有 1 都在边界上或可以到达边界。
//
//
//
//
// 提示：
//
//
// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 500
// grid[i][j] 的值为 0 或 1
//
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 👍 181 👎 0

/**
 * @author mayanwei
 * @date 2022-08-05.
 */
public class _1020_飞地的数量{
    class Solution {
        // 该题与1245 的区别是
        // 1. 1表示陆地，0表示海洋
        // 2. 求封闭岛屿的面积
        // 思路：先把靠边的陆地淹掉，然后去数剩下的陆地数量就行了
        public int numEnclaves(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            for (int j = 0; j < n; j++) {
                // 淹掉上边的
                dfs(grid, 0, j);
                // 淹掉下边的
                dfs(grid, m-1, j);
            }
            for (int i = 0; i < m; i++) {
                // 淹掉左边的
                dfs(grid, i, 0);
                // 淹掉右边的
                dfs(grid, i, n-1);
            }
            int area = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        area ++;
                    }
                }
            }
            return area;
        }

        void dfs(int [][] grid, int i, int j) {
            int m = grid.length, n = grid[0].length;
            if (i < 0 || j < 0 || i >= m || j >= n) {
                // 越界返回
                return;
            }
            if (grid[i][j] == 0) {
                // 遇到的是海洋返回
                return;
            }
            // 把 （i,j） 变成海洋
            grid[i][j] = 0;
            dfs(grid, i-1, j); // 淹掉上
            dfs(grid, i+1, j); // 淹掉上
            dfs(grid, i, j-1); // 淹掉左
            dfs(grid, i, j+1); // 淹掉右
        }
    }
}
