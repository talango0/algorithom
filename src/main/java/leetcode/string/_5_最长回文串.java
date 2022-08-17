package leetcode.string;
//给你一个字符串 s，找到 s 中最长的回文子串。
//
//
//
// 示例 1：
//
//
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
//
//
// 示例 2：
//
//
//输入：s = "cbbd"
//输出："bb"
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 1000
// s 仅由数字和英文字母组成
//
// Related Topics 字符串 动态规划 👍 5389 👎 0

import leetcode.dp._1312_让字符串成为回文串的最少插入次数;
import leetcode.dp._516_最长回文子序列;

/**
 * @see _1312_让字符串成为回文串的最少插入次数
 * @see _516_最长回文子序列
 */
public class _5_最长回文串 {
    class Solution {
        public String longestPalindrome(String s) {
            if(s == null || s.length()<2){
                return s;
            }
            String res = "";
            for (int i = 0; i<s.length(); i++){
                String s1 = palingdrome(s, i, i);
                String s2 = palingdrome(s, i, i+1);
                res = res.length() > s1.length()? res:s1;
                res = res.length() > s2.length()? res:s2;
            }
            return res;
        }

        //再s中寻找已s[l] 和 是 s[r] 为中心的回文串
        String palingdrome(String s, int l, int r) {
            int n = s.length();
            //防止越界，如果输入相同的 l 和 r，就相当于于奇数长度的回文串，如果输入相邻的两个l和r，则相当于长度为偶数的回文串
            while (l>=0 && r<n && s.charAt(l) == s.charAt(r)) {
                l--;
                r++;
            }
            return s.substring(l+1, r);
        }
    }
}
