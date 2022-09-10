package leetcode.arrays;
//给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。
//
//
//
// 示例 1:
//
//
//
//
//输入: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
//输出: 4
//解释: 下雨后，雨水将会被上图蓝色的方块中。总的接雨水量为1+2+1=4。
//
//
// 示例 2:
//
//
//
//
//输入: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
//输出: 10
//
//
//
//
// 提示:
//
//
// m == heightMap.length
// n == heightMap[i].length
// 1 <= m, n <= 200
// 0 <= heightMap[i][j] <= 2 * 10⁴
//
//
//
//
// Related Topics 广度优先搜索 数组 矩阵 堆（优先队列） 👍 627 👎 0


import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-09-10.
 * @see _42_接雨水
 */
public class _407_接雨水II{
    class Solution{
        //时间复杂度 O(MN*log(MN))
        //空间复杂度 O(MN)
        public int trapRainWater(int[][] heightMap) {
            if (heightMap.length <= 2 || heightMap[0].length <= 2) {
                return 0;
            }
            int m = heightMap.length, n = heightMap[0].length;
            boolean[][] visited = new boolean[m][n];
            PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                        // 可以确定最外层的木桶接水后的高度为 water[i][j] = heightMap[i][j]
                        pq.offer(new int[]{i * n + j, heightMap[i][j]});
                        visited[i][j] = true;
                    }
                }
            }
            int res = 0;
            int[] dirs = new int[]{-1, 0, 1, 0, -1};
            while (!pq.isEmpty()) {
                int[] curr = pq.poll();

                for (int k = 0; k < 4; ++k) {
                    int nx = curr[0] / n + dirs[k];
                    int ny = curr[0] % n + dirs[k + 1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                        if (curr[1] > heightMap[nx][ny]) {
                            // 方块 (nx,ny) 的实际节水量
                            res += (curr[1] - heightMap[nx][ny]);
                        }
                        pq.offer(new int[]{nx * n + ny, Math.max(heightMap[nx][ny], curr[1])});
                        visited[nx][ny] = true;
                    }

                }
            }
            return res;
        }
    }
}
