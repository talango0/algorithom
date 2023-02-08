package leetcode.twopoints;
//给你一个字符串 s 和一个字符串数组 dictionary ，找出并返回dictionary 中最长的字符串，该字符串可以通过删除 s 中的某些字符得到。
//
//如果答案不止一个，返回长度最长且字母序最小的字符串。如果答案不存在，则返回空字符串。
//
//
//
//示例 1：
//
//输入：s = "abpcplea", dictionary = ["ale","apple","monkey","plea"]
//输出："apple"
//示例 2：
//
//输入：s = "abpcplea", dictionary = ["a","b","c"]
//输出："a"
//
//
//提示：
//
//1 <= s.length <= 1000
//1 <= dictionary.length <= 1000
//1 <= dictionary[i].length <= 1000
//s 和 dictionary[i] 仅由小写英文字母组成
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/longest-word-in-dictionary-through-deleting
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author mayanwei
 * @date 2023-02-08.
 */
public class _524_通过删除字母匹配到字典里最长单词{

    /**
     * 思路：
     * <p>根据题意需要解决的问题有两个：</p>
     * 1. 如何判断dictionary中的字符串t是否可以通过删除s中的某些字符得到。
     * <p>
     * 2. 如何找出长度最长其字典序最小的字符串。
     * <p>
     * 对于1，实际上就是判断t是否是s的子序列。因此只要能找到一种t在s中中出现
     * 的方式，即可以认为t是s的一个子序列。当我们从前往后匹配时，可以发现每次
     * 贪心选择匹配最靠前的字符时最优决策。
     * 这样，我们初始化两个指针i，j，分别指向 t 和 s 的初始位置。每次贪心地
     * 匹配，匹配成功则i和j同时右移，匹配t的下一个位置，匹配失败则j右移，i不变，
     * 尝试用s的下一个字符匹配t。
     * 最终，如果i移动到t的末尾，则说明t时s的子序列。
     * <p>
     * 对于2，可以通过遍历dictionary中的字符串，并维护当前长度最长且字典序最小
     * 的字符串来找到。
     */
    class Solution{
        //时间复杂度：O（d*(m+n)) d，dictionary的长度，m表示s的长度，n表示
        //dictionary中字符串的平均长度。m+n每次遍历都需要m+n的复杂度
        //空间复杂度O（1）
        public String findLongestWord(String s, List<String> dictionary) {
            String res = "";
            for (String t : dictionary) {
                int i = 0, j = 0;
                while (i < t.length() && j < s.length()) {
                    if (t.charAt(i) == s.charAt(j)) {
                        i++;
                    }
                    j++;
                }
                if (i == t.length()) {
                    if (t.length() > res.length() || t.length() == res.length()
                            && t.compareTo(res) < 0) {
                        res = t;
                    }
                }
            }
            return res;
        }
    }

    // 方法一的基础上，我们尝试通过对 dictionary 的预处理，来优化第 2 个问题的处理。
    // 我们可以先将 dictionary 依据字符串长度的降序和字典序的升序进行排序，
    // 然后从前向后找到第一个符合条件的字符串直接返回即可
    class Solution2 {
        public String findLongestWord(String s, List<String> dictionary) {
            Collections.sort(dictionary, new Comparator<String>() {
                public int compare(String word1, String word2) {
                    if (word1.length() != word2.length()) {
                        return word2.length() - word1.length();
                    } else {
                        return word1.compareTo(word2);
                    }
                }
            });
            for (String t : dictionary) {
                int i = 0, j = 0;
                while (i < t.length() && j < s.length()) {
                    if (t.charAt(i) == s.charAt(j)) {
                        ++i;
                    }
                    ++j;
                }
                if (i == t.length()) {
                    return t;
                }
            }
            return "";
        }
    }

}
