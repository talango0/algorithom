package leetcode.graph;
//想象一下你是个城市基建规划者，地图上有 N 座城市，它们按以 1 到 N 的次序编号。
//
//给你一些可连接的选项 conections，其中每个选项 `conections[i] = [city1, city2, cost]` 表示将城市 city1 和城市 city2 连接所要的成本。
// （连接是双向的，也就是说城市 city1 和城市 city2 相连也同样意味着城市 city2 和城市 city1 相连）。
//
//返回使得每对城市间都存在将它们连接在一起的连通路径（可能长度为 1 的）**最小成本**。 该最小成本应该是所用全部连接代价的综合。
// 如果根据已知条件无法完成该项任务，则请你返回 -1。
//
//例如：
//输入：N = 3, conections = [[1,2,5],[1,3,6],[2,3,1]]
//输出：6
//解释：
//选出任意 2 条边都可以连接所有城市，我们从中选取成本最小的 2 条。

import inductiontoalgorithm.graph.Prim;
import inductiontoalgorithm.union_find.UF;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @see _1584_连接所有点的最小费用
 * @see _1135_最低成本联通所有城市
 */
public class _1135_最低成本联通所有城市 {
    class Solution {
        int minimumCost(int n, int[][] connections) {
            // 城市的编号为1...n, 所以初始化大小为 n+1
            UF uf = new UF(n + 1);
            // 对所有边按照权重从小到大排序
            Arrays.sort(connections, (a, b) -> a[2] - b[2]);
            // 记录最小生成树的权重之和
            int mst = 0;
            for (int[] edges : connections) {
                int u = edges[0];
                int v = edges[1];
                int weight = edges[2];
                // 若这条边边构成环，则不能加入 mst
                if (uf.connected(u, v)) {
                    continue;
                }
                // 若这条边不会产生环，则属于最小生成树
                mst += weight;
                uf.union(u, v);
            }
            // 保证所有节点都被连通
            // 按理说，uf.count() == 1 说明所有节点被连通
            // 但因为节点0 没有被使用，所以 0 会额外占领一个连通分量
            return uf.count() == 2 ? mst : -1;
        }
    }

    class Solution2 {
        int minimumCost(int n, int[][] connections) {
            List<int []>[] graph = buildGraph(n, connections);
            // 执行 prim 算法
            Prim prim = new Prim(graph);
            if (!prim.allConnected()) {
                return -1;
            }
            return prim.weightSum();
        }
        List<int []>[] buildGraph(int n, int[][] connections) {
            List<int []>[] graph = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new LinkedList<>();
            }
            for (int [] edge : connections) {
                // 题目给定的节点编号是从 1 开始的
                // 但我们的 Prim 算法需要从 0 开始
                int u = edge[0] - 1, v = edge[1] - 1, weight = edge[2];
                // 【无向边】其实就是双向图
                // 一条边表示为 int[]{from, to, weight}
                graph[u].add(new int[]{u, v, weight});
                graph[v].add(new int[]{v, u, weight});
            }
            return graph;
        }

    }
}