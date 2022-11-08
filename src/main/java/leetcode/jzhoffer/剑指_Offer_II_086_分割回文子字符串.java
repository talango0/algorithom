package leetcode.jzhoffer;

import leetcode.string._131_分割回文串;

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-11-04.
 * @see _131_分割回文串
 */
public class 剑指_Offer_II_086_分割回文子字符串{
    class Solution {
        public String[][] partition(String s) {
            int n = s.length();
            if (n == 0) {
                return new String[][]{};
            }
            // dp[i][j] 表示 s[i...j] 是否是回文串
            boolean [][] dp = new boolean[n][n];
            // 状态转移方式，当s[i] == s[j] 时，dp[i][j] 参考 dp[i+1][j-1];
            for (int right = 0; right < n; right++) {
                for (int left = 0; left <= right; left++) {
                    if (s.charAt(left) == s.charAt(right)
                            && (right-left <= 2 || dp[left + 1][right-1])) {
                        dp[left][right] = true;
                    }
                }
            }
            Deque<String> stack = new ArrayDeque<>();
            List<List<String>> res = new ArrayList<>();
            backtracking(s, 0,  n, dp, stack, res);

            return arrayAs(res);
        }
        public String[][] arrayAs(List<List<String>> list) {
            int n = list.size();
            String [][] res = new String[n][];
            for (int i = 0; i < n; i++) {
                List<String> strings = list.get(i);
                res[i] = strings.toArray(new String[strings.size()]);
            }
            return res;
        }

        private void backtracking(String s,
                                  int start,
                                  int len,
                                  boolean[][] dp,
                                  Deque<String> path,
                                  List<List<String>> res) {
            if (start == len) {
                res.add(new ArrayList<>(path));
                return;
            }

            for (int i = start; i < len; i++) {
                // 剪枝
                if (!dp[start][i]) {
                    continue;
                }
                path.addLast(s.substring(start, i + 1));
                backtracking(s, i + 1, len, dp, path, res);
                path.removeLast();
            }
        }
    }

    class Solution2 {

        List<String[]> ans = new ArrayList<>();
        String[][] ps;

        public String[][] partition(String s) {
            int length = s.length();
            ps = new String[length][length];
            initP(s.toCharArray());
            dfs(s, 0, new ArrayList<String>());
            return ans.toArray(new String[ans.size()][]);
        }

        private void initP(char[] s) {
            int length = s.length;
            boolean[][] p = new boolean[length][length];
            for (int i = length - 1; i >= 0; i--) {
                for (int j = i; j < length; j++) {
                    p[i][j] = s[i] == s[j] && (j - i < 2 || p[i + 1][j - 1]);
                    if(p[i][j]){
                        ps[i][j] = new String(Arrays.copyOfRange(s, i, j+1));
                    }
                }
            }
        }

        private void dfs(String s, int i, ArrayList<String> list) {
            if (i == s.length()) {
                ans.add(list.toArray(new String[list.size()]));
                return;
            }
            for (int j = 1; j <= s.length() - i; j++) {
                if (ps[i][i + j - 1] == null) {
                    continue;
                }
                list.add(ps[i][i + j - 1]);
                dfs(s, i + j, list);
                list.remove(list.size() - 1);
            }
        }
    }


}
