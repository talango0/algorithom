package leetcode.arrays;
//给你一个字符串数组 words ，找出并返回 length(words[i]) * length(words[j]) 的最大值，并且这两个单词不含有公共字母
//。如果不存在这样的两个单词，返回 0 。
//
//
//
// 示例 1：
//
//
//输入：words = ["abcw","baz","foo","bar","xtfn","abcdef"]
//输出：16
//解释：这两个单词为 "abcw", "xtfn"。
//
// 示例 2：
//
//
//输入：words = ["a","ab","abc","d","cd","bcd","abcd"]
//输出：4
//解释：这两个单词为 "ab", "cd"。
//
// 示例 3：
//
//
//输入：words = ["a","aa","aaa","aaaa"]
//输出：0
//解释：不存在这样的两个单词。
//
//
//
//
// 提示：
//
//
// 2 <= words.length <= 1000
// 1 <= words[i].length <= 1000
// words[i] 仅包含小写字母
//
//
// Related Topics 位运算 数组 字符串 👍 383 👎 0
/**
 * @author mayanwei
 * @date 2022-10-22.
 */
public class _318_最大单词长度乘积{
    class Solution {
        public int maxProduct(String[] words) {
            if (words == null || words.length <= 1) {
                return 0;
            }
            int n = words.length;
            char [][] wordsCount = new char[n][26];
            for (int i = 0;i < n; i++) {
                char [] chs = words[i].toCharArray();
                for (char ch : chs) {
                    wordsCount[i][ch - 'a'] ++;
                }
            }
            int res = Integer.MIN_VALUE;
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (!intersection(wordsCount[i], wordsCount[j])) {
                        res = Math.max(res, words[i].length() * words[j].length());
                    }
                }
            }
            return res == Integer.MIN_VALUE ? 0 : res;
        }
        private boolean intersection(char [] ch1, char [] ch2) {
            for (int i = 0; i< 26; i++) {
                if(ch1[i] > 0 && ch2[i] > 0) {
                    return true;
                }
            }
            return false;
        }
    }
}
