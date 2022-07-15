package leetcode.graph;
//给定一组 n 人（编号为 1, 2, ..., n）， 我们想把每个人分进任意大小的两组。每个人都可能不喜欢其他人，那么他们不应该属于同一组。
//
// 给定整数 n 和数组 dislikes ，其中 dislikes[i] = [ai, bi] ，表示不允许将编号为 ai 和 bi的人归入同一组。当可以用
//这种方法将所有人分进两组时，返回 true；否则返回 false。
//
//
//
//
//
//
// 示例 1：
//
//
//输入：n = 4, dislikes = [[1,2],[1,3],[2,4]]
//输出：true
//解释：group1 [1,4], group2 [2,3]
//
//
// 示例 2：
//
//
//输入：n = 3, dislikes = [[1,2],[1,3],[2,3]]
//输出：false
//
//
// 示例 3：
//
//
//输入：n = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
//输出：false
//
//
//
//
// 提示：
//
//
// 1 <= n <= 2000
// 0 <= dislikes.length <= 10⁴
// dislikes[i].length == 2
// 1 <= dislikes[i][j] <= n
// ai < bi
// dislikes 中每一组都 不同
//
//
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 图 👍 184 👎 0

import java.util.LinkedList;
import java.util.List;

public class _886_可能的二分法 {
    class Solution {
        //思路：该题考察的就是二分图的判定
        // 如果把每个人都看成图中的节点，相互讨厌的关系看作图中的边，那么 dislikes 就可以构成一幅图

        List<Integer> [] graph;
        boolean ok = true;
        boolean [] color;
        boolean [] visited;
        public boolean possibleBipartition(int n, int[][] dislikes) {
            // 图节点编号从1开始
            color = new boolean[n+1];
            visited = new boolean[n+1];
            //转化成领接表表示图结构
            graph = buildGraph(n+1, dislikes);

            for (int v = 1; v <= n; v ++){
                if (!visited[v]) {
                    traverse(graph, v);
                }
            }
            return ok;
        }

        List<Integer> [] buildGraph(int n, int[][] dislikes) {
            List<Integer> [] graph = new LinkedList[n+1];
            for (int i = 1; i<n; i++) {
                graph[i] = new LinkedList<Integer>();
            }

            for (int [] edge:dislikes){
                int v = edge[0];
                int w = edge[1];
                //无向图相当于双向图
                // v -> w
                graph[v].add(w);
                // w -> v
                graph[w].add(v);
            }
            return graph;
        }

        void traverse(List<Integer>[] graph, int v) {
            if (!ok) return;
            visited[v] = true;
            for (int w:graph[v]) {
                if (!visited[w]) {
                    color[w] = !color[v];
                    traverse(graph, w);
                }
                else {
                    if (color[w] == color[v]) {
                        ok = false;
                    }
                }
            }
        }

    }
}
