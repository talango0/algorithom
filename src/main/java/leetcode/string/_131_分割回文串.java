package leetcode.string;

//给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
//
// 返回 s 所有可能的分割方案。
//
// 示例:
//
// 输入: "aab"
//输出:
//[
//  ["aa","b"],
//  ["a","a","b"]
//]
// Related Topics 回溯算法
// 👍 417 👎 0


import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


import static org.junit.Assert.*;

public class _131_分割回文串{
    class Solution{
        public List<List<String>> partition(String s) {
            int len = s.length();
            List<List<String>> res = new ArrayList<>();
            if (len == 0) {
                return res;
            }
            // 预处理
            // 状态：dp[i][j] 表示 s[i][j] 是否是回文
            boolean[][] dp = new boolean[len][len];
            // 状态转移方程：在 s[i] == s[j] 的时候，dp[i][j] 参考 dp[i + 1][j - 1]
            for (int right = 0; right < len; right++) {
                // 注意：left <= right 取等号表示 1 个字符的时候也需要判断
                for (int left = 0; left <= right; left++) {
                    if (s.charAt(left) == s.charAt(right) && (right - left <= 2 || dp[left + 1][right - 1])) {
                        dp[left][right] = true;
                    }
                }
            }
            Deque<String> stack = new ArrayDeque<>();
            backtracking(s, 0, len, dp, stack, res);
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

    @Test
    public void testPartition() {
        String s = "aaab";
        Solution solution = new Solution();
        List<List<String>> res = solution.partition(s);
        System.out.println(res.toString());
    }


}
