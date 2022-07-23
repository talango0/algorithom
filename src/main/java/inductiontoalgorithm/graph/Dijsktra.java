package inductiontoalgorithm.graph;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijsktra {
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
