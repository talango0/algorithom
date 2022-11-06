package leetcode.jzhoffer;
//给定一个由 0 和 1 组成的非空二维数组 grid ，用来表示海洋岛屿地图。
//
// 一个 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在水平或者竖直方向上相邻。你可以假设 grid 的四个边缘都被
//0（代表水）包围着。
//
// 找到给定的二维数组中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
//
//
//
// 示例 1:
//
//
//
//
//输入: grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1
//,0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0
//,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
//输出: 6
//解释: 对于上面这个给定矩阵应返回 6。注意答案不应该是 11 ，因为岛屿只能包含水平或垂直的四个方向的 1 。
//
// 示例 2:
//
//
//输入: grid = [[0,0,0,0,0,0,0,0]]
//输出: 0
//
//
//
// 提示：
//
//
// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 50
// grid[i][j] is either 0 or 1
//
//
//
//
// 注意：本题与主站 695 题相同： https://leetcode-cn.com/problems/max-area-of-island/
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 👍 62 👎 0

import leetcode.dfs._695_岛屿的最大面积;

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _695_岛屿的最大面积
 */
public class 剑指_Offer_II_105_岛屿的最大面积{
    class Solution{
        // 我们可以给 dfs 函数设置返回值，记录每次淹没陆地的个数
        public int maxAreaOfIsland(int[][] grid) {
            // 记录岛屿的最大面积
            int res = 0;
            int m = grid.length, n = grid[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        res = Math.max(res, dfs(grid, i, j));
                    }
                }
            }
            return res;
        }

        int dfs(int[][] grid, int i, int j) {
            int m = grid.length, n = grid[0].length;
            if (i < 0 || j < 0 || i >= m || j >= n) {
                // 超出索引边界
                return 0;
            }
            if (grid[i][j] == 0) {
                // 已经是海水了
                return 0;
            }
            // 将(i, j) 变成海水
            grid[i][j] = 0;
            return dfs(grid, i - 1, j)
                    + dfs(grid, i + 1, j)
                    + dfs(grid, i, j - 1)
                    + dfs(grid, i, j + 1)
                    + 1;
        }
    }
}
