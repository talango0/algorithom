package leetcode.graph;
//有一个需要密码才能打开的保险箱。密码是 n 位数, 密码的每一位是 k 位序列 0, 1, ..., k-1 中的一个 。
//
// 你可以随意输入密码，保险箱会自动记住最后 n 位输入，如果匹配，则能够打开保险箱。
//
// 举个例子，假设密码是 "345"，你可以输入 "012345" 来打开它，只是你输入了 6 个字符.
//
// 请返回一个能打开保险箱的最短字符串。
//
//
//
// 示例1:
//
// 输入: n = 1, k = 2
//输出: "01"
//说明: "10"也可以打开保险箱。
//
//
//
//
// 示例2:
//
// 输入: n = 2, k = 2
//输出: "00110"
//说明: "01100", "10011", "11001" 也能打开保险箱。
//
//
//
//
// 提示：
//
//
// n 的范围是 [1, 4]。
// k 的范围是 [1, 10]。
// k^n 最大可能为 4096。
//
//
//
//
// Related Topics 深度优先搜索 图 欧拉回路 👍 113 👎 0

import java.util.HashSet;
import java.util.Set;

/**
 * @author mayanwei
 * @date 2022-09-22.
 * @see _332_重新安排行程
 */
public class _753_破解保险箱{
    class Solution{
        /**
         * 思路：
         * 欧拉回路
         * Hierholzer 算法
         * 1. 从节点u开始，任意地经过还未经过的边，知道我们【无路可走】。此时我们一定会到了结点 u，这时因为所有节点的出度和入度都相等。
         * 2. 回到结点 u 之后，我们得到一条从u开始到u结束的回路，这条回路上仍然有些结点未经过的出边。我们从某个这样的节点 v 开始，继续得到一条
         * v 到 u 结束的回路，再嵌入之前的回路中，即u→⋯→v→⋯→u 变为u→⋯→v→⋯→v→⋯→u 以此类推，直到没有节点有未经过的出边，此时我们就找到了一条欧拉回路
         */
        Set<Integer> seen = new HashSet<Integer>();
        StringBuffer ans = new StringBuffer();
        int highest;
        int k;

        public String crackSafe(int n, int k) {
            highest = (int) Math.pow(10, n - 1);
            this.k = k;
            dfs(0);
            for (int i = 1; i < n; i++) {
                ans.append('0');
            }
            return ans.toString();
        }

        public void dfs(int node) {
            for (int x = 0; x < k; x++) {
                int nei = node * 10 + x;
                if (!seen.contains(nei)) {
                    seen.add(nei);
                    dfs(nei % highest);
                    ans.append(x);
                }
            }
        }
    }
}
