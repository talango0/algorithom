package leetcode.graph;
//现在你总共有 numCourses 门课需要选，记为 0 到 numCourses - 1。给你一个数组 prerequisites ，其中
//prerequisites[i] = [ai, bi] ，表示在选修课程 ai 前 必须 先选修 bi 。
//
//
// 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示：[0,1] 。
//
//
// 返回你为了学完所有课程所安排的学习顺序。可能会有多个正确的顺序，你只要返回 任意一种 就可以了。如果不可能完成所有课程，返回 一个空数组 。
//
//
//
// 示例 1：
//
//
//输入：numCourses = 2, prerequisites = [[1,0]]
//输出：[0,1]
//解释：总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
//
//
// 示例 2：
//
//
//输入：numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
//输出：[0,2,1,3]
//解释：总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
//因此，一个正确的课程顺序是 [0,1,2,3] 。另一个正确的排序是 [0,2,1,3] 。
//
// 示例 3：
//
//
//输入：numCourses = 1, prerequisites = []
//输出：[0]
//
//
//
//提示：
//
//
// 1 <= numCourses <= 2000
// 0 <= prerequisites.length <= numCourses * (numCourses - 1)
// prerequisites[i].length == 2
// 0 <= ai, bi < numCourses
// ai != bi
// 所有[ai, bi] 互不相同
//
// Related Topics 深度优先搜索 广度优先搜索 图 拓扑排序 👍 657 👎 0

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class _210_课程表2 {
    class Solution {

        //依赖问题转化为有向图，用领接表表示该图
        private List<Integer> [] graph;
        /**构建一颗有向图 */
        private List<Integer> [] buildGraph(int numCourses, int [][] prerequisites) {
            //图中共有numsCourses门课程
            List<Integer> [] graph = new LinkedList[numCourses];
            for (int i = 0; i<numCourses; i++) {
                graph[i] = new LinkedList<Integer>();
            }

            for (int [] edge: prerequisites) {
                int from = edge[0], to = edge[1];
                //添加一条从from指向to的有向边
                //边的方向是【被依赖】的关系，即修完课程from，才能修课程to
                graph[from].add(to);

            }

            return graph;
        }

        //只要会遍历图，就可以判断是否存在环
        //用visited 防止重复遍历一个节点
        boolean [] visited;

        //把traverse看作在图中节点上游走的指针，只需要再添加一个布尔数组 onPath 记录当前 traverse 经过的路径
        boolean [] onPath;
        boolean hasCycle;
        List<Integer> postorder = new ArrayList<>();
        void traverse(List<Integer> [] graph, int s) {
            if (onPath[s]) {
                hasCycle = true;
            }
            if (visited[s]) {
                return;
            }

            // 前序遍历代码位置
            // 将当前节点标记为已遍历
            visited[s] = true;
            // 开始遍历节点s
            onPath[s] = true;
            for (int to: graph[s]) {
                traverse(graph, to);
            }
            //后序遍历的位置
            //这里有点儿回溯遍历的味道，在进入节点s的时候将 onPath[s] 标记为 true,离开时标记为false，如果发现 onPath[s] 已经被标记过，则说明出现了环。
            postorder.add(s);
            onPath[s] = false;
        }

        public int[] findOrder(int numCourses, int[][] prerequisites) {
            //后序遍历的结果进行反转，就是拓扑排序的结果
            List<Integer>[] graph = buildGraph(numCourses, prerequisites);
            visited = new boolean[numCourses];
            onPath = new boolean[numCourses];
            // 遍历图
            for (int i = 0; i < numCourses; i++) {
                traverse(graph, i);
            }
            // 有环图无法进行拓扑排序
            if (hasCycle) {
                return new int[]{};
            }
            // 逆后序遍历结果即为拓扑排序结果
            // Collections.reverse(postorder);
            int[] res = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                res[i] = postorder.get(i);
            }
            return res;
        }
    }
}
