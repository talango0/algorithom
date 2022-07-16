package leetcode.graph;
//给你一个points 数组，表示 2D 平面上的一些点，其中 points[i] = [xi, yi] 。
//
// 连接点 [xi, yi] 和点 [xj, yj] 的费用为它们之间的 曼哈顿距离 ：|xi - xj| + |yi - yj| ，其中 |val| 表示
//val 的绝对值。
//
// 请你返回将所有点连接的最小总费用。只有任意两点之间 有且仅有 一条简单路径时，才认为所有点都已连接。
//
//
//
// 示例 1：
//
//
//
//
//输入：points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
//输出：20
//解释：
//
//我们可以按照上图所示连接所有点得到最小总费用，总费用为 20 。
//注意到任意两个点之间只有唯一一条路径互相到达。
//
//
// 示例 2：
//
//
//输入：points = [[3,12],[-2,5],[-4,1]]
//输出：18
//
//
// 示例 3：
//
//
//输入：points = [[0,0],[1,1],[1,0],[-1,1]]
//输出：4
//
//
// 示例 4：
//
//
//输入：points = [[-1000000,-1000000],[1000000,1000000]]
//输出：4000000
//
//
// 示例 5：
//
//
//输入：points = [[0,0]]
//输出：0
//
//
//
//
// 提示：
//
//
// 1 <= points.length <= 1000
// -10⁶ <= xi, yi <= 10⁶
// 所有点 (xi, yi) 两两不同。
//
// Related Topics 并查集 数组 最小生成树 👍 219 👎 0


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class _1584_连接所有点的最小费用 {
    class Solution {
        public int minCostConnectPoints(int[][] points) {
            int n = points.length;
            // 生成所有边及权重
            List<int []> edges = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = i+1; j < n; j++) {
                    int xi = points[i][0], yi = points[i][1];
                    int xj = points[j][0], yj = points[j][1];
                    // 用坐标点在 points 中的索引表示坐标点
                    edges.add(new int [] {
                            i, j, Math.abs(xi-xj) + Math.abs(yi-yj)
                    });
                }
            }

            //将边按权重从小到大排列
            Collections.sort(edges, (a, b)->a[2] - b[2]);
            // 执行 kruskal 算法
            int mst = 0;
            UF uf = new UF(n);
            for (int [] edge: edges) {
                int u = edge[0];
                int v = edge[1];
                int weight = edge[2];
                //若这条边会产生环，则不能加入 mst
                if (uf.connected(u,v)) {
                    continue;
                }
                // 若这条边不会产生环，则属于最小生成树
                mst += weight;
                uf.union(u, v);
            }
            return mst;

        }
        class UF {
            // 连通分量个数
            private int count;
            // 存储每个节点的父节点
            private int[] parent;

            // n 为图中节点的个数
            public UF(int n) {
                this.count = n;
                parent = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                }
            }

            // 将节点 p 和节点 q 连通
            public void union(int p, int q) {
                int rootP = find(p);
                int rootQ = find(q);

                if (rootP == rootQ)
                    return;

                parent[rootQ] = rootP;
                // 两个连通分量合并成一个连通分量
                count--;
            }

            // 判断节点 p 和节点 q 是否连通
            public boolean connected(int p, int q) {
                int rootP = find(p);
                int rootQ = find(q);
                return rootP == rootQ;
            }

            public int find(int x) {
                if (parent[x] != x) {
                    parent[x] = find(parent[x]);
                }
                return parent[x];
            }

            // 返回图中的连通分量个数
            public int count() {
                return count;
            }
        }
    }
}
