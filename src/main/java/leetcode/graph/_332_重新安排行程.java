package leetcode.graph;

import java.util.*;
//给你一份航线列表 tickets ，其中 tickets[i] = [fromi, toi] 表示飞机出发和降落的机场地点。请你对该行程进行重新规划排序。
//
//
// 所有这些机票都属于一个从 JFK（肯尼迪国际机场）出发的先生，所以该行程必须从 JFK 开始。如果存在多种有效的行程，请你按字典排序返回最小的行程组合。
//
//
//
// 例如，行程 ["JFK", "LGA"] 与 ["JFK", "LGB"] 相比就更小，排序更靠前。
//
//
// 假定所有机票至少存在一种合理的行程。且所有的机票 必须都用一次 且 只能用一次。
//
//
//
// 示例 1：
//
//
//输入：tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
//输出：["JFK","MUC","LHR","SFO","SJC"]
//
//
// 示例 2：
//
//
//输入：tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL",
//"SFO"]]
//输出：["JFK","ATL","JFK","SFO","ATL","SFO"]
//解释：另一种有效的行程是 ["JFK","SFO","ATL","JFK","ATL","SFO"] ，但是它字典排序更大更靠后。
//
//
//
//
// 提示：
//
//
// 1 <= tickets.length <= 300
// tickets[i].length == 2
// fromi.length == 3
// toi.length == 3
// fromi 和 toi 由大写英文字母组成
// fromi != toi
//
//
// Related Topics 深度优先搜索 图 欧拉回路 👍 640 👎 0

/**
 * 欧拉回路
 *
 * @author mayanwei
 * @date 2022-09-18.
 */
public class _332_重新安排行程{
    /**
     * Hierholzer 算法
     * <p>
     * 1. 选择任一顶点为起点，遍历所有相邻边。
     * 2. 深度搜索，访问相邻顶点。将经过的边都删除。
     * 3. 如果当前顶点没有相邻边，则将顶点入栈。
     * 4. 栈中的顶点倒序输出，就是从起点出发的欧拉回路。
     * 为了证明该算法的有效性，下说明两条性质。
     * 性质一：
     * 如果该图为欧拉图，则栈底的必定为起点。如果该图为半欧拉图，则栈底部存储的是与起点不同的另外一个奇度数顶点。
     *
     * 性质二：
     * 如果该图为欧拉图(/半欧拉图)，则栈中的自底到顶第 n 个顶点就是欧拉回路(/欧拉路径)上的第 n 个顶点。
     * <p>
     * 时间复杂度 O(mlogm)
     * 空间复杂度 O(m)
     */
    class Solution{

        Map<String, PriorityQueue<String>> map = new HashMap<>();
        List<String> itinerary = new LinkedList<String>();

        public List<String> findItinerary(List<List<String>> tickets) {
            for (List<String> ticket : tickets) {
                String src = ticket.get(0), dst = ticket.get(1);
                if (!map.containsKey(src)) {
                    map.put(src, new PriorityQueue<String>());
                }
                map.get(src).offer(dst);
            }
            dfs("JFK");
            Collections.reverse(itinerary);
            return itinerary;
        }

        public void dfs(String curr) {
            while (map.containsKey(curr) && map.get(curr).size() > 0) {
                dfs(map.get(curr).poll());
            }
            itinerary.add(curr);
        }
    }
}
