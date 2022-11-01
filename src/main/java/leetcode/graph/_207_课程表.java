package leetcode.graph;
//你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
//
// 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表
//示如果要学习课程 ai 则 必须 先学习课程 bi 。
//
//
// 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
//
//
// 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
//
//
//
// 示例 1：
//
//
//输入：numCourses = 2, prerequisites = [[1,0]]
//输出：true
//解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
//
// 示例 2：
//
//
//输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
//输出：false
//解释：总共有 2 门课程。学习课程 1 之前，你需要先完成课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
//
//
//
// 提示：
//
//
// 1 <= numCourses <= 10⁵
// 0 <= prerequisites.length <= 5000
// prerequisites[i].length == 2
// 0 <= ai, bi < numCourses
// prerequisites[i] 中的所有课程对 互不相同
//
// Related Topics 深度优先搜索 广度优先搜索 图 拓扑排序 👍 1334 👎 0

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class _207_课程表 {
    class Solution {
        //依赖问题转化为有向图，用领接表表示该图
        private List<Integer>[] graph;
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
            // 后序遍历的位置
            // 这里有点儿回溯遍历的味道，在进入节点s的时候将 onPath[s] 标记为 true,
            // 离开时标记为false，如果发现 onPath[s] 已经被标记过，则说明出现了环。
            onPath[s] = false;
        }

        public boolean canFinish(int numCourses, int[][] prerequisites) {
            graph = buildGraph(numCourses, prerequisites);
            visited = new boolean[numCourses];
            onPath = new boolean[numCourses];
            // 图中并不是所有的节点都相连，所以要用一个for循环将所有节点都作为起点调用一次DFS搜索算法
            for (int i = 0; i < numCourses; i++) {
                traverse(graph, i);
            }
            return !hasCycle;
        }
    }


    class Solution2 {
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


        public boolean canFinish(int numCourses, int[][] prerequisites) {
            graph = buildGraph(numCourses, prerequisites);
            int [] indegree = new int[numCourses];
            for (int [] edge: prerequisites) {
                int from = edge[0];
                int to = edge[1];
                //节点to的入度加1
                indegree[to]++;
            }
            //根据入度初始化队列中的节点
            Queue<Integer> q = new LinkedList<Integer>();
            for (int i = 0; i < numCourses; i++) {
                if (indegree[i] == 0) {
                    //节点i没有入度，即没有以来的节点
                    //可以作为拓扑排序的起点，加入队列
                    q.offer(i);
                }
            }
            //记录遍历的节点个数
            int count = 0;
            //开始执行BFS循环
            while(!q.isEmpty()) {
                //弹出节点 cur，并将它指向的节点的入度减1
                int cur = q.poll();
                count++;
                for (int next: graph[cur]) {
                    indegree[next] --;
                    if (indegree[next] == 0) {
                        // 如果入度变为0， 说明next依赖的节点都已被遍历
                        q.offer(next);
                    }
                }
            }
            //如果所有节点都被遍历过，说明不成环
            return count == numCourses;
        }
    }
}
