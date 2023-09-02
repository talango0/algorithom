package leetcode.jzhoffer;
//给定一个字符串 s ，验证 s 是否是 回文串 ，只考虑字母和数字字符，可以忽略字母的大小写。
//
// 本题中，将空字符串定义为有效的 回文串 。
//
//
//
// 示例 1:
//
//
//输入: s = "A man, a plan, a canal: Panama"
//输出: true
//解释："amanaplanacanalpanama" 是回文串
//
// 示例 2:
//
//
//输入: s = "race a car"
//输出: false
//解释："raceacar" 不是回文串
//
//
//
// 提示：
//
//
// 1 <= s.length <= 2 * 10⁵
// 字符串 s 由 ASCII 字符组成
//
//
//
//
//
// 注意：本题与主站 125 题相同： https://leetcode-cn.com/problems/valid-palindrome/
//
// Related Topics 双指针 字符串 👍 34 👎 0

import leetcode.dp._1312_让字符串成为回文串的最少插入次数;
import leetcode.dp._516_最长回文子序列;
import leetcode.string._5_最长回文串;
import leetcode.string._647_回文子串;

/**
 * @author mayanwei
 * @date 2022-10-19.
 * @see _1312_让字符串成为回文串的最少插入次数
 * @see _516_最长回文子序列
 * @see _647_回文子串
 * @see _5_最长回文串
 */
public class 剑指_Offer_II_018_有效的回文{
    class Solution{
        public boolean isPalindrome(String s) {
            if (s == null || s.length() == 0) {
                return true;
            }
            int i = 0, j = s.length() - 1;
            while (i < j) {
                if (!Character.isLetterOrDigit(s.charAt(i))) {
                    i++;
                    continue;
                }
                if (!Character.isLetterOrDigit(s.charAt(j))) {
                    j--;
                    continue;
                }
                if (Character.toLowerCase(s.charAt(i++)) != Character.toLowerCase(s.charAt(j--))) {
                    return false;
                }
            }
            return true;
        }
    }
}
