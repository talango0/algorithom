package leetcode.程序员面试金典;
//节点间通路。给定有向图，设计一个算法，找出两个节点之间是否存在一条路径。
//
//示例1:
// 输入：n = 3, graph = [[0, 1], [0, 2], [1, 2], [1, 2]], start = 0, target = 2
// 输出：true
//
//示例2:
// 输入：n = 5, graph = [[0, 1], [0, 2], [0, 4], [0, 4], [0, 1], [1, 3], [1, 4], [1, 3], [2, 3], [3, 4]], start = 0, target = 4
// 输出 true
//提示：
//
//节点数量n在[0, 1e5]范围内。
//节点编号大于等于 0 小于 n。
//图中可能存在自环和平行边。
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/route-between-nodes-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2023-05-06.
 */
public class _04_01_节点间通路{
    class Solution{
        public boolean findWhetherExistsPath(int n, int[][] graph, int start, int target) {
            // edges[i] 表示节点 i 出发的边
            List<Integer>[] edges = buildEdges(n, graph);
            // for(int i = 0; i< edges.length; i++) {
            //     List<Integer> edge = edges[i];
            //     if (edge != null) {
            //         System.out.println(i + "===>" + Arrays.toString(edge.toArray()));
            //     }
            // }
            boolean[] visited = new boolean[n];
            findTarget(target, start, edges, visited);
            return find;
        }

        private List<Integer>[] buildEdges(int n, int[][] graph) {
            List<Integer>[] edges = new ArrayList[n];
            for (int i = 0; i < graph.length; i++) {
                int[] cur = graph[i];
                int start = cur[0];
                if (edges[start] == null) {
                    edges[start] = new ArrayList<>();
                }
                edges[start].add(cur[1]);
            }
            return edges;
        }

        boolean find;

        private void findTarget(Integer target, Integer cur, List<Integer>[] edges, boolean[] visited) {
            if (find) {
                return;
            }
            visited[cur] = true;
            List<Integer> edge = edges[cur];
            if (edge != null) {
                for (Integer next : edge) {
                    if (next.equals(target)) {
                        find = true;
                        // System.out.println(find);
                    }
                    if (!visited[next]) {
                        findTarget(target, next, edges, visited);
                    }
                    // if (next == 606){

                    //     System.out.println(next.equals(target) );
                    //     System.out.println(target);
                    //     System.out.println(target);

                    //     System.out.println("tag 1 cur=" + cur + " target=" + target);
                    //     System.out.println(Arrays.toString(edge.toArray()));
                    // }
                }
            }
            visited[cur] = false;
        }
    }

    class Solution2{
        public boolean findWhetherExistsPath(int n, int[][] graph, int start, int target) {
            boolean[] node = new boolean[n];
            node[start] = true;
            for (int[] edge : graph) {
                if (node[edge[0]]) {
                    node[edge[1]] = true;
                }
            }
            for (int[] edge : graph) {
                if (node[edge[0]]) {
                    if (edge[1] == target) {
                        return true;
                    }
                    node[edge[1]] = true;
                }
            }
            return false;
        }
    }

    @Test
    public void test() {

    }
}
