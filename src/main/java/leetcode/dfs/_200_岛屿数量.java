package leetcode.dfs;
//给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
//
// 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
//
// 此外，你可以假设该网格的四条边均被水包围。
//
//
//
// 示例 1：
//
//
//输入：grid = [
//  ["1","1","1","1","0"],
//  ["1","1","0","1","0"],
//  ["1","1","0","0","0"],
//  ["0","0","0","0","0"]
//]
//输出：1
//
//
// 示例 2：
//
//
//输入：grid = [
//  ["1","1","0","0","0"],
//  ["1","1","0","0","0"],
//  ["0","0","1","0","0"],
//  ["0","0","0","1","1"]
//]
//输出：3
//
//
//
//
// 提示：
//
//
// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 300
// grid[i][j] 的值为 '0' 或 '1'
//
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 👍 1832 👎 0
/**
 * @see _1020_飞地的数量
 * @see _694_不同的岛屿数量
 * @see _695_岛屿的最大面积
 * @see _1254_统计封闭岛屿的数目
 * @see _1905_统计子岛屿
 * @author mayanwei
 * @date 2022-08-05.
 */
public class _200_岛屿数量{
    class Solution {
        public int numIslands(char[][] grid) {
            int res = 0;
            int m = grid.length, n = grid[0].length;
            // 遍历 grid
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1') {
                        // 发现一个岛屿，岛屿数量加1
                        res ++;
                        // 然后使用 dfs 把岛屿淹了
                        dfs(grid, i, j);
                    }
                }
            }
            return res;
        }

        //FloodFill
        public void dfs(char [][] grid, int i, int j) {
            int m = grid.length, n = grid[0].length;
            if ( i < 0 || j < 0 || i >= m || j >= n) {
                // 超出索引边界
                return;
            }
            if (grid[i][j] == '0') {
                // 已经是海水了
                return;
            }
            // 将 (i,j) 变成海水
            grid[i][j] = '0';
            // 淹没上下左右的陆地
            dfs(grid, i-1, j);
            dfs(grid, i+1, j);
            dfs(grid, i, j-1);
            dfs(grid, i, j+1);
        }
    }
}
