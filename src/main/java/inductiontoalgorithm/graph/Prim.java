package inductiontoalgorithm.graph;

import java.util.List;
import java.util.PriorityQueue;

/**
 * 切分定理
 */
public class Prim {
    // 核心数据结构，存储【横切边】的优先级队列
    private PriorityQueue<int []> pq;
    // 类似 visited 数组的作用，记录哪些节点已经成为了最小生成树的一部分。
    private boolean [] inMST;
    // 记录最小生成树的权重和
    private int weightSum;
    // graph 用邻接表表示的一幅图
    // graph[s] 记录节点 s 的所有相邻的边
    // 三元组 int[]{from, to, weight} 表示一条边
    private List<int[]>[] graph;

    public Prim(List<int[]> [] graph) {
        this.graph = graph;
        this.pq = new PriorityQueue<>((a,b)->a[2]-b[2]);
        // 图中有n个节点
        int n = graph.length;
        this.inMST = new boolean[n];

        // 随便从一个点开始切分都可以，我们不妨从节点0开始
        inMST[0] = true;
        cut(0);
        // 不断进行切分，想最小生成树中添加边
        while (!pq.isEmpty()) {
            int[] edge = pq.poll();
            int to = edge[1];
            int weight = edge[2];
            if (inMST[to]) {
                // 节点 to 已经在最小生成树中，跳过，否则这边边会产生环。
                continue;
            }
            // 将边 edge 加入最小生成树
            weightSum += weight;
            inMST[to] = true;
            // 节点 to 加入后，进行新一轮切分，会产生更多横切边
            cut(to);
        }
    }

    /**
     * 将 s 的横切边加入优先队列
     * @param s
     */
    private void cut(int s) {
        // 遍历 s 的邻边
        for (int[] edge : graph[s]) {
            int to = edge[1];
            if (inMST[to]) {
                //相邻节点 to 已经在最下生成树中，跳过，否则这条边会产生环
                continue;
            }
            // 加入横切边队列
            pq.offer(edge);
        }
    }

    /**
     * 最小生成树的权重和
     * @return
     */
    public int weightSum(){
        return weightSum;
    }

    /**
     * 判断最小生成树是否包含图中的所有节点
     */
    public boolean allConnected(){
        for (int i = 0; i<inMST.length; i++) {
            if (!inMST[i]) {
                return false;
            }
        }
        return true;
    }
}
