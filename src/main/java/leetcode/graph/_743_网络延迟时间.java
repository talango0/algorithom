package leetcode.graph;
//有 n 个网络节点，标记为 1 到 n。
//
// 给你一个列表 times，表示信号经过 有向 边的传递时间。 times[i] = (ui, vi, wi)，其中 ui 是源节点，vi 是目标节点，
//wi 是一个信号从源节点传递到目标节点的时间。
//
// 现在，从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 -1 。
//
//
//
// 示例 1：
//
//
//
//
//输入：times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
//输出：2
//
//
// 示例 2：
//
//
//输入：times = [[1,2,1]], n = 2, k = 1
//输出：1
//
//
// 示例 3：
//
//
//输入：times = [[1,2,1]], n = 2, k = 2
//输出：-1
//
//
//
//
// 提示：
//
//
// 1 <= k <= n <= 100
// 1 <= times.length <= 6000
// times[i].length == 3
// 1 <= ui, vi <= n
// ui != vi
// 0 <= wi <= 100
// 所有 (ui, vi) 对都 互不相同（即，不含重复边）
//
// Related Topics 深度优先搜索 广度优先搜索 图 最短路 堆（优先队列） 👍 558 👎 0

import java.util.*;

public class _743_网络延迟时间 {

    class Solution {
        // times 记录边和权重，n为节点个数（从1开始），k为起点
        // 计算从 k 发出的信号至少需要多久传遍整幅图。
        public int networkDelayTime(int[][] times, int n, int k) {
            // 节点编号是从1开始的，所以要一个大小为n+1的领接表
            List<int[]> [] graph = new LinkedList[n+1];
            for (int i = 1; i<=n; i++) {
                graph[i] = new LinkedList<>();
            }
            //构造图
            for (int [] edge:times) {
                int from = edge[0];
                int to = edge[1];
                int weight = edge[2];
                //领接表存储图结构，同事存储权重信息 from -> List<(to, weight)>
                graph[from].add(new int[]{to, weight});
            }

            // 启动 dijkstra 算法以节点 k 为起点到其他节点的最短路径
            int [] distTo = dijkstra(k, graph);

            // 找到最长的那一条最短路径
            int res = 0;
            for (int i = 1; i<distTo.length; i++) {
                if (distTo[i] == Integer.MAX_VALUE) {
                    //节点不可达，返回-1
                    return -1;
                }
                res = Math.max(res, distTo[i]);
            }
            return res;
        }
        class State {
            // 图节点Id
            int id;
            // 从 start 节点到当前节点的距离
            int distFromStart;
            State(int id, int distFromStart){
                this.id = id;
                this.distFromStart = distFromStart;
            }
        }

        // 输入一个起点 start，计算从start 到其他节点的最短距离
        int []  dijkstra(int start, List<int []>[]graph) {
            // 定义，distTo[i] 的值就是起点 start 到达节点 i 的最短路径权重。
            int[] distTo = new int[graph.length];
            Arrays.fill(distTo, Integer.MAX_VALUE);
            //base case
            distTo[start] = 0;
            // 优先级队列， distFromStart 较小的排在前面
            Queue<State> pq = new PriorityQueue<State>((a, b)->a.distFromStart-b.distFromStart);

            // 从起点start 开始 BFS
            pq.offer(new State(start, 0));

            while (!pq.isEmpty()) {
                State curState = pq.poll();
                int curNodeId = curState.id;
                int curStateDistFromStart = curState.distFromStart;
                if (distTo[curNodeId] < curStateDistFromStart) {
                    continue;
                }

                // 将curNode 的相邻节点装入队列
                for (int [] neighbor: graph[curNodeId]) {
                    int nextNodeId = neighbor[0];
                    int distToNextNode = distTo[curNodeId] + neighbor[1];
                    // 更新dp table
                    if (distTo[nextNodeId] > distToNextNode) {
                        distTo[nextNodeId] = distToNextNode;
                        pq.offer(new State(nextNodeId, distToNextNode));
                    }
                }
            }
            return distTo;
        }
    }
}
