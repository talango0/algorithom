package leetcode.dfs;
//给你两个 m x n 的二进制矩阵 grid1 和 grid2 ，它们只包含 0 （表示水域）和 1 （表示陆地）。一个 岛屿 是由 四个方向 （水平或者竖
//直）上相邻的 1 组成的区域。任何矩阵以外的区域都视为水域。
//
// 如果 grid2 的一个岛屿，被 grid1 的一个岛屿 完全 包含，也就是说 grid2 中该岛屿的每一个格子都被 grid1 中同一个岛屿完全包含，那
//么我们称 grid2 中的这个岛屿为 子岛屿 。
//
// 请你返回 grid2 中 子岛屿 的 数目 。
//
//
//
// 示例 1：
// 输入：grid1 = [[1,1,1,0,0],[0,1,1,1,1],[0,0,0,0,0],[1,0,0,0,0],[1,1,0,1,1]],
//grid2 = [[1,1,1,0,0],[0,0,1,1,1],[0,1,0,0,0],[1,0,1,1,0],[0,1,0,1,0]]
//输出：3
//解释：如上图所示，左边为 grid1 ，右边为 grid2 。
//grid2 中标红的 1 区域是子岛屿，总共有 3 个子岛屿。
//
//
// 示例 2：
// 输入：grid1 = [[1,0,1,0,1],[1,1,1,1,1],[0,0,0,0,0],[1,1,1,1,1],[1,0,1,0,1]],
//grid2 = [[0,0,0,0,0],[1,1,1,1,1],[0,1,0,1,0],[0,1,0,1,0],[1,0,0,0,1]]
//输出：2
//解释：如上图所示，左边为 grid1 ，右边为 grid2 。
//grid2 中标红的 1 区域是子岛屿，总共有 2 个子岛屿。
//
//
//
//
// 提示：
//
//
// m == grid1.length == grid2.length
// n == grid1[i].length == grid2[i].length
// 1 <= m, n <= 500
// grid1[i][j] 和 grid2[i][j] 都要么是 0 要么是 1 。
//
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 👍 68 👎 0

/**
 * @author mayanwei
 * @date 2022-08-05.
 */
public class _1905_统计子岛屿{
    class Solution {
        // 什么情况下 grid2 中的一个岛屿 B 时 grid1 中的一个岛屿 A 的子岛
        // 反过来说，如果岛屿 B 中存在一个块陆地，在岛屿 A 的对应位置是海水，那么岛屿 B 就不是岛屿 A 的子岛
        // 那么我们只要遍历 grid2 中的所有岛屿，把那些不可能是子岛的岛屿排除，剩下的就是子岛
        public int countSubIslands(int[][] grid1, int[][] grid2) {
            int m = grid1.length, n = grid1[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid1[i][j] == 0 && grid2[i][j] == 1) {
                        // 这个岛屿肯定不是子岛，淹掉
                        dfs(grid2, i, j);
                    }
                }
            }
            // 现在 grid2 中剩下的岛屿都是子岛，计算岛的数量
            int res = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid2[i][j] == 1) {
                        res ++ ;
                        dfs(grid2, i, j);
                    }
                }
            }
            return res;
        }
        void dfs(int [][] grid, int i, int j) {
            int m = grid.length, n = grid[0].length;
            if (i < 0 || j < 0 || i >= m || j>= n) {
                return;
            }
            if (grid[i][j] == 0) {
                return;
            }
            grid[i][j] = 0;
            dfs(grid, i-1, j);
            dfs(grid, i+1, j);
            dfs(grid, i, j-1);
            dfs(grid, i, j+1);
        }
    }
}
