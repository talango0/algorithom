package leetcode.dfs;
//261. Graph Valid Tree 图验证树
//
//
// Given n nodes labeled from 0 to n-1 and a list of undirected edges (each edge is a pair of nodes),
// write a function to check whether these edges make up a valid tree.
//
//Example 1:
//
// n = 5
// edges = [[0,1], [0,2], [0,3], [1,4]]
// Example 2:
//
// n = 5,
// edges = [[0,1], [1,2], [2,3], [1,3], [1,4]]
//
// Note: you can assume that no duplicate edges will appear in edges. Since all edges are undirected,
// [0,1] is the same as [1,0] and thus will not appear together in edges.

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-08-19.
 */
public class _261_图验证树{

    /**
     *
     */
    class Solution{
        public boolean validTree(int n, int[][] edges) {
            return edges.length + 1 == n;
        }
    }

    // 思路 ：这道题给了一个无向图，让我们来判断其是否为一棵树，如果是树的话，所有的节点必须是连接的，也就是说必须是连通图，而且不能有环，
    // 所以焦点就变成了验证是否是连通图和是否含有环。

    /**
     * DFS
     * 根据 pair 来建立一个图的结构，用邻接链表来表示，还需要一个一位数组v来记录某个结点是否被访问过，然后用 DFS 来搜索结点0，遍历的思想是，
     * 当 DFS 到某个结点，先看当前结点是否被访问过，如果已经被访问过，说明环存在，直接返回 false，如果未被访问过，现在将其状态标记为已访问过，
     * 然后到邻接链表里去找跟其相邻的结点继续递归遍历，注意还需要一个变量 pre 来记录上一个结点，以免回到上一个结点，这样遍历结束后，
     * 就把和结点0相邻的节点都标记为 true，然后再看v里面是否还有没被访问过的结点，如果有，则说明图不是完全连通的，返回 false，反之返回 true
     */
    class Solution2{

        boolean[] visited;

        public boolean validTree(int n, int[][] edges) {
            visited = new boolean[n];
            List<Integer>[] graphs = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                graphs[i] = new LinkedList<>();
            }
            for (int[] edge : edges) {
                int v1 = edge[0];
                int v2 = edge[1];
                // 无向图
                graphs[v1].add(v2);
                graphs[v2].add(v1);
            }

            if (!dfs(graphs, 0, -1)) {
                return false;
            }

            for (boolean v : visited) {
                if (!v) {
                    return false;
                }
            }
            return true;
        }

        private boolean dfs(List<Integer>[] graphs, int v, int pre) {
            if (visited[v]) {
                return false;
            }
            visited[v] = true;
            List<Integer> graph = graphs[v];
            for (int nextV : graph) {
                if (nextV != pre && !dfs(graphs, nextV, v)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * BFS
     * 需要用 queue 来辅助遍历，这里没有用一维向量来标记节点是否访问过，而是用了一个 HashSet，如果遍历到一个节点，在 HashSet 中没有，
     * 则加入 HashSet，如果已经存在，则返回false，还有就是在遍历邻接链表的时候，遍历完成后需要将结点删掉
     */
    class Solution3{
        public boolean validTree(int n, int[][] edges) {
            HashSet<Integer>[] graphs = new HashSet[n];
            for (int i = 0; i < n; i++) {
                graphs[i] = new HashSet<>();
            }
            for (int[] edge : edges) {
                int v1 = edge[0];
                int v2 = edge[1];
                // 无向图
                graphs[v1].add(v2);
                graphs[v2].add(v1);
            }
            Set<Integer> s = new HashSet<>();
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(0);
            s.add(0);
            while (!queue.isEmpty()) {
                Integer v = queue.poll();
                HashSet<Integer> nextVs = graphs[v];
                for (Integer nextV : nextVs) {
                    if (s.contains(nextV)) {
                        return false;
                    }
                    s.add(nextV);
                    queue.offer(nextV);
                    graphs[nextV].remove(v);
                }
            }
            return s.size() == n;
        }

    }


    class Solution4{
        public boolean validTree(int n, int[][] edges) {
            if (n == 0) {
                return false;
            }
            // 判断是否满足条件：1. 刚好N-1条边
            if (edges.length != n - 1) {
                return false;
            }
            Map<Integer, Set<Integer>> graph = initializeGraph(n, edges);

            //bfs
            Queue<Integer> queue = new LinkedList<>();
            Set<Integer> hash = new HashSet<>();//所有进去过的点

            queue.offer(0); //从0出发 把0加入队列
            hash.add(0);
            while (!queue.isEmpty()) {
                int node = queue.poll();//去除队列第一个
                for (Integer neighbor : graph.get(node)) { //找所有的邻居
                    //重复判断
                    if (hash.contains(neighbor)) {
                        continue;//有重复
                    }
                    hash.add(neighbor);
                    queue.offer(neighbor);
                }
            }
            return (hash.size() == n);
        }

        //邻接表  map 键值对中 键为某一个点 值为这个点所有的邻居(用set存储)
        private Map<Integer, Set<Integer>> initializeGraph(int n, int[][] edges) {
            Map<Integer, Set<Integer>> graph = new HashMap<>();
            for (int i = 0; i < n; i++) {
                graph.put(i, new HashSet<Integer>());
            }
            for (int i = 0; i < edges.length; i++) {
                int u = edges[i][0];
                int v = edges[i][1];
                graph.get(u).add(v);
                graph.get(v).add(u);
            }
            return graph;
        }
    }


    class Solution5{
        public class UnionFind{
            int[] parents;
            int[] ranks;

            public UnionFind(int n) {
                parents = new int[n];
                ranks = new int[n];
                for (int i = 0; i < n; i++) {
                    parents[i] = i;
                    ranks[i] = 1;
                }
            }

            public boolean union(int x, int y) {
                int xParent = find(x);
                int yParent = find(y);
                // Same Parent
                if (xParent == yParent) {
                    return false;
                }
                // different parent
                if (ranks[xParent] > ranks[yParent]) {
                    parents[yParent] = xParent;
                }
                else if (ranks[xParent] < ranks[yParent]) {
                    parents[xParent] = yParent;
                }
                else {
                    parents[yParent] = xParent;
                    ranks[xParent]++;
                }
                return true;
            }

            public int find(int node) {
                int parentNode = parents[node];
                while (parentNode != node) {
                    parentNode = parents[parentNode];
                    parents[node] = parentNode;
                    node = parentNode;
                }
                return node;
            }
        }

        UnionFind node;

        public boolean validTree(int n, int[][] edges) {
            if (edges.length + 1 < n) {
                return false;
            }
            node = new UnionFind(n);
            for (int[] edge : edges) {
                if (!node.union(edge[0], edge[1])) {
                    return false;
                }
            }
            return true;
        }
    }

    @Test
    public void test() {
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {1, 3}, {1, 4}};
        //int[][] edges = {{0, 1}, {0, 2}, {0, 3}, {1, 4}};
        Solution solution = new Solution();
        boolean res = solution.validTree(5, edges);
        System.out.println(res);

        Solution2 solution2 = new Solution2();
        boolean res1 = solution2.validTree(5, edges);
        System.out.println(res1);

        Solution3 solution3 = new Solution3();
        boolean res3 = solution3.validTree(5, edges);
        System.out.println(res3);

        Solution4 solution4 = new Solution4();
        boolean res4 = solution4.validTree(5, edges);
        System.out.println(res4);

        Solution5 solution5 = new Solution5();
        boolean res5 = solution5.validTree(5, edges);
        System.out.println(res5);
    }
}
