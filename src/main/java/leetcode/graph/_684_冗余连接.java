package leetcode.graph;
//树可以看成是一个连通且 无环的无向图。
//
//给定往一棵n 个节点 (节点值1～n) 的树中添加一条边后的图。添加的边的两个顶点包含在 1 到 n中间，且这条附加的边不属于树中已存在的边。图的信息记录于长度为 n 的二维数组 edges，edges[i] = [ai, bi]表示图中在 ai 和 bi 之间存在一条边。
//
//请找出一条可以删去的边，删除后可使得剩余部分是一个有着 n 个节点的树。如果有多个答案，则返回数组edges中最后出现的边。
//
//
//
//示例 1：
//
//
//
//输入: edges = [[1,2], [1,3], [2,3]]
//输出: [2,3]
//示例 2：
//
//
//
//输入: edges = [[1,2], [2,3], [3,4], [1,4], [1,5]]
//输出: [1,4]
//
//
//提示:
//
//n == edges.length
//3 <= n <= 1000
//edges[i].length == 2
//1 <= ai< bi<= edges.length
//ai != bi
//edges 中无重复元素
//给定的图是连通的
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/redundant-connection
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.*;

/**
 * @see _207_课程表
 */
public class _684_冗余连接 {
    class Solution1 {
        /**
         * 无向图的拓扑排序
         * <p>
         * 可以去除所有不在环内的节点，在剩余的环内找到最后出现的边。
         * <p>
         * 找环可以用拓扑排序，无向图的拓扑排序可以用栈，将所有度为1 的节点入栈，依次出栈。
         *
         * @param edges
         * @return
         */
        public int[] findRedundantConnection(int[][] edges) {
            int n = edges.length;
            List<List<Integer>> graph = new ArrayList<>(n + 1);
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }
            // 建图
            int[] degree = new int[n + 1];
            for (int[] edge : edges) {
                graph.get(edge[0]).add(edge[1]);
                graph.get(edge[1]).add(edge[0]);
                degree[edge[0]]++;
                degree[edge[1]]++;
            }

            // 无向图的拓扑排序
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = 1; i <= n; i++) {
                if (degree[i] == 1) {
                    stack.push(i);
                }
            }
            while (!stack.isEmpty()) {
                int node = stack.pop();
                for (int x : graph.get(node)) {
                    if ((--degree[x]) == 1) {
                        stack.push(x);
                    }
                }
            }

            // 统计环内的节点
            HashSet<Integer> remain = new HashSet<>();
            for (int i = 1; i <= n; i++) {
                if (degree[i] > 1) {
                    remain.add(i);
                }
            }
            // 找到最后出现的边并返回
            for (int i = n - 1; i >= 0; i--) {
                if (remain.contains(edges[i][0]) && remain.contains(edges[i][1])) {
                    return new int[]{edges[i][0], edges[i][1]};
                }
            }
            return null;
        }
    }

    class Solution2 {
        /**
         * 并查集
         * <p>
         * 使用路径压缩，最后所有的节点父节点都是同一个节点，
         * 如果两个节点加入的时候，已经有了相同的父节点，说明存在环，而这条边也是
         * 最后出现的。
         */
        public int[] findRedundantConnection(int[][] edges) {
            int n = edges.length;
            int[] parent = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
            }
            for (int[] edge : edges) {
                int x = edge[0];
                int y = edge[1];
                if (find(parent, x) != find(parent, y)) {
                    union(parent, x, y);
                } else {
                    return edge;
                }
            }
            return new int[]{};
        }

        private void union(int[] parent, int x, int y) {
            parent[find(parent, x)] = parent[find(parent, y)];
        }

        // 路径压缩
        private int find(int[] parent, int x) {
            if (parent[x] != x) {
                parent[x] = find(parent, parent[x]);
            }
            return parent[x];
        }
    }
}
