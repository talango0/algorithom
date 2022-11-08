package leetcode.jzhoffer;

import leetcode.math._399_除法求值;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//给定一个变量对数组 equations 和一个实数值数组 values 作为已知条件，其中 equations[i] = [Ai, Bi] 和
//values[i] 共同表示等式 Ai / Bi = values[i] 。每个 Ai 或 Bi 是一个表示单个变量的字符串。
//
// 另有一些以数组 queries 表示的问题，其中 queries[j] = [Cj, Dj] 表示第 j 个问题，请你根据已知条件找出 Cj / Dj =
// ? 的结果作为答案。
//
// 返回 所有问题的答案 。如果存在某个无法确定的答案，则用 -1.0 替代这个答案。如果问题中出现了给定的已知条件中没有出现的字符串，也需要用 -1.0 替
//代这个答案。
//
// 注意：输入总是有效的。可以假设除法运算中不会出现除数为 0 的情况，且不存在任何矛盾的结果。
//
//
//
// 示例 1：
//
//
//输入：equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"]
//,["b","a"],["a","e"],["a","a"],["x","x"]]
//输出：[6.00000,0.50000,-1.00000,1.00000,-1.00000]
//解释：
//条件：a / b = 2.0, b / c = 3.0
//问题：a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
//结果：[6.0, 0.5, -1.0, 1.0, -1.0 ]
//
//
// 示例 2：
//
//
//输入：equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0],
//queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
//输出：[3.75000,0.40000,5.00000,0.20000]
//
//
// 示例 3：
//
//
//输入：equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],[
//"a","c"],["x","y"]]
//输出：[0.50000,2.00000,-1.00000,-1.00000]
//
//
//
//
// 提示：
//
//
// 1 <= equations.length <= 20
// equations[i].length == 2
// 1 <= Ai.length, Bi.length <= 5
// values.length == equations.length
// 0.0 < values[i] <= 20.0
// 1 <= queries.length <= 20
// queries[i].length == 2
// 1 <= Cj.length, Dj.length <= 5
// Ai, Bi, Cj, Dj 由小写英文字母与数字组成
//
//
//
//
//
// 注意：本题与主站 399 题相同： https://leetcode-cn.com/problems/evaluate-division/
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 图 数组 最短路 👍 30 👎 0

/**
 * @author mayanwei
 * @date 2022-11-04.
 * @see _399_除法求值
 */
public class 剑指_Offer_II_111_计算除法{
    class Solution{
        public double[] calcEquation(List<List<String>> equations, double[] values,
                                     List<List<String>> queries) {
            int equationsSize = equations.size();
            UnionFind unionFind = new UnionFind(2 * equationsSize);
            // 第 1 步：预处理，将变量的值与 id 进行映射，使得并查集的底层使用数组实现，方便编码
            Map<String, Integer> hashMap = new HashMap<>(2 * equationsSize);
            int id = 0;
            for (int i = 0; i < equationsSize; i++) {
                List<String> equation = equations.get(i);
                String var1 = equation.get(0);
                String var2 = equation.get(1);
                if (!hashMap.containsKey(var1)) {
                    hashMap.put(var1, id);
                    id++;
                }
                if (!hashMap.containsKey(var2)) {
                    hashMap.put(var2, id);
                    id++;
                }
                unionFind.union(hashMap.get(var1), hashMap.get(var2), values[i]);
            }
            // 第 2 步：做查询
            int queriesSize = queries.size();
            double[] res = new double[queriesSize];
            for (int i = 0; i < queriesSize; i++) {
                String var1 = queries.get(i).get(0);
                String var2 = queries.get(i).get(1);

                Integer id1 = hashMap.get(var1);
                Integer id2 = hashMap.get(var2);

                if (id1 == null || id2 == null) {
                    res[i] = -1.0d;
                }
                else {
                    res[i] = unionFind.isConnected(id1, id2);
                }
            }
            return res;
        }

        class UnionFind{
            private int[] parent;
            /**
             * 指向父节点的权值
             */
            private double[] weigth;

            public UnionFind(int n) {
                this.parent = new int[n];
                this.weigth = new double[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    weigth[i] = 1.0d;
                }
            }

            public void union(int x, int y, double value) {
                int rootX = find(x);
                int rootY = find(y);
                if (rootX == rootY) {
                    return;
                }
                parent[rootX] = rootY;
                weigth[rootX] = weigth[y] * value / weigth[x];
            }

            /**
             * 路径压缩，返回根节点的id
             */
            public int find(int x) {
                if (x != parent[x]) {
                    int origin = parent[x];
                    parent[x] = find(parent[x]);
                    weigth[x] *= weigth[origin];
                }
                return parent[x];
            }

            public double isConnected(int x, int y) {
                int rootX = find(x);
                int rootY = find(y);
                if (rootX == rootY) {
                    return weigth[x] / weigth[y];
                }
                else {
                    return -1.0d;
                }
            }
        }
    }

}
