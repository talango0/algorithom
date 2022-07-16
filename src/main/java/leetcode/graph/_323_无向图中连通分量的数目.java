package leetcode.graph;
//Number of Connected Components in an Undirected Graph
//Problem:
//Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function
// to find the number of connected components in an undirected graph.
//
//Example 1:
//     0          3
//     |          |
//     1 --- 2    4
//Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.
//
//Example 2:
//     0           4
//     |           |
//     1 --- 2 --- 3
//Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.
//
//Note:
//You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same
// as [1, 0] and thus will not appear together in edges.

import inductiontoalgorithm.union_find.UF;

/**
 * @see _990_等式方程的可满足性
 */
public class _323_无向图中连通分量的数目 {
    int computeComponents(int n, int [][] edges ){
        UF uf = new UF(n);
        // 将每个节点进行连通
        for (int [] e:edges) {
            uf.union(e[0], e[1]);
        }
        // 返回连通风量的个数
        return uf.count();
    }
}
