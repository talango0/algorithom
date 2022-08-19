package leetcode.dfs;
//有一幅以 m x n 的二维整数数组表示的图画 image ，其中 image[i][j] 表示该图画的像素值大小。
//
//你也被给予三个整数 sr , sc 和 newColor 。你应该从像素 image[sr][sc] 开始对图像进行 上色填充 。
//
//为了完成 上色工作 ，从初始像素开始，记录初始坐标的 上下左右四个方向上 像素值与初始坐标相同的相连像素点，接着再记录这四个方向上符合条件的像素点与他们对应 四个方向上 像素值与初始坐标相同的相连像素点，……，重复该过程。将所有有记录的像素点的颜色值改为 newColor 。
//
//最后返回 经过上色渲染后的图像 。
//
//示例 1:
//
//
//
//输入: image = [[1,1,1],[1,1,0],[1,0,1]]，sr = 1, sc = 1, newColor = 2
//输出: [[2,2,2],[2,2,0],[2,0,1]]
//解析: 在图像的正中间，(坐标(sr,sc)=(1,1)),在路径上所有符合条件的像素点的颜色都被更改成2。
//注意，右下角的像素没有更改为2，因为它不是在上下左右四个方向上与初始点相连的像素点。
//示例 2:
//
//输入: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, newColor = 2
//输出: [[2,2,2],[2,2,2]]
//提示:
//
//m == image.length
//n == image[i].length
//1 <= m, n <= 50
//0 <= image[i][j], newColor < 216
//0 <= sr < m
//0 <= sc < n
//Related Topics
//
//👍 360, 👎 0

/**
 * @author mayanwei
 * @date 2022-08-19.
 *
 * @see _1034_边界着色
 */
public class _733_图像渲染{
    class Solution{
        /**
         * FloodFill 算法 自动魔棒工具、扫雷
         * <p>
         * (x, y) 为坐标位置
         * <p>
         * 二维矩阵中的遍历，深度优先搜索 DFS、四叉树访问
         * <p>
         * void fill(int x, int y) {
         * fill(x - 1, y); // 上
         * fill(x + 1, y); // 下
         * fill(x, y - 1); // 左
         * fill(x, y + 1); // 右
         * }
         */
        boolean[][] visited;

        public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
            int originColor = image[sr][sc];
            visited = new boolean[image.length][image[0].length];
            fill(image, sr, sc, originColor, newColor);
            return image;
        }


        void fill(int[][] image, int x, int y, int originColor, int newColor) {
            // 出界：超出数组边界
            if (!inArea(image, x, y)) {
                return;
            }
            // 碰壁：遇到其他颜色，超出originColor区域
            if (image[x][y] != originColor) {
                return;
            }

            // 已探索过的 origColor 区域
            if (visited[x][y]) {
                return;
            }
            visited[x][y] = true;
            image[x][y] = newColor;
            fill(image, x, y + 1, originColor, newColor);
            fill(image, x, y - 1, originColor, newColor);
            fill(image, x - 1, y, originColor, newColor);
            fill(image, x + 1, y, originColor, newColor);
        }

        boolean inArea(int[][] image, int x, int y) {
            return x >= 0 && x < image.length
                    && y >= 0 && y < image[0].length;
        }


    }

    /**
     * 用特殊字符代替visited
     */
    class Solution2{
        public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
            int originColor = image[sr][sc];
            fill(image, sr, sc, originColor, newColor);
            return image;
        }


        void fill(int[][] image, int x, int y, int originColor, int newColor) {
            // 出界：超出数组边界
            if (!inArea(image, x, y)) {
                return;
            }
            // 碰壁：遇到其他颜色，超出originColor区域
            if (image[x][y] != originColor) {
                return;
            }

            // 已探索过的 origColor 区域
            if (image[x][y] == -1) {
                return;
            }
            // 这种解决方法是最常用的，相当于使用一个特殊值 -1 代替 visited 数组的作用，达到不走回头路的效果
            // 0 <= image[i][j], newColor < 2^16
            image[x][y] = -1;
            fill(image, x, y + 1, originColor, newColor);
            fill(image, x, y - 1, originColor, newColor);
            fill(image, x - 1, y, originColor, newColor);
            fill(image, x + 1, y, originColor, newColor);
            // unchose :将标记替换为 newColor
            image[x][y] = newColor;
        }

        boolean inArea(int[][] image, int x, int y) {
            return x >= 0 && x < image.length
                    && y >= 0 && y < image[0].length;
        }


    }
}
