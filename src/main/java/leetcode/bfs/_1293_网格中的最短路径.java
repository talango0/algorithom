package leetcode.bfs;
//给你一个 m * n 的网格，其中每个单元格不是 0（空）就是 1（障碍物）。每一步，您都可以在空白单元格中上、下、左、右移动。
//
// 如果您 最多 可以消除 k 个障碍物，请找出从左上角 (0, 0) 到右下角 (m-1, n-1) 的最短路径，并返回通过该路径所需的步数。如果找不到这样
//的路径，则返回 -1 。
//
//
//
// 示例 1：
//
//
//
//
//输入： grid = [[0,0,0],[1,1,0],[0,0,0],[0,1,1],[0,0,0]], k = 1
//输出：6
//解释：
//不消除任何障碍的最短路径是 10。
//消除位置 (3,2) 处的障碍后，最短路径是 6 。该路径是 (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3
//,2) -> (4,2).
//
//
// 示例 2：
//
//
//
//
//输入：grid = [[0,1,1],[1,1,1],[1,0,0]], k = 1
//输出：-1
//解释：我们至少需要消除两个障碍才能找到这样的路径。
//
//
//
//
// 提示：
//
//
// grid.length == m
// grid[0].length == n
// 1 <= m, n <= 40
// 1 <= k <= m*n
// grid[i][j] 是 0 或 1
// grid[0][0] == grid[m-1][n-1] == 0
//
//
// Related Topics 广度优先搜索 数组 矩阵 👍 204 👎 0


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author mayanwei
 * @date 2022-09-05.
 */
public class _1293_网格中的最短路径{
    class Solution{
        public int shortestPath(int[][] grid, int k) {
            int m = grid.length;
            int n = grid[0].length;
            // 非法参数处理
            if (validateInputParams(k, m, n)) {
                return -1;
            }
            // 特殊场景处理
            if (m == 1 && n == 1) {
                return 0;
            }
            // 二维标记数组初始状态为-1，值记录剩余消除障碍的次数，剩余次数越多，越有价值（此处贪心，记录局部最优解）
            int[][] visited = new int[m][n];
            for (int i = 0; i < m; i++) {
                Arrays.fill(visited[i], -1);
            }
            //初始步数为0， m = n = 1的特殊场景已经处理
            int minSteps = 0;
            // 初始位置标记已访问，值记录剩余消除障碍物次数 越多越好
            // 1. 对于其他路径到达此点，且剩余消除障碍物次数小于等于当前 -- 减枝
            // 2. 对于其他路径到达此点，且甚于消除障碍物次数大于当前值 -- 取代并入队
            visited[0][0] = k;
            Queue<Point> queue = new LinkedList<>();
            queue.offer(new Point(0, 0, 0));

            // 定义四个方向移动坐标
            int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            // BFS 搜索-队列遍历
            while (!queue.isEmpty()) {
                minSteps++;
                // BFS搜索-遍历相同层级下所有节点
                // 当前队列长度一定要在循环外部定义，循环内部有插入队列操作
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Point current = queue.poll();
                    int x = current.x;
                    int y = current.y;
                    int oneCount = current.oneCount;

                    // 对当前节点四个方向识别处理
                    for (int[] dir : dirs) {
                        int newX = x + dir[0];
                        int newY = y + dir[1];
                        // 越界判断
                        if (newX < 0 || newX >= m || newY < 0 || newY >= n) {
                            continue;
                        }
                        // 搜索到目标节点直接返回结果，按广度优先搜索，层级就是最短步数
                        if (newX == m - 1 && newY == n - 1) {
                            return minSteps;
                        }

                        // 穿越障碍次数已满
                        if (grid[newX][newY] == 1 && oneCount >= k) {
                            continue;
                        }
                        int oneCountNew = grid[newX][newY] == 1 ? oneCount + 1 :oneCount;
                        // 剪枝-节点已经访问过，且当前 visited记录的剩余障碍物消除次数 >= 当前搜索节点层级的剩余消除次数
                        if (visited[newX][newY] != -1 && visited[newX][newY] >= k - oneCountNew) {
                            continue;
                        }
                        else {
                            // 否则，贪心将最优值更新， 并将该层节点入队
                            visited[newX][newY] = k - oneCountNew;
                        }
                        queue.offer(new Point(newX, newY, oneCountNew));
                    }
                }
            }
            // BFS 没有搜索到目标，返回-1
            return -1;
        }

        boolean validateInputParams(int k, int m, int n) {
            return m > 40 || m < 1 || n > 40 || n < 1 || k < 1 || k > m * n;
        }

        class Point{
            int x;
            int y;
            int oneCount;

            public Point(int x, int y, int oneCount) {
                this.x = x;
                this.y = y;
                this.oneCount = oneCount;
            }
        }
    }
}
