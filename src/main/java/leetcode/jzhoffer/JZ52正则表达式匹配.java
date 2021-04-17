package leetcode.jzhoffer;

public class JZ52正则表达式匹配 {
    /**
     * 请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。
     * 在本题中，匹配是指字符串的所有字符匹配整个模式。
     * 例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配
     */

    public static class Solution {
        /*
思路:
主串为s,长度为sn
模式串为p, 长度为pn
对于模式串p 当前的第 i 位来说，有 '正常字符'、'*'和'.'

         */

        public boolean isMatch(char[] str, char[] pattern){
            return match(str, 0, pattern, 0);

        }
        public boolean match(char [] str, int i, char [] pattern, int j){
            // 边界
            if (i == str.length && j == pattern.length) { // 字符串和模式串都为空
                return true;
            } else if (j == pattern.length) { // 模式串为空
                return false;
            }

            // 模式串下一个字符是'*'
            boolean next = (j + 1 < pattern.length && pattern[j + 1] == '*');
            if (next) {
                // 要保证i<str.length，否则越界
                if (i < str.length && (pattern[j] == '.' || str[i] == pattern[j])) {
                    return match(str, i, pattern, j + 2) || match(str, i + 1, pattern, j);
                } else {
                    return match(str, i, pattern, j + 2);
                }
            } else {
                if (i < str.length && (pattern[j] == '.' || str[i] == pattern[j])) {
                    return match(str, i + 1, pattern, j + 1);
                } else {
                    return false;
                }
            }

        }
    }


    /**
     * 分析：
     * 用f[i][j] 表示s的前i个字符与p中的前j个字符是否匹配。在状态转移时，考虑p的第j个字符的匹配情况：
     *
     * 1. p[j] != '*'
     *       f[i][j] = matches(s[i],p[j])  ? f[i-1][j-1] : false
     * 2. p[j] == '*'
     *      f[i][j] = matches(s[i], p[j-1]) ? (f[i-1][j] or f[i][j-2]) : f[i][j-2]
     * 初始状态 f[0][0] = true,表示空字符串为true，最终的答案为f[m][n]
     *
     * 空间复杂度 O(m*n)
     * 时间复杂度 O(n*n)
     */


    static class Solution2 {
        public boolean isMatch(String s, String p) {
            int m = s.length(), n = p.length();
            boolean[][] f = new boolean[m + 1][n + 1];
            f[0][0] = true;
            for(int i = 0; i <= m; i++){
                for(int j = 1; j<= n; j++){
                    if(p.charAt(j - 1) == '*'){
                        f[i][j] = f[i][j-2];
                        if(matches(s, p, i, j-1)){
                            f[i][j] = f[i][j] ||  f[i-1][j];
                        }
                    }else{
                        if(matches(s, p, i, j)){
                            f[i][j] = f[i-1][j-1];
                        }
                    }
                }
            }
            return f[m][n];
        }

        private boolean matches(String s, String p, int i, int j) {
            if(i == 0){
                return false;
            }
            if(p.charAt(j-1) == '.'){
                return true;
            }
            return s.charAt(i-1) == p.charAt(j-1);
        }
    }

    public static void main(String[] args) {
//        String s = "aaa";
        String s = "";

        String p = ".*";
//        String p = "a.a";
//        String p = "ab*ac*a";
//        String p = "aa.a";
//        String p ="ab*a";
        Solution2 solution = new Solution2();
        System.out.println(solution.isMatch(s, p));

    }
}
