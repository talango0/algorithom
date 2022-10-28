package leetcode.jzhoffer;
//给定两个字符串 s 和 t ，编写一个函数来判断它们是不是一组变位词（字母异位词）。
//
// 注意：若 s 和 t 中每个字符出现的次数都相同且字符顺序不完全相同，则称 s 和 t 互为变位词（字母异位词）。
//
//
//
// 示例 1:
//
//
//输入: s = "anagram", t = "nagaram"
//输出: true
//
//
// 示例 2:
//
//
//输入: s = "rat", t = "car"
//输出: false
//
// 示例 3:
//
//
//输入: s = "a", t = "a"
//输出: false
//
//
//
// 提示:
//
//
// 1 <= s.length, t.length <= 5 * 10⁴
// s and t 仅包含小写字母
//
//
//
//
// 进阶: 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
//
//
//
//
// 注意：本题与主站 242 题相似（字母异位词定义不同）：https://leetcode-cn.com/problems/valid-anagram/
//
// Related Topics 哈希表 字符串 排序 👍 33 👎 0

import leetcode.string._242_有效的字母异位词;
import org.junit.jupiter.api.Test;

/**
 * @author mayanwei
 * @date 2022-10-28.
 * @see _242_有效的字母异位词
 */
public class 剑指_Offer_II_032_有效的变位词{
    class Solution {
        public boolean isAnagram(String s, String t) {
            if (s == null || s.length() == 1 || t == null || t.length()==1){
                return false;
            }
            int [] counts = encode(s);
            int [] countt = encode(t);
            // 确保两个字符串中所有字符出现的数量相同
            for (int i = 0; i < counts.length; i++) {
                if (counts[i] != countt[i]) {
                    return false;
                }
            }
            return true;
        }
        // 计数字符出现的次数
        private int [] encode(String s) {
            int [] count = new int[26];
            for (char ch : s.toCharArray()) {
                count[ch-'a'] += 1;
            }
            return count;
        }
    }

    @Test
    public void testSolution1() {
        Solution solution = new Solution();
        boolean anagram = solution.isAnagram("a", "a");
        System.out.println(anagram);
    }

}
