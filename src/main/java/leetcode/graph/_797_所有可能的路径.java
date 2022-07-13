package leetcode.graph;

import java.util.LinkedList;
import java.util.List;

//给你一个有 n 个节点的 有向无环图（DAG），请你找出所有从节点 0 到节点 n-1 的路径并输出（不要求按特定顺序）
//
// graph[i] 是一个从节点 i 可以访问的所有节点的列表（即从节点 i 到节点 graph[i][j]存在一条有向边）。
//
//
//
// 示例 1：
//
//
//
//
//输入：graph = [[1,2],[3],[3],[]]
//输出：[[0,1,3],[0,2,3]]
//解释：有两条路径 0 -> 1 -> 3 和 0 -> 2 -> 3
//
//
// 示例 2：
//
//
//
//
//输入：graph = [[4,3,1],[3,2,4],[3],[4],[]]
//输出：[[0,4],[0,3,4],[0,1,3,4],[0,1,2,3,4],[0,1,4]]
//
//
//
//
// 提示：
//
//
// n == graph.length
// 2 <= n <= 15
// 0 <= graph[i][j] < n
// graph[i][j] != i（即不存在自环）
// graph[i] 中的所有元素 互不相同
// 保证输入为 有向无环图（DAG）
//
//
//
// Related Topics 深度优先搜索 广度优先搜索 图 回溯 👍 305 👎 0
public class _797_所有可能的路径 {
    class Solution {
        List<List<Integer>> res = new LinkedList<>();
        //因为是有向无环图，所以不用visted来记录回路
        public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
            //维护递归过程中经过的路径
            LinkedList<Integer> path = new LinkedList<>();
            traverse(graph, 0, path);
            return res;
        }

        public void traverse(int [][] graph, int s, LinkedList<Integer> path) {
            //添加s到路径
            path.addLast(s);
            int n = graph.length;
            if (s == n-1) {
                //到达终点
                res.add(new LinkedList<>(path));
                // 可以在这里直接return,但要removeLast ，正确维护path
                // path.removeLast();
                // return;
                // 不return 也可以，因为图中不含环，不会出现无限递归
            }

            //递归遍历每一个相邻节点
            for (int v:graph[s]) {
                traverse(graph, v, path);
            }

            //从路径中移除节点s
            path.removeLast();

        }

        //总结一下
        // 图的存储方式主要有两种，邻接表和邻接矩阵
        // 在面试中最常考的算法是图的遍历，和多叉树的遍历是类似的
    }
}
