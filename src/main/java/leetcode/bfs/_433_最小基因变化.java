package leetcode.bfs;
//基因序列可以表示为一条由 8 个字符组成的字符串，其中每个字符都是 'A'、'C'、'G' 和 'T' 之一。
//
// 假设我们需要调查从基因序列 start 变为 end 所发生的基因变化。一次基因变化就意味着这个基因序列中的一个字符发生了变化。
//
//
// 例如，"AACCGGTT" --> "AACCGGTA" 就是一次基因变化。
//
//
// 另有一个基因库 bank 记录了所有有效的基因变化，只有基因库中的基因才是有效的基因序列。（变化后的基因必须位于基因库 bank 中）
//
// 给你两个基因序列 start 和 end ，以及一个基因库 bank ，请你找出并返回能够使 start 变化为 end 所需的最少变化次数。如果无法完成
//此基因变化，返回 -1 。
//
// 注意：起始基因序列 start 默认是有效的，但是它并不一定会出现在基因库中。
//
//
//
// 示例 1：
//
//
//输入：start = "AACCGGTT", end = "AACCGGTA", bank = ["AACCGGTA"]
//输出：1
//
//
// 示例 2：
//
//
//输入：start = "AACCGGTT", end = "AAACGGTA", bank = ["AACCGGTA","AACCGCTA",
//"AAACGGTA"]
//输出：2
//
//
// 示例 3：
//
//
//输入：start = "AAAAACCC", end = "AACCCCCC", bank = ["AAAACCCC","AAACCCCC",
//"AACCCCCC"]
//输出：3
//
//
//
//
// 提示：
//
//
// start.length == 8
// end.length == 8
// 0 <= bank.length <= 10
// bank[i].length == 8
// start、end 和 bank[i] 仅由字符 ['A', 'C', 'G', 'T'] 组成
//
//
// Related Topics 广度优先搜索 哈希表 字符串 👍 224 👎 0


import java.util.*;

/**
 * @author mayanwei
 * @date 2022-09-12.
 * @see _126_单词接龙II
 * @see _127_单词接龙
 */
public class _433_最小基因变化{
    /**
     * 时间复杂度 O(C*n*m) 其中 C = 4, n为基因序列的长度，m 为数组 bank 的长度。
     * 空间复杂度 O(n*m) , 其中 n 为基因序列的长度， m 为数组bank的长度。
     */
    class Solution{
        public int minMutation(String start, String end, String[] bank) {
            Set<String> cnt = new HashSet<>();
            Set<String> visited = new HashSet<>();
            char[] keys = {'A', 'G', 'C', 'T'};
            for (String w : bank) {
                cnt.add(w);
            }
            if (start.equals(end)) {
                return 0;
            }
            if (!cnt.contains(end)) {
                return -1;
            }
            Queue<String> queue = new ArrayDeque<>();
            queue.offer(start);
            visited.add(start);
            int step = 1;
            while (!queue.isEmpty()) {
                int sz = queue.size();
                for (int i = 0; i < sz; i++) {
                    String curr = queue.poll();
                    for (int j = 0; j < 8; j++) {
                        for (int k = 0; k < 4; k++) {
                            if (keys[k] != curr.charAt(j)) {
                                StringBuilder sb = new StringBuilder(curr);
                                sb.setCharAt(j, keys[k]);
                                String next = sb.toString();
                                if (!visited.contains(next) && cnt.contains(next)) {
                                    if (next.equals(end)) {
                                        return step;
                                    }
                                    queue.offer(next);
                                    visited.add(next);
                                }
                            }
                        }
                    }
                }
                step++;
            }
            return -1;
        }
    }

    /**
     * 时间负载度 O(m*n*n) m为基因序列的长度，n为数组bank的长度。
     * 空间复杂度 O(n*n) 其中n为数组bank的长度
     */
    class Solution0{
        public int minMutation(String start, String end, String[] bank) {
            int m = start.length();
            int n = bank.length;
            List<Integer>[] adj = new List[n];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<Integer>();
            }
            int endIndex = -1;
            // 对bank 进行预处理，只在合法的基因变化中进行搜索即可
            for (int i = 0; i < n; i++) {
                if (end.equals(bank[i])) {
                    endIndex = i;
                }
                for (int j = i + 1; j < n; j++) {
                    int mutations = 0;
                    for (int k = 0; k < m; k++) {
                        if (bank[i].charAt(k) != bank[j].charAt(k)) {
                            mutations++;
                        }
                        if (mutations > 1) {
                            break;
                        }
                    }
                    if (mutations == 1) {
                        adj[i].add(j);
                        adj[j].add(i);
                    }
                }
            }
            if (endIndex == -1) {
                return -1;
            }

            Queue<Integer> queue = new ArrayDeque<Integer>();
            boolean[] visited = new boolean[n];
            int step = 1;
            for (int i = 0; i < n; i++) {
                int mutations = 0;
                for (int k = 0; k < m; k++) {
                    if (start.charAt(k) != bank[i].charAt(k)) {
                        mutations++;
                    }
                    if (mutations > 1) {
                        break;
                    }
                }
                if (mutations == 1) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
            while (!queue.isEmpty()) {
                int sz = queue.size();
                for (int i = 0; i < sz; i++) {
                    int curr = queue.poll();
                    if (curr == endIndex) {
                        return step;
                    }
                    for (int next : adj[curr]) {
                        if (visited[next]) {
                            continue;
                        }
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
                step++;
            }
            return -1;
        }
    }
}
