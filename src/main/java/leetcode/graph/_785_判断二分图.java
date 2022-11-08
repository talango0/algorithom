package leetcode.graph;
//存在一个 无向图 ，图中有 n 个节点。其中每个节点都有一个介于 0 到 n - 1 之间的唯一编号。给你一个二维数组 graph ，其中 graph[u]
// 是一个节点数组，由节点 u 的邻接节点组成。形式上，对于 graph[u] 中的每个 v ，都存在一条位于节点 u 和节点 v 之间的无向边。该无向图同时具有
//以下属性：
//
// 不存在自环（graph[u] 不包含 u）。
// 不存在平行边（graph[u] 不包含重复值）。
// 如果 v 在 graph[u] 内，那么 u 也应该在 graph[v] 内（该图是无向图）
// 这个图可能不是连通图，也就是说两个节点 u 和 v 之间可能不存在一条连通彼此的路径。
//
//
// 二分图 定义：如果能将一个图的节点集合分割成两个独立的子集 A 和 B ，并使图中的每一条边的两个节点一个来自 A 集合，一个来自 B 集合，就将这个图称
//为 二分图 。
//
// 如果图是二分图，返回 true ；否则，返回 false 。
//
//
//
// 示例 1：
//
//
//输入：graph = [[1,2,3],[0,2],[0,1,3],[0,2]]
//输出：false
//解释：不能将节点分割成两个独立的子集，以使每条边都连通一个子集中的一个节点与另一个子集中的一个节点。
//
// 示例 2：
//
//
//输入：graph = [[1,3],[0,2],[1,3],[0,2]]
//输出：true
//解释：可以将节点分成两组: {0, 2} 和 {1, 3} 。
//
//
//
// 提示：
//
//
// graph.length == n
// 1 <= n <= 100
// 0 <= graph[u].length < n
// 0 <= graph[u][i] <= n - 1
// graph[u] 不会包含 u
// graph[u] 的所有值 互不相同
// 如果 graph[u] 包含 v，那么 graph[v] 也会包含 u
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 图 👍 381 👎 0

import java.util.LinkedList;
import java.util.Queue;

/**
 * @see _886_可能的二分法
 */
public class _785_判断二分图 {
    class Solution {
        //记录图是否符合二分图的性质
        private boolean ok = true;
        //记录图中节点的颜色，true 和 false 代表两种不同颜色
        private boolean[] color;
        //记录图中节点是否被访问过
        private boolean [] visited;
        //输入领接表，判断是否是一棵二分图
        public boolean isBipartite(int[][] graph) {
            int n = graph.length;
            color = new boolean[n];
            visited = new boolean[n];
            // 因为不一定是联通的，可能存在多个子树
            // 所以要把每个节点都作为起点进行一次遍历
            // 如果发现任何一个子图不是二分图，整幅图都不算二分图
            for (int v = 0; v< n; v++) {
                if (!visited[v]) {
                    traverse(graph, v);
                }
            }
            return ok;
        }

        // DFS 遍历
        private void traverse(int [][] graph, int v) {
            // 如果确定不是二分子图，就不再浪费时间递归了
            if (!ok) return;
            visited[v] = true;
            for (int w: graph[v]) {
                if(!visited[w]) {
                    //相邻节点没有被访问过
                    //那么应该给节点w 涂上和节点v不同的颜色
                    color[w] = !color[v];
                    //继续遍历w
                    traverse(graph, w);
                }
                else {

                    //相邻节点w已经被访问过了
                    //根据 v和w 的颜色判断是否是二分图
                    if (color[w] == color[v]) {
                        ok = false;
                    }

                }
            }
        }
    }


    class Solution2{
        // 记录图是否符合二分图性质
        private boolean ok = true;
        // 记录图中节点的颜色，false 和 true 代表两种不同颜色
        private boolean[] color;
        // 记录图中节点是否被访问过
        private boolean[] visited;

        public boolean isBipartite(int[][] graph) {
            int n = graph.length;
            color =  new boolean[n];
            visited =  new boolean[n];

            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    // 改为使用 BFS 函数
                    bfs(graph, v);
                }
            }

            return ok;
        }

        // 从 start 节点开始进行 BFS 遍历
        private void bfs(int[][] graph, int start) {
            Queue<Integer> q = new LinkedList<>();
            visited[start] = true;
            q.offer(start);

            while (!q.isEmpty() && ok) {
                int v = q.poll();
                // 从节点 v 向所有相邻节点扩散
                for (int w : graph[v]) {
                    if (!visited[w]) {
                        // 相邻节点 w 没有被访问过
                        // 那么应该给节点 w 涂上和节点 v 不同的颜色
                        color[w] = !color[v];
                        // 标记 w 节点，并放入队列
                        visited[w] = true;
                        q.offer(w);
                    } else {
                        // 相邻节点 w 已经被访问过
                        // 根据 v 和 w 的颜色判断是否是二分图
                        if (color[w] == color[v]) {
                            // 若相同，则此图不是二分图
                            ok = false;
                            return;
                        }
                    }
                }
            }
        }

    }
}
