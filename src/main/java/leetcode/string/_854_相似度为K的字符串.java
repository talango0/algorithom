package leetcode.string;
//字符串 s1 和 s2 是 k 相似 的(对于某些非负整数 k )，如果我们可以交换 s1 中两个字母的位置正好 k 次，使结果字符串等于 s2 。
//
// 给定两个字谜游戏 s1 和 s2 ，返回 s1 和 s2 与 k 相似 的最小 k 。
//
//
//
// 示例 1：
//
//
//输入：s1 = "ab", s2 = "ba"
//输出：1
//
//
// 示例 2：
//
//
//输入：s1 = "abc", s2 = "bca"
//输出：2
//
//
//
//
// 提示：
//
//
// 1 <= s1.length <= 20
// s2.length == s1.length
// s1 和 s2 只包含集合 {'a', 'b', 'c', 'd', 'e', 'f'} 中的小写字母
// s2 是 s1 的一个字谜
//
//
// Related Topics 广度优先搜索 字符串 👍 127 👎 0

import leetcode.bfs._752_打开盘锁;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-09-10.
 * @see _752_打开盘锁
 */
public class _854_相似度为K的字符串{

    /**
     * BackTrace 回溯
     */
    class Solution0{
        int n;

        public int kSimilarity(String s1, String s2) {
            n = s1.length();

            char[] c1 = s1.toCharArray();
            char[] c2 = s2.toCharArray();

            int preProcess = preProcess(c1, c2);

            int process = backtrack(0, c1, c2);
            return preProcess + process;
        }

        int backtrack(int from, char[] c1, char[] c2) {
            if (from == n) {
                return 0;
            }

            if (c1[from] == c2[from]) {
                return backtrack(from + 1, c1, c2);
            }

            int min = Integer.MAX_VALUE;
            for (int i = from + 1; i < n; ++i) {
                if (c1[i] != c2[i] && c1[i] == c2[from]) {
                    swap(c1, i, from);
                    int res = backtrack(from + 1, c1, c2);
                    if (res < min) {
                        min = res;
                    }
                    swap(c1, i, from);
                }
            }
            return min + 1;
        }

        int preProcess(char[] c1, char[] c2) {
            int res = 0;
            for (int i = 0; i < n; ++i) {
                if (c1[i] == c2[i]) {
                    continue;
                }

                for (int j = i + 1; j < n; ++j) {
                    if (c1[i] == c2[j] && c1[j] == c2[i]) {
                        swap(c1, i, j);
                        res++;
                        break;
                    }
                }
            }
            return res;
        }

        void swap(char[] c, int i, int j) {
            char temp = c[i];
            c[i] = c[j];
            c[j] = temp;
        }
    }


    /**
     * 利用回溯
     */
    class Solution{
        Integer minCount = Integer.MAX_VALUE;
        Integer oneExchageCount = 0;

        public int kSimilarity(String str1, String str2) {
            int n = str1.length();

            // 计算最小环的个数，s1 = [a,c], s2 = [c,a] 只需交换1次
            char[] s1 = str1.toCharArray(), s2 = str2.toCharArray();
            for (int i = 0; i < n; i++) {
                if (s1[i] != s2[i]) {
                    for (int j = i + 1; j < n; j++) {
                        if (s1[i] == s2[j] && s1[j] == s2[i]) {
                            oneExchageCount++;
                            swap(s1, i, j);
                            break;
                        }
                    }
                }
            }
            // 回溯 + 剪枝 计算次数最小的情况
            dfs(s1, s2, 0, 0);
            return minCount + oneExchageCount;
        }

        public void dfs(char[] s1, char[] s2, int curCount, int index) {
            if (curCount >= minCount) {
                return;
            }
            if (index == s1.length) {
                minCount = Math.min(minCount, curCount);
                return;
            }
            for (int i = index; i < s1.length; i++) {
                if (s1[i] == s2[i]) {
                    dfs(s1, s2, curCount, index + 1);
                }
                for (int j = i + 1; j < s2.length; j++) {
                    if (s1[i] == s2[j]) {
                        swap(s2, i, j);
                        dfs(s1, s2, curCount + 1, index + 1);
                        swap(s2, i, j);
                    }
                }
                return;
            }
        }

        public void swap(char[] s, int i, int j) {
            char tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
        }
    }


    /**
     * BFS
     */
    class Solution2{
        public int kSimilarity(String A, String B) {
            Queue<String> queue = new ArrayDeque();
            queue.offer(A);

            Map<String, Integer> dist = new HashMap();
            dist.put(A, 0);

            while (!queue.isEmpty()) {
                String S = queue.poll();
                if (S.equals(B)) {
                    return dist.get(S);
                }
                for (String T : neighbors(S, B)) {
                    if (!dist.containsKey(T)) {
                        dist.put(T, dist.get(S) + 1);
                        queue.offer(T);
                    }
                }
            }

            throw null;
        }

        public List<String> neighbors(String S, String target) {
            List<String> ans = new ArrayList();
            int i = 0;
            for (; i < S.length(); ++i) {
                if (S.charAt(i) != target.charAt(i)) {
                    break;
                }
            }

            char[] T = S.toCharArray();
            for (int j = i + 1; j < S.length(); ++j) {
                if (S.charAt(j) == target.charAt(i)) {
                    swap(T, i, j);
                    ans.add(new String(T));
                    swap(T, i, j);
                }
            }

            return ans;
        }

        public void swap(char[] T, int i, int j) {
            char tmp = T[i];
            T[i] = T[j];
            T[j] = tmp;
        }
    }

    @Test
    public void test() {
        Solution0 solution = new Solution0();
        System.out.println(solution.kSimilarity("abc", "bca"));
    }
}
