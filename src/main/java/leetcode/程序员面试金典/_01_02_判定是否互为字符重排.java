package leetcode.程序员面试金典;
//给定两个由小写字母组成的字符串 s1 和 s2，请编写一个程序，确定其中一个字符串的字符重新排列后，能否变成另一个字符串。
//
//示例 1：
//
//输入: s1 = "abc", s2 = "bca"
//输出: true
//示例 2：
//
//输入: s1 = "abc", s2 = "bad"
//输出: false
//说明：
//
//0 <= len(s1) <= 100
//0 <= len(s2) <= 100
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/check-permutation-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _01_02_判定是否互为字符重排{
    class Solution {
        public boolean CheckPermutation(String s1, String s2) {
            if (s1.length() != s2.length()) {
                return false;
            }
            return sort(s1).equals(sort(s2));
        }
        private String sort(String s) {
            char[] chs = s.toCharArray();
            Arrays.sort(chs);
            return new String(chs);
        }
    }

    /**
     * 利用变位词的语义
     */
    class Solution2 {
        public boolean CheckPermutation(String s1, String s2) {
            if (s1.length() !=  s2.length()) {
                return false;
            }
            int[] letters = new int[128]; // 假设为 ASCII
            for (int i = 0; i < s2.length(); i++) {
                letters[s2.charAt(i)]++;
            }
            for (int i = 0; i < s1.length(); i++) {
                letters[s1.charAt(i)]--;
                if (letters[s2.charAt(i)] < 0) {
                    return false;
                }
            }
            for (int letter : letters) {
                if (letter != 0) {
                    return false;
                }
            }
            return true;
        }

    }


}
