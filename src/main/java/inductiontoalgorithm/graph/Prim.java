package inductiontoalgorithm.graph;

import java.util.List;
import java.util.PriorityQueue;
/*
图论的最小生成树问题，就是让你从图中找若干边形成一个边的集合 mst，这些边具有一下性质：
1. 这些边组成的是一个树（树和图的区别在于树不能有环）
2. 这些边形成的树要包含所有节点。
3. 这些边的权重之和要尽可能小。

Kruskal
首先，Kruskal 算法用到了贪心思想，来满足权重之和尽可能小的问题：
先对所有边按照权重从小到大排序，从权重最小的边开始，选择合适的边加入 mst 集合，这样挑出来的边组成的树就是权重和最小的。
其次，Kruskal 算法用到了 Union-Find 并查集算法，来保证挑选出来的这些边组成的一定是一棵「树」，而不会包含环或者形成一片「森林」：
如果一条边的两个节点已经是连通的，则这条边会使树中出现环；如果最后的连通分量总数大于 1，则说明形成的是「森林」而不是一棵「树」。

Prim
首先，Prim 算法也使用贪心思想来让生成树的权重尽可能小，也就是「切分定理」
其次，Prim 算法使用 BFS 算法思想 和 visited 布尔数组避免成环，来保证选出来的边最终形成的一定是一棵树。
Prim 算法不需要事先对所有边排序，而是利用优先级队列动态实现排序的效果，所以我觉得 Prim 算法类似于 Kruskal 的动态过程。

「切分定理」：
对于任意一种「切分」，其中权重最小的那条「横切边」一定是构成最小生成树的一条边。
（切分定理的证明可以采用反证法）
既然每一次「切分」一定可以找到最小生成树中的一条边，那我就随便切呗，每次都把权重最小的「横切边」拿出来加入最小生成树，
直到把构成最小生成树的所有边都切出来为止。
 */

/**
 * 切分定理
 */
public class Prim{
    // 核心数据结构，存储【横切边】的优先级队列
    private PriorityQueue<int[]> pq;
    // 类似 visited 数组的作用，记录哪些节点已经成为了最小生成树的一部分。
    private boolean[] inMST;
    // 记录最小生成树的权重和
    private int weightSum;
    // graph 用邻接表表示的一幅图
    // graph[s] 记录节点 s 的所有相邻的边
    // 三元组 int[]{from, to, weight} 表示一条边
    private List<int[]>[] graph;

    public Prim(List<int[]>[] graph) {
        this.graph = graph;
        this.pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
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
     * <p>
     * 我们思考算法问题时，如果问题的一般情况不好解决，可以从比较简单的特殊情况入手，Prim 算法就是使用的这种思路。
     * <p>
     * 按照「切分」的定义，只要把图中的节点切成两个不重叠且非空的节点集合即可算作一个合法的「切分」，那么我只切出来一个节点，是不是也算是一个合法的「切分」？
     * <p>
     * 是的，这是最简单的「切分」，而且「横切边」也很好确定，就是这个节点的边。
     * <p>
     * 在进行切分的过程中，我们只要不断把新节点的邻边加入横切边集合，就可以得到新的切分的所有横切边。
     * 用一个布尔数组 inMST 辅助，防止重复计算横切边就行了。
     *
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
     *
     * @return
     */
    public int weightSum() {
        return weightSum;
    }

    /**
     * 判断最小生成树是否包含图中的所有节点
     */
    public boolean allConnected() {
        for (int i = 0; i < inMST.length; i++) {
            if (!inMST[i]) {
                return false;
            }
        }
        return true;
    }
}
