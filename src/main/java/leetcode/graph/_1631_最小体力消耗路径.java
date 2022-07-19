package leetcode.graph;
//你准备参加一场远足活动。给你一个二维 rows x columns 的地图 heights ，其中 heights[row][col] 表示格子 (row,
// col) 的高度。一开始你在最左上角的格子 (0, 0) ，且你希望去最右下角的格子 (rows-1, columns-1) （注意下标从 0 开始编号）。你
//每次可以往 上，下，左，右 四个方向之一移动，你想要找到耗费 体力 最小的一条路径。
//
// 一条路径耗费的 体力值 是路径上相邻格子之间 高度差绝对值 的 最大值 决定的。
//
// 请你返回从左上角走到右下角的最小 体力消耗值 。
//
//
//
// 示例 1：
//
//
//
//
//输入：heights = [[1,2,2],[3,8,2],[5,3,5]]
//输出：2
//解释：路径 [1,3,5,3,5] 连续格子的差值绝对值最大为 2 。
//这条路径比路径 [1,2,2,2,5] 更优，因为另一条路径差值最大值为 3 。
//
//
// 示例 2：
//
//
//
//
//输入：heights = [[1,2,3],[3,8,4],[5,3,5]]
//输出：1
//解释：路径 [1,2,3,4,5] 的相邻格子差值绝对值最大为 1 ，比路径 [1,3,5,3,5] 更优。
//
//
// 示例 3：
//
//
//输入：heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
//输出：0
//解释：上图所示路径不需要消耗任何体力。
//
//
//
//
// 提示：
//
//
// rows == heights.length
// columns == heights[i].length
// 1 <= rows, columns <= 100
// 1 <= heights[i][j] <= 10⁶
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 二分查找 矩阵 堆（优先队列） 👍 298 👎 0

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class _1631_最小体力消耗路径 {
    class Solution {
        // 思路，常见的二维矩阵题目，如果把二维数组中的每个（x,y）坐标看作一个节点，他的上下左右就是相邻节点，它对应的值和相邻坐标对应的值的差的绝对值就是题目中的【体力消耗】，可以理解为权重。
        // 就是从左上角坐标为起点，以右下角坐标为终点，计算起点到终点的最短路径，
        // 只不过，这道题评判一条路径是长是段不再是路径经过的权重总和，而是路径经过的权重最大值。
        public int minimumEffortPath(int[][] heights) {
            int m = heights.length, n = heights[0].length;
            // 定义从 (0,0) 到 (i,j) 的最小体力消耗值
            int [][] effortTo = new int[m][n];

            // dp table 初始化为无穷大
            for (int i = 0; i<m ; i++) {
                for (int j = 0; j<n; j++) {
                    effortTo[i][j] = Integer.MAX_VALUE;
                }
            }
            // base case
            effortTo[0][0] = 0;

            // 优先级队列
            Queue<State> pq = new PriorityQueue<>((a, b)->a.effortFromStart-b.effortFromStart);

            // 从起点(0,0)开始进行BFS
            pq.offer(new State(0, 0, 0));
            while (!pq.isEmpty()) {
                State curState = pq.poll();
                int curX = curState.x;
                int curY = curState.y;
                int curEffortFromStart = curState.effortFromStart;
                // 到达终点提前结束
                if ( curX == m-1 && curY == n-1) {
                    return curEffortFromStart;
                }

                if (curEffortFromStart > effortTo[curX][curY]) {
                    continue;
                }

                // 将 (curX, curY) 的相邻坐标装入队列
                for (int [] neighbor : adj(heights, curX, curY)) {
                    int x = neighbor[0];
                    int y = neighbor[1];
                    // 计算从 (curX, curY) 达到 (x, y) 的消耗
                    int effortToNextNode = Math.max(effortTo[curX][curY], Math.abs(heights[curX][curY] - heights[x][y]));
                    //更新 dp table
                    if (effortToNextNode < effortTo[x][y]) {
                        effortTo[x][y] = effortToNextNode;
                        pq.offer(new State(x, y, effortToNextNode));
                    }
                }
            }
            //正常情况下不会到这个return
            return -1;
        }
        //方向数组，上下左右相邻坐标
        int [][] dirs = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};

        List<int []> adj(int [][] matrix, int x, int y) {
            int m = matrix.length, n = matrix[0].length;
            // 存储相邻节点
            List<int[]> neighbors = new ArrayList<>();
            for (int [] dir:dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                if (nx >= m || nx < 0 || ny >= n || ny < 0) {
                    // 索引越界
                    continue;
                }
                neighbors.add(new int[]{nx, ny});
            }
            return neighbors;
        }
        class State{
            // 矩阵中的一个位置
            int x, y;
            // 从起点 (0,0) 到当前位置的最小体力消耗值
            int effortFromStart;
            State(int x, int y, int effortFromStart) {
                this.x = x;
                this.y = y;
                this.effortFromStart = effortFromStart;
            }
        }
    }
}
