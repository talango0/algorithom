package leetcode.string;


//给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
//
// 说明：本题中，我们将空字符串定义为有效的回文串。
//
// 示例 1:
//
// 输入: "A man, a plan, a canal: Panama"
//输出: true
//
//
// 示例 2:
//
// 输入: "race a car"
//输出: false
//
// Related Topics 双指针 字符串
// 👍 288 👎 0


public class _125_验证回文串{
    class Solution1{
        public boolean isPalindrome(String s) {
            if (s == null || s.length() == 0) {
                return true;
            }
            char[] c = s.toCharArray();
            int i = 0;
            int j = c.length - 1;
            while (i <= j) {
                if (c[i] == c[j] || Character.toLowerCase(c[i]) == Character.toLowerCase(c[j])) {
                    i++;
                    j--;
                }
                else {
                    if ((Character.isDigit(c[i]) || Character.isLetter(c[i]))
                            && ((Character.isDigit(c[j]) || Character.isLetter(c[j])))) {
                        return false;
                    }
                    else if (!Character.isDigit(c[i]) && !Character.isLetter(c[i])) {
                        i++;
                    }
                    else if (!Character.isDigit(c[j]) && !Character.isLetter(c[j])) {
                        j--;
                    }
                }
            }
            return true;
        }
    }
}
