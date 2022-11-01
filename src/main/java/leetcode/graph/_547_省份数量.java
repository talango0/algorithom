package leetcode.graph;
//有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
//
//省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
//
//给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
//
//返回矩阵中 省份 的数量。
//
//
//
//示例 1：
//
//
//输入：isConnected = [[1,1,0],[1,1,0],[0,0,1]]
//输出：2
//示例 2：
//
//
//输入：isConnected = [[1,0,0],[0,1,0],[0,0,1]]
//输出：3
//
//
//提示：
//
//1 <= n <= 200
//n == isConnected.length
//n == isConnected[i].length
//isConnected[i][j] 为 1 或 0
//isConnected[i][i] == 1
//isConnected[i][j] == isConnected[j][i]
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/number-of-provinces
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.LinkedList;
import java.util.Queue;

/**
 * @see _323_无向图中连通分量的数目
 */
public class _547_省份数量 {
    /**
     * 题目题意是求一个图的连通域的个数，入参为无向图的邻接矩阵。
     * 常规做法可以对图进行深度优先搜索技术、广度优先搜索技术、并查集等方法处理。
     *
     * 深度优先算法和广度优先算法从各个连通域的任一顶点开始遍历整个连通域，遍历的过程中对
     * boolean[] visited 数组进行标记，遍历完当前的连通域后，若仍有顶点未被访问，说明
     * 又是一个新的连通域，使用cnt累计当前遍历过的连通域的数量。
     */
    // dfs
    class Solution1{
        public int findCircleNum(int[][] isConnected) {
            // int[][] isConnected是无向图的邻接矩阵，n为无向图的顶点数量
            int n = isConnected.length;
            // 定义boolean 数组表示顶点是否被访问
            boolean [] visited = new boolean[n];
            // 定义cnt来累计遍历过的连通域的数量
            int cnt = 0;
            for (int i = 0; i < n; i++) {
                // 若当前顶点i未被访问过，说明又是一个新的连通域
                if(!visited[i]) {
                    cnt ++;
                    dfs(i, isConnected, visited);
                }
            }
            return cnt;
        }
        void dfs(int i, int [][] isConnected, boolean[] visited) {
            // 对当前顶点 i 进行访问标记
            visited[i] = true;
            // 继续遍历与顶点i相邻的顶点（使用visited数组防止重复访问）
            for (int j = 0; j < isConnected.length; j++) {
                if (isConnected[i][j] == 1 && !visited[j]) {
                    dfs(j, isConnected, visited);
                }
            }
        }
    }

    // bfs
    class Solution2{
        public int findCircleNum(int[][] isConnected) {
            // int[][] isConnected是无向图的邻接矩阵，n为无向图的顶点数量
            int n = isConnected.length;
            // 定义boolean 数组表示顶点是否被访问
            boolean [] visited = new boolean[n];
            // 定义cnt来累计遍历过的连通域的数量
            int cnt = 0;
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                // 若当前顶点 i 未被访问过，说明又是一个新的连通域，则bfs新的连通域且 cnt+=1
                if (!visited[i]) {
                    cnt ++;
                    queue.offer(i);
                    visited[i] = true;
                    while (!queue.isEmpty()) {
                        int v = queue.poll();
                        for (int w = 0; w < n; w++) {
                            if (isConnected[v][w] == 1 && !visited[w]) {
                                visited[w] = true;
                                queue.offer(w);
                            }
                        }
                    }
                }
            }
            return cnt;
        }
    }

    // 并查集 UnionFind
    class Solution3 {
        public int findCircleNum(int[][] isConnected) {
            int n = isConnected.length;
            // 初始化并查集
            UF uf = new UF(n);
            // 遍历每个顶点，将当前顶点与其邻接点进行合并
            for (int i = 0; i <n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (isConnected[i][j] == 1) {
                        uf.union(i, j);
                    }
                }
            }
            // 返回最终合并后的集合的数量
            return uf.size;
        }
        // 并查集
        class UF{
            int [] roots;
            int size;
            public UF(int n){
                roots = new int[n];
                for (int i = 0; i < n; i++) {
                    roots[i] = i;
                }
                size = n;
            }
            public int find(int i) {
                return roots[i] = roots[i] == i ? i : find(roots[i]);
            }
            public void union(int i, int j) {
                int iRoot = find(i);
                int jRoot = find(j);
                if (iRoot!= jRoot) {
                    roots[iRoot] = jRoot;
                    size --;
                }
            }
        }
    }
}
