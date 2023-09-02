package leetcode.jzhoffer;

import leetcode.graph._207_课程表;

/**
 * @see _207_课程表
 * @see 剑指_Offer_II_035_最小时间差
 */
public class 剑指_Offer_II_118_多余的边{
    class Solution{
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
                }
                else {
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
