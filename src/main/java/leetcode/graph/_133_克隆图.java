package leetcode.graph;
//给你无向 连通 图中一个节点的引用，请你返回该图的 深拷贝（克隆）。
//
//图中的每个节点都包含它的值 val（int） 和其邻居的列表（list[Node]）。
//
//class Node {
//    public int val;
//    public List neighbors;
//}
//测试用例格式：
//
//简单起见，每个节点的值都和它的索引相同。例如，第一个节点值为 1（val = 1），第二个节点值为 2（val = 2），以此类推。该图在测试用例中使用邻接列表表示。
//
//邻接列表 是用于表示有限图的无序列表的集合。每个列表都描述了图中节点的邻居集。
//
//给定节点将始终是图中的第一个节点（值为 1）。你必须将 给定节点的拷贝 作为对克隆图的引用返回。
//
//示例 1：
//
//
//
//输入：adjList = [[2,4],[1,3],[2,4],[1,3]]
//输出：[[2,4],[1,3],[2,4],[1,3]]
//解释：
//图中有 4 个节点。
//节点 1 的值是 1，它有两个邻居：节点 2 和 4 。
//节点 2 的值是 2，它有两个邻居：节点 1 和 3 。
//节点 3 的值是 3，它有两个邻居：节点 2 和 4 。
//节点 4 的值是 4，它有两个邻居：节点 1 和 3 。
//示例 2：
//
//
//
//输入：adjList = [[]]
//输出：[[]]
//解释：输入包含一个空列表。该图仅仅只有一个值为 1 的节点，它没有任何邻居。
//示例 3：
//
//输入：adjList = []
//输出：[]
//解释：这个图是空的，它不含任何节点。
//示例 4：
//
//
//
//输入：adjList = [[2],[1]]
//输出：[[2],[1]]
//提示：
//
//节点数不超过 100 。
//每个节点值 Node.val 都是唯一的，1 <= Node.val <= 100。
//无向图是一个简单图，这意味着图中没有重复的边，也没有自环。
//由于图是无向的，如果节点 p 是节点 q 的邻居，那么节点 q 也必须是节点 p 的邻居。
//图是连通图，你可以从给定节点访问到所有节点。
//Related Topics
//深度优先搜索 | 广度优先搜索 | 图 | 哈希表

import leetcode.list._138_复制带随机指针的链表;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @see _138_复制带随机指针的链表
 */
public class _133_克隆图 {
    class Solution {
        public Node cloneGraph(Node node) {
            // DFS 遍历图，顺便构建克隆图
            traverse(node);
            // 从 map 里找到克隆图的对应节点
            return originToClone.get(node);
        }

        // 记录 DFS 遍历过的节点，防止走回头路
        HashSet<Node> visited = new HashSet<>();
        // 记录原节点到克隆节点的映射
        HashMap<Node, Node> originToClone = new HashMap<>();

        // DFS 图遍历框架
        void traverse(Node node) {
            if (node == null) {
                return;
            }
            if (visited.contains(node)) {
                return;
            }
            // 前序位置，标记为已访问
            visited.add(node);
            // 前序位置，克隆节点
            if (!originToClone.containsKey(node)) {
                originToClone.put(node, new Node(node.val));
            }
            Node cloneNode = originToClone.get(node);

            // 递归遍历邻居节点，并构建克隆图
            for (Node neighbor : node.neighbors) {
                traverse(neighbor);
                // 递归之后，邻居节点一定存在 originToClone 中
                Node cloneNeighbor = originToClone.get(neighbor);
                cloneNode.neighbors.add(cloneNeighbor);
            }
        }
    }


    class Solution2 {
        private HashMap<Node, Node> visited = new HashMap<>();

        public Node cloneGraph(Node node) {
            if (node == null) {
                return null;
            }

            if (visited.containsKey(node)) {
                return visited.get(node);
            }

            Node cloneNode = new Node(node.val);
            visited.put(node, cloneNode);
            for (Node neighbor : node.neighbors) {
                cloneNode.neighbors.add(cloneGraph(neighbor));
            }
            return cloneNode;
        }
    }

}
