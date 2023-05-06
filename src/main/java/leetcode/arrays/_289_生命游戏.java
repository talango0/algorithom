package leetcode.arrays;
//根据 百度百科 ， 生命游戏 ，简称为 生命 ，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
//
// 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态： 1 即为 活细胞 （live），或 0 即为 死细胞
// （dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
//
// 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
// 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
// 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
// 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
//
//
// 下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。给你 m x n 网格面板 board 的当前状态，返
//回下一个状态。
//
//
//
// 示例 1：
//输入：board = [[0,1,0],
//              [0,0,1],
//              [1,1,1],
//              [0,0,0]]
//输出：[[0,0,0],
//      [1,0,1],
//      [0,1,1],
//      [0,1,0]]
//
//
// 示例 2：
//输入：board = [[1,1],[1,0]]
//输出：[[1,1],[1,1]]
//
//
//
//
// 提示：
//
//
// m == board.length
// n == board[i].length
// 1 <= m, n <= 25
// board[i][j] 为 0 或 1
//
//
//
//
// 进阶：
//
//
// 你可以使用原地算法解决本题吗？请注意，面板上所有格子需要同时被更新：你不能先更新某些格子，然后使用它们的更新后的值再更新其他格子。
// 本题中，我们使用二维数组来表示面板。原则上，面板是无限的，但当活细胞侵占了面板边界时会造成问题。你将如何解决这些问题？
//
//
// Related Topics 数组 矩阵 模拟 👍 463 👎 0


/**
 * @author mayanwei
 * @date 2022-09-24.
 * @see _73_矩阵置零
 */
public class _289_生命游戏{
    class Solution{
        /**
         * 为防止污染原始数据，复制一份原始数组。
         * 根据复制数组中另据细胞的状态跟下board中的细胞状态
         * 时间复杂度：O(mn)
         * 空间复杂度：O(mn)
         */
        public void gameOfLife(int[][] board) {
            int[] neighbors = new int[]{0, 1, -1};
            int rows = board.length;
            int cols = board[0].length;

            // 创建复制数组
            int[][] copyboard = new int[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    copyboard[row][col] = board[row][col];
                }
            }

            // 遍历面板的每一个格子
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    int liveNeighbors = 0;
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (!(neighbors[i] == 0 && neighbors[j] == 0)) {
                                int r = (row + neighbors[i]);
                                int c = (col + neighbors[j]);
                                // 查看相邻的细胞是否存活
                                if (r < rows && r >= 0 && (c < cols && c >= 0) && (copyboard[r][c] == 1)) {
                                    liveNeighbors += 1;
                                }
                            }
                        }
                    }

                    //规则1和规则3
                    if ((copyboard[row][col] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)) {
                        board[row][col] = 0;
                    }
                    // 规则4
                    if (copyboard[row][col] == 0 && liveNeighbors == 3) {
                        board[row][col] = 1;
                    }
                }
            }

        }
    }


    class Solution2{
        /**
         * <pre>
         * 使用额外状态：题目中的细胞状态只有两种状态，live(1) 或者 dead(0) ，
         * 但是可以拓展一些复合状态使其包含之前的状态。
         * 例如，如果细胞之前的状态是 0，但是在更新了之后变成了1，就可以定义个复合状态 2。
         * 这样我们看到2，既能知道这个细胞是活的，还能知道之前是死的。
         * 时间复杂度：O(mn)
         * 空间复杂度：O(1)
         * </pre>
         */
        public void gameOfLife(int[][] board) {
            int[] neighbors = new int[]{0, 1, -1};
            int rows = board.length;
            int cols = board[0].length;


            // 遍历面板的每一个格子
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    int liveNeighbors = 0;
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (!(neighbors[i] == 0 && neighbors[j] == 0)) {
                                int r = (row + neighbors[i]);
                                int c = (col + neighbors[j]);
                                // 查看相邻的细胞是否存活
                                if (r < rows && r >= 0 && (c < cols && c >= 0) && (Math.abs(board[r][c]) == 1)) {
                                    liveNeighbors += 1;
                                }
                            }
                        }
                    }

                    //规则1和规则3
                    if ((board[row][col] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)) {
                        // -1表示这个细胞过去是活的现在死了
                        board[row][col] = -1;
                    }
                    // 规则4
                    if (board[row][col] == 0 && liveNeighbors == 3) {
                        // 2表示这个细胞过去是死的现在活了
                        board[row][col] = 2;
                    }

                }
            }
            // 遍历一边 board得到一次更新后的状态
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (board[row][col] > 0) {
                        board[row][col] = 1;
                    }
                    else {
                        board[row][col] = 0;
                    }
                }
            }

        }
    }
}
