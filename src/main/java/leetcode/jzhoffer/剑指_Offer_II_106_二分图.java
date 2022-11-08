package leetcode.jzhoffer;

import leetcode.graph._785_判断二分图;
import leetcode.graph._886_可能的二分法;

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _785_判断二分图
 * @see _886_可能的二分法
 */
public class 剑指_Offer_II_106_二分图{
    class Solution{
        //记录图是否符合二分图的性质
        private boolean ok = true;
        //记录图中节点的颜色，true 和 false 代表两种不同颜色
        private boolean[] color;
        //记录图中节点是否被访问过
        private boolean[] visited;

        //输入领接表，判断是否是一棵二分图
        public boolean isBipartite(int[][] graph) {
            int n = graph.length;
            color = new boolean[n];
            visited = new boolean[n];
            // 因为不一定是联通的，可能存在多个子树
            // 所以要把每个节点都作为起点进行一次遍历
            // 如果发现任何一个子图不是二分图，整幅图都不算二分图
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    traverse(graph, v);
                }
            }
            return ok;
        }

        // DFS 遍历
        private void traverse(int[][] graph, int v) {
            // 如果确定不是二分子图，就不再浪费时间递归了
            if (!ok) return;
            visited[v] = true;
            for (int w : graph[v]) {
                if (!visited[w]) {
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
}
