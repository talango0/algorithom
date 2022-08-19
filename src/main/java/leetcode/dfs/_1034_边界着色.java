package leetcode.dfs;
//给你一个大小为 m x n 的整数矩阵 grid ，表示一个网格。另给你三个整数 row、col 和 color 。网格中的每个值表示该位置处的网格块的颜色。
//
//两个网格块属于同一 连通分量 需满足下述全部条件：
//
//两个网格块颜色相同
//在上、下、左、右任意一个方向上相邻
//连通分量的边界 是指连通分量中满足下述条件之一的所有网格块：
//
//在上、下、左、右任意一个方向上与不属于同一连通分量的网格块相邻
//在网格的边界上（第一行/列或最后一行/列）
//请你使用指定颜色 color 为所有包含网格块 grid[row][col] 的 连通分量的边界 进行着色，并返回最终的网格 grid 。
//
//示例 1：
//
//输入：grid = [[1,1],[1,2]], row = 0, col = 0, color = 3
//输出：[[3,3],[3,2]]
//示例 2：
//
//输入：grid = [[1,2,2],[2,3,2]], row = 0, col = 1, color = 3
//输出：[[1,3,3],[2,3,3]]
//示例 3：
//
//输入：grid = [[1,1,1],[1,1,1],[1,1,1]], row = 1, col = 1, color = 2
//输出：[[2,2,2],[2,1,2],[2,2,2]]
//提示：
//
//m == grid.length
//n == grid[i].length
//1 <= m, n <= 50
//1 <= grid[i][j], color <= 1000
//0 <= row < m
//0 <= col < n
//Related Topics
//
//👍 151, 👎 0

/**
 * @author mayanwei
 * @date 2022-08-19.
 */
public class _1034_边界着色{
    class Solution{
        boolean[][] visited;

        public int[][] colorBorder(int[][] grid, int row, int col, int newColor) {
            int originColor = grid[row][col];
            visited = new boolean[grid.length][grid[0].length];
            fill(grid, row, col, originColor, newColor);
            return grid;
        }

        /**
         * int fill(int[][] image, int x, int y, int originColor, int newColor) {
         * if(!inArea(image, x, y)) {
         * //出界
         * return 0;
         * }
         * if (visited[x][y] == true) {
         * // 已探索过的 origColor 区域
         * return 1;
         * }
         * if (image[x][y] != originColor) {
         * // 碰壁：遇到其他颜色，超出originColor区域
         * return 0;
         * }
         * if (image[x][y] == originColor) {
         * // 未搜索且属于originColor 区域
         * ...
         * return 1;
         * }
         * }
         */
        int fill(int[][] image, int x, int y, int originColor, int newColor) {
            // 出界：超出数组边界
            if (!inArea(image, x, y)) {
                return 0;
            }
            // 已探索过的 origColor 区域
            if (visited[x][y] == true) {
                return 1;
            }
            // 碰壁：遇到其他颜色，超出originColor区域
            if (image[x][y] != originColor) {
                return 0;
            }
            // 下面是未访问过且 image[x][y] == originColor
            visited[x][y] = true;
            int surround = fill(image, x - 1, y, originColor, newColor)
                    + fill(image, x + 1, y, originColor, newColor)
                    + fill(image, x, y - 1, originColor, newColor)
                    + fill(image, x, y + 1, originColor, newColor);
            //区域内部的坐标探索四周后得到的 surround 是 4，而边界的坐标要么遇到其他颜色，要么超出边界索引，surround 会小于 4。
            if (surround < 4) {
                // unchose :将标记替换为 newColor
                image[x][y] = newColor;
            }
            return 1;

        }

        boolean inArea(int[][] image, int x, int y) {
            return x >= 0 && x < image.length
                    && y >= 0 && y < image[0].length;
        }
    }
}
