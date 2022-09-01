package leetcode.dp;
//给定一个字符串(s) 和一个字符模式(p) ，实现一个支持'?'和'*'的通配符匹配。
//
//'?' 可以匹配任何单个字符。
//'*' 可以匹配任意字符串（包括空字符串）。
//两个字符串完全匹配才算匹配成功。
//
//说明:
//
//s可能为空，且只包含从a-z的小写字母。
//p可能为空，且只包含从a-z的小写字母，以及字符?和*。
//示例1:
//
//输入:
//s = "aa"
//p = "a"
//输出: false
//解释: "a" 无法匹配 "aa" 整个字符串。
//示例2:
//
//输入:
//s = "aa"
//p = "*"
//输出: true
//解释:'*' 可以匹配任意字符串。
//示例3:
//
//输入:
//s = "cb"
//p = "?a"
//输出: false
//解释:'?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
//示例4:
//
//输入:
//s = "adceb"
//p = "*a*b"
//输出: true
//解释:第一个 '*' 可以匹配空字符串, 第二个 '*' 可以匹配字符串 "dce".
//示例5:
//
//输入:
//s = "acdcb"
//p = "a*c?b"
//输出: fals
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/wildcard-matching
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * @see _10_正则表达式匹配
 */
public class _44_通配符匹配 {
    class Solution {
        // 备忘录：0代表未计算，1代表true，-1代表false
        int [][] memo;
        public boolean isMatch(String s, String p) {
            if (p == null ||p.length() == 0) {
                return s == null || s.length() == 0;
            }
            p = removeRedudantStr(p);
            int m = s.length(), n = p.length();
            memo = new int[m][n];
            return dp(s.toCharArray(), 0, p.toCharArray(), 0);
        }



        // 定义： 判断 s[i..]是否能被 p[j..]匹配
        boolean dp(char [] s, int i, char []p, int j) {
            // base case
            if (j == p.length && i == s.length) {
                return true;
            }
            if (i == s.length){
                for (int k = j;k<p.length; k++) {
                    if (p[k] != '*') {
                        return false;
                    }
                }
                return true;
            }

            if (j == p.length) {
                return false;
            }

            if (memo[i][j] != 0) {
                return memo[i][j] == 1?true:false;
            }
            boolean res = false;
            if (s[i] == p[j] || p[j] == '?') {
                // s[i] 和 p[j] 完全匹配
                res = dp(s,i+1, p,j+1);
            }
            else if(p[j] == '*') {
                // s[i] 和 p[j] 不匹配，但是 p[j] 是通配符'*'
                // 可以匹配0个或者多个s中的字符
                // 只要有一种情况能够完成匹配即可
                res = dp(s, i+1, p, j) || dp(s, i, p, j+1);
            }
            // 将 s[i] 和 p[j] 的匹配结果存储备忘录
            memo[i][j] = res?1:-1;
            return res;
        }

        /**该方法去除相邻的*号 */
        private String removeRedudantStr(String s) {
            if (s == null || s.length() == 1){
                return s;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(s.charAt(0));
            for (int i = 1; i<s.length(); i++){
                if (s.charAt(i) != '*' || s.charAt(i) != s.charAt(i-1)){
                    sb.append(s.charAt(i));
                }
            }
            return sb.toString();
        }
    }



    class Solution2 {
        public boolean isMatch(String s, String p) {
            int sRight = s.length(), pRight = p.length();
            while (sRight > 0 && pRight > 0 && p.charAt(pRight - 1) != '*') {
                if (charMatch(s.charAt(sRight - 1), p.charAt(pRight - 1))) {
                    --sRight;
                    --pRight;
                } else {
                    return false;
                }
            }

            if (pRight == 0) {
                return sRight == 0;
            }

            int sIndex = 0, pIndex = 0;
            int sRecord = -1, pRecord = -1;

            while (sIndex < sRight && pIndex < pRight) {
                if (p.charAt(pIndex) == '*') {
                    ++pIndex;
                    sRecord = sIndex;
                    pRecord = pIndex;
                } else if (charMatch(s.charAt(sIndex), p.charAt(pIndex))) {
                    ++sIndex;
                    ++pIndex;
                } else if (sRecord != -1 && sRecord + 1 < sRight) {
                    ++sRecord;
                    sIndex = sRecord;
                    pIndex = pRecord;
                } else {
                    return false;
                }
            }

            return allStars(p, pIndex, pRight);
        }

        public boolean allStars(String str, int left, int right) {
            for (int i = left; i < right; ++i) {
                if (str.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        public boolean charMatch(char u, char v) {
            return u == v || v == '?';
        }
    }

    @Test
    public void test(){
        Solution2 solution2 = new Solution2();
        Assert.assertTrue(solution2.isMatch("adceb", "*a*b"));


    }

}
