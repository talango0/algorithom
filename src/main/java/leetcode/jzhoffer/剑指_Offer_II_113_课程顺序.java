package leetcode.jzhoffer;
//现在总共有 numCourses门课需要选，记为0到numCourses-1。
//
//给定一个数组prerequisites ，它的每一个元素prerequisites[i]表示两门课程之间的先修顺序。例如prerequisites[i] = [ai, bi]表示想要学习课程 ai，需要先完成课程 bi。
//
//请根据给出的总课程数 numCourses 和表示先修顺序的prerequisites得出一个可行的修课序列。
//
//可能会有多个正确的顺序，只要任意返回一种就可以了。如果不可能完成所有课程，返回一个空数组。
//
//
//
//示例1:
//
//输入: numCourses = 2, prerequisites = [[1,0]] 
//输出: [0,1]
//解释:总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
//示例2:
//
//输入: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
//输出: [0,1,2,3] or [0,2,1,3]
//解释:总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
//因此，一个正确的课程顺序是[0,1,2,3] 。另一个正确的排序是[0,2,1,3] 。
//示例 3:
//
//输入: numCourses = 1, prerequisites = [] 
//输出: [0]
//解释:总共 1 门课，直接修第一门课就可。
//
//
//提示:
//
//1 <= numCourses <= 2000
//0 <= prerequisites.length <= numCourses * (numCourses - 1)
//prerequisites[i].length == 2
//0 <= ai, bi < numCourses
//ai != bi
//prerequisites中不存在重复元素
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/QA2IGt
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.graph._210_课程表2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @see _210_课程表2
 */
public class 剑指_Offer_II_113_课程顺序 {
    class Solution {
        // 用 visited 防止重复遍历一个节点
        boolean [] visited;
        // 把 traverse 看作在图中节点上游走的指针，只需要在添加一个布尔数组 onPath 记录当前
        // traverse 经过的路径
        boolean [] onPath;
        boolean hasCycle;
        List<Integer> postOrder = new ArrayList<>();
        public int[] findOrder(int numCourses, int[][] prerequisites) {
            // 后序遍历的结果就是拓扑排序的结果
            List<Integer> [] graph = buildGraph(numCourses, prerequisites);
            visited = new boolean[numCourses];
            onPath = new boolean[numCourses];
            // 遍历图
            for (int i = 0; i < numCourses; i++) {
                traverse(graph, i);
            }
            // 有换图无法进行拓扑排序
            if (hasCycle) {
                return new int []{};
            }
            // 逆后序遍历的结果即为拓扑排序
            // Collections.reverse(postorder);
            int[] res = new int[numCourses];
            for (int i = 0; i< numCourses; i++) {
                res[i] = postOrder.get(i);
            }
            return res;
        }
        List<Integer> [] buildGraph(int numCourses, int [][] prerequisites) {
            List<Integer>[] graph = new LinkedList[numCourses];
            for (int i = 0; i < numCourses; i++){
                graph[i] = new LinkedList<>();
            }
            for (int[] edge : prerequisites) {
                int from = edge[0], to = edge[1];
                //添加一条从from指向to的有向边
                //边的方向是【被依赖】的关系，即修完课程from，才能修课程to
                graph[from].add(to);
            }
            return graph;
        }

        void traverse(List<Integer> [] graph, int s) {
            if (onPath[s]) {
                hasCycle = true;
            }
            if (visited[s]) {
                return;
            }
            visited[s] = true;
            onPath[s] = true;
            for (int to : graph[s]) {
                traverse(graph,to);
            }
            postOrder.add(s);
            onPath[s] = false;
        }
    }
}
