package leetcode.design;
//有 n 个城市通过一些航班连接。给你一个数组 flights ，其中 flights[i] = [fromi, toi, pricei] ，表示该航班都从城
//市 fromi 开始，以价格 pricei 抵达 toi。
//
// 现在给定所有的城市和航班，以及出发城市 src 和目的地 dst，你的任务是找到出一条最多经过 k 站中转的路线，使得从 src 到 dst 的 价格最便
//宜 ，并返回该价格。 如果不存在这样的路线，则输出 -1。
//
//
//
// 示例 1：
//
//
//输入:
//n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
//src = 0, dst = 2, k = 1
//输出: 200
//解释:
//城市航班图如下
//
//
//从城市 0 到城市 2 在 1 站中转以内的最便宜价格是 200，如图中红色所示。
//
// 示例 2：
//
//
//输入:
//n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
//src = 0, dst = 2, k = 0
//输出: 500
//解释:
//城市航班图如下
//
//
//从城市 0 到城市 2 在 0 站中转以内的最便宜价格是 500，如图中蓝色所示。
//
//
//
// 提示：
//
//
// 1 <= n <= 100
// 0 <= flights.length <= (n * (n - 1) / 2)
// flights[i].length == 3
// 0 <= fromi, toi < n
// fromi != toi
// 1 <= pricei <= 10⁴
// 航班没有重复，且不存在自环
// 0 <= src, dst, k < n
// src != dst
//
//
// Related Topics 深度优先搜索 广度优先搜索 图 动态规划 最短路 堆（优先队列） 👍 505 👎 0


/**
 * @author mayanwei
 * @date 2022-07-27.
 */
public class _787_K站中转内最便宜的航班{
    /**
     * 动态规划法
     */
    class Solution1 {
        // 这里采用动态规划求解
        // 很多求最值的问题都可以使用动态规划来求解
        // minPath(src, dist) = min(
        //  minPath(src, s1)+w1,
        //  minPath(src, s2)+w2
        // )
        //
        // dp(dst, k) = min(
        // dp(s1, k - 1) + w1,
        // dp(s2, k - 1) + w2
        // )

        // 定义：从起点src出发， k 步之内（一步就是一条边）到达节点s的最小路径权重为 dp(s,k)
        int dp(int s, int k) {
            // 从 src 到 src
            if (s == src) {
                return 0;
            }
            // 如果步数用完了，就无解了
            if (k == 0) {
                return -1;
            }

            if (memo[s][k] != -888) {
                return memo[s][k];
            }

            // 初始化为最大值，方便等会儿去最小值
            int res = Integer.MAX_VALUE;
            if (indegree.containsKey(s)) {
                // 当 s 有入度节点时，分解为子问题
                for (int [] v:indegree.get(s)) {
                    int from  = v[0];
                    int price = v[1];
                    // 从 src 到相邻的入度节点所需的最短路径权重
                    int subProblem = dp(from, k-1);
                    // 跳过无解的情况
                    if (subProblem != -1) {
                        res = Math.min(res, subProblem + price);
                    }
                }
            }
            // 如果还是初始值，说明此节点不可达
            memo[s][k] = res == Integer.MAX_VALUE ? -1 : res;
            return memo[s][k];
        }
        // 如何利用备忘录消除子问题
        // 如果某个节点同时指向两个其他节点，那么这两个节点就有相同的一个入度节点，就会产生相同的递归计算。
        // 如何消除？找问题的【状态】，状态就是在问题分解（状态转移）的过程中变化的，就是状态
        int memo [][];

        // 哈希表记录每个点的入度
        // to -> [from, price]
        HashMap<Integer, List<int []>> indegree;
        int src, dst;

        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            // 将中专站的个数转化为边的条数
            k++;
            //...
            this.src = src;
            this.dst = dst;
            // 初始化备忘录，全部填写一个特殊值, k 是从1开始算的，所以备忘录的初始大小要再加1
            memo = new int [n][k+1];
            for (int [] row: memo) {
                Arrays.fill(row, -888);
            }

            indegree = new HashMap();
            for (int [] f : flights) {
                int from = f[0];
                int to = f[1];
                int price = f[2];
                indegree.putIfAbsent(to, new ArrayList<>());
                indegree.get(to).add(new int[]{from, price});
            }

            return dp(dst, k);
        }
    }

    /**
     * BFS法，Dijkstra算法
     */
    class Solution {
        // 求最短路径，肯定可以用到BFS算法解决
        // 因为 BFS 算法相当于从起始点开始，一步一步向外扩散，那当然是离起点越近的节点越先被遍历到，如果 BFS遍历的过程中遇到中带呢，那么走的肯定是最短路径
        // 在多叉树（或者扩展到无权图）的遍历中，与其说没有权重，不如说是权重都是1， 起点和终点之间的路径权重就是它们之间边的条数。
        // 对于加权图，路径中的边的条数和路径的权重并不是正比关系了，需要用到优先级队列【自动排序】的特性，将路径权重较小的节点排在队列的最前面，以此为基础施展BFSBFS，也就变成了 Dijkstra 算法

        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            List<int[]> [] graph = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new LinkedList<>();
            }
            for (int [] edge : flights) {
                int from = edge[0];
                int to = edge[1];
                int price = edge[2];
                graph[from].add(new int[]{to, price});
            }

            // 启动 dijsktra 算法
            // 计算以 src 为起点在 k 次中转到达 dst 的最短路径
            K++;
            return dijsktra(graph, src, K, dst);
        }

        class State {
            // 图中的节点
            int id;
            // 从 src 节点到达当前节点的花费
            int costFromSrc;
            // 从 src 节点到当前节点的节点数
            int nodeNumFromSrc;

            State(int id, int costFromSrc, int nodeNumFromSrc){
                this.id = id;
                this.costFromSrc = costFromSrc;
                this.nodeNumFromSrc = nodeNumFromSrc;
            }
        }
        // 输入一个起点 src，计算从 src 到其他节点的最短距离
        public int dijsktra(List<int []>[] graph, int src, int K, int dst) {
            // 定义：从起点 src 到达节点 i 的最短路径权重为 distTo[i]
            int [] distTo = new int[graph.length];
            // 定义：从起点 src 到达节点 i 至少要经过 nodeNumTo[i] 个节点
            int [] nodeNumTo = new int[graph.length];
            Arrays.fill(distTo, Integer.MAX_VALUE);
            Arrays.fill(nodeNumTo, Integer.MAX_VALUE);

            // base case
            distTo[src] = 0;
            nodeNumTo[src] = 0;

            // 优先级队列， costFromSrc 较小的排在前面
            Queue<State> pq = new PriorityQueue<>((s1,s2)-> s1.costFromSrc-s2.costFromSrc);
            // 从起点 src 开始进行 BFS
            pq.offer(new State(src, 0, 0));

            while (!pq.isEmpty()) {
                State curState = pq.poll();
                int curNodeId = curState.id;
                int curCostFromSrc = curState.costFromSrc;
                int curNodeNumFromSrc = curState.nodeNumFromSrc;
                if (curNodeId == dst) {
                    // 找到最短路径
                    return curCostFromSrc;
                }
                if (curNodeNumFromSrc == K) {
                    // 中转次数耗尽
                    continue;
                }

                // 将 curNode 的相邻节点转入队列
                for (int [] neighbor : graph[curNodeId]) {
                    int nextNodeID = neighbor[0];
                    int costToNextNode = curCostFromSrc + neighbor[1];
                    // 中转次数消耗1
                    int nextNodeNumFromSrc = curNodeNumFromSrc + 1;

                    // 更新 dp table
                    if (distTo[nextNodeID] > costToNextNode) {
                        distTo[nextNodeID] = costToNextNode;
                        nodeNumTo[nextNodeID] = nextNodeNumFromSrc;
                    }

                    // 剪枝，如果中转次数更多，花费还更大，那必然不会是最短路径
                    if (costToNextNode > distTo[nextNodeID] && nextNodeNumFromSrc > nodeNumTo[nextNodeID]) {
                        continue;
                    }

                    pq.offer(new State(nextNodeID, costToNextNode, nextNodeNumFromSrc));

                }
            }
            return -1;
        }

    }
}
