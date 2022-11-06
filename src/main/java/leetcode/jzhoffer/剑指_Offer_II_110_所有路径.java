package leetcode.jzhoffer;

import leetcode.graph._797_所有可能的路径;

import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _797_所有可能的路径
 */
public class 剑指_Offer_II_110_所有路径{
    class Solution{
        /**
         * 以 0 为起点遍历，同时记录遍历过的路径，当遍历到终点时将路径记录下来即可。
         * 因为图示有向无环图，所以无需visited数组辅助。
         */
        // 记录所有路径
        List<List<Integer>> res = new LinkedList<>();

        public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
            LinkedList<Integer> path = new LinkedList<>();
            traverse(graph, 0, path);
            return res;
        }

        /**
         * 图的遍历框架
         */
        void traverse(int[][] graph, int s, LinkedList<Integer> path) {
            // 添加节点 s 到路径
            path.addLast(s);
            int n = graph.length;
            if (s == n - 1) {
                res.add(new LinkedList<>(path));
                path.removeLast();
                return;
            }
            // 遍历每一个相连节点
            for (int v : graph[s]) {
                traverse(graph, v, path);
            }
            // 从路径中移除及诶单s
            path.removeLast();
        }
    }
}
