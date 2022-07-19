package leetcode.graph;
//给你一个由 n 个节点（下标从 0 开始）组成的无向加权图，该图由一个描述边的列表组成，其中 edges[i] = [a, b] 表示连接节点 a 和 b
//的一条无向边，且该边遍历成功的概率为 succProb[i] 。
//
// 指定两个节点分别作为起点 start 和终点 end ，请你找出从起点到终点成功概率最大的路径，并返回其成功概率。
//
// 如果不存在从 start 到 end 的路径，请 返回 0 。只要答案与标准答案的误差不超过 1e-5 ，就会被视作正确答案。
//
//
//
// 示例 1：
//
//
//
// 输入：n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.2], start = 0,
//end = 2
//输出：0.25000
//解释：从起点到终点有两条路径，其中一条的成功概率为 0.2 ，而另一条为 0.5 * 0.5 = 0.25
//
//
// 示例 2：
//
//
//
// 输入：n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.3], start = 0,
//end = 2
//输出：0.30000
//
//
// 示例 3：
//
//
//
// 输入：n = 3, edges = [[0,1]], succProb = [0.5], start = 0, end = 2
//输出：0.00000
//解释：节点 0 和 节点 2 之间不存在路径
//
//
//
//
// 提示：
//
//
// 2 <= n <= 10^4
// 0 <= start, end < n
// start != end
// 0 <= a, b < n
// a != b
// 0 <= succProb.length == edges.length <= 2*10^4
// 0 <= succProb[i] <= 1
// 每两个节点之间最多有一条边
//
// Related Topics 图 最短路 堆（优先队列） 👍 105 👎 0


import java.util.*;

public class _1514_概率最大的路径 {
    class Solution {
        // 输入一幅无向图，边上的权重代表概率，返回从 start 到达 end 最大的概率
        // 这道题给的是无向图，也可以用dijsktra 算法吗
        // 更重要的是，dijsktra 算法计算的是最短路径，计算的是最小值，这题计算的是最大概率是多少，如何使用dijkstra
        // 1. 无向图本质上可以认为是双向图，从而转化为有向图
        // 2. dijsktra 计算最短路径正确性的前提：路径中每增加一条边，路径的总权重就会增加
        // 如果你想计算最长路径，路径中没增加一条边，路径的总权重就会减少，要是能满足这个条件，也可以使用 dijsktra
        // 本地正好可以，边和边之间的乘法，因为每条边的概率都是小于1的，所以肯定会越来越小。
        // 只不过，这道题的解法要把优先级队列的排序顺序反过来，一些if大小判断也要反过来。
        public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
            List<double []> [] graph = new LinkedList[n];
            for (int i = 0; i<n ;i++) {
                graph[i] = new LinkedList<>();
            }
            //构造领接表结构表示图
            for (int i = 0; i<edges.length; i++) {
                int from = edges[i][0];
                int to = edges[i][1];
                double weight = succProb[i];
                // 无向图就是双向图，先把int统一转成 double, 带会让再转回来
                graph[from].add(new double[]{(double)to, weight});
                graph[to].add(new double[]{(double)from, weight});
            }
            return dijkstra(start, end, graph);
        }

        class State {
            // 图节点的id
            int id;
            // 从start几点到达当前节点的概率
            double probFromStart;
            State(int id, double probFromStart) {
                this.id = id;
                this.probFromStart = probFromStart;
            }
        }

        double dijkstra(int start, int end, List<double[]>[] graph) {
            // 定义：probTo[i] 的值就是节点 start 到达节点 i 的最大概率
            double[] probTo = new double[graph.length];
            // dp table 初始化为一个取不到的最小值
            Arrays.fill(probTo, -1);
            // base case，start 到 start 的概率就是 1
            probTo[start] = 1;

            // 优先级队列，probFromStart 较大的排在前面
            Queue<State> pq = new PriorityQueue<>((a, b) -> {
                return Double.compare(b.probFromStart, a.probFromStart);
            });
            // 从起点 start 开始进行 BFS
            pq.offer(new State(start, 1));

            while (!pq.isEmpty()) {
                State curState = pq.poll();
                int curNodeID = curState.id;
                double curProbFromStart = curState.probFromStart;

                // 遇到终点提前返回
                if (curNodeID == end) {
                    return curProbFromStart;
                }

                if (curProbFromStart < probTo[curNodeID]) {
                    // 已经有一条概率更大的路径到达 curNode 节点了
                    continue;
                }
                // 将 curNode 的相邻节点装入队列
                for (double[] neighbor : graph[curNodeID]) {
                    int nextNodeID = (int)neighbor[0];
                    // 看看从 curNode 达到 nextNode 的概率是否会更大
                    double probToNextNode = probTo[curNodeID] * neighbor[1];
                    if (probTo[nextNodeID] < probToNextNode) {
                        probTo[nextNodeID] = probToNextNode;
                        pq.offer(new State(nextNodeID, probToNextNode));
                    }
                }
            }
            // 如果到达这里，说明从 start 开始无法到达 end，返回 0
            return 0.0;
        }
    }
}
