package leetcode.程序员面试金典;
//给定一组单词words，编写一个程序，找出其中的最长单词，且该单词由这组单词中的其他单词组合而成。若有多个长度相同的结果，
// 返回其中字典序最小的一项，若没有符合要求的单词则返回空字符串。
//
//示例：
//
//输入： ["cat","banana","dog","nana","walk","walker","dogwalker"]
//输出： "dogwalker"
//解释： "dogwalker"可由"dog"和"walker"组成。
//提示：
//
//0 <= len(words) <= 200
//1 <= len(words[i]) <= 100
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/longest-word-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author mayanwei
 * @date 2023-06-25.
 */
public class _17_15_最长单词{
    class Solution{
        public String longestWord(String[] words) {
            Arrays.sort(words, new Comparator<String>(){
                @Override
                public int compare(String o1, String o2) {
                    if (o1.length() == o2.length()) {
                        return o1.compareTo(o2);
                    }
                    return o2.length() - o1.length();
                }
            });
            // 创建 map 以便查找
            HashMap<String, Boolean> map = new HashMap<>();
            for (String word : words) {
                map.put(word, true);
            }

            for (String s : words) {
                // 切分所有可能的两半
                for (int i = 1; i < s.length(); i++) {
                    if (canBuildWord(s, true, map)) {
                        return s;
                    }
                }
            }
            return "";
        }

        /**
         * 使用动态规划方法缓存了多次调用的结果。
         * 标志isOriginWord 作用：对于原始单词，map 会将这些单词初始化为 true（因为单词不能只有它本身组成。）。因此我们会利用
         * isOriginalWord 标志直接跳过这项检查。
         */
        boolean canBuildWord(String str, boolean isOriginWord, HashMap<String, Boolean> map) {
            if (map.containsKey(str) && !isOriginWord) {
                return map.get(str);
            }
            for (int i = 1; i < str.length(); i++) {
                String left = str.substring(0, i);
                String right = str.substring(i);
                if (map.containsKey(left) && map.get(left) == true && canBuildWord(right, false, map)) {
                    return true;
                }
            }
            map.put(str, false);
            return false;
        }

    }


    class Solution1{
        public String longestWord(String[] words) {
            Arrays.sort(words, new Comparator<String>(){
                @Override
                public int compare(String o1, String o2) {
                    return o1.length() - o2.length() == 0 ? o2.compareTo(o1) :o1.length() - o2.length();
                }
            });
            for (int i = words.length - 1; i > 0; i--) {
                String str = doFilt(words[i], words, i);
                if (str == null || str.length() == 0) {
                    return words[i];
                }
            }
            return "";
        }

        public String doFilt(String str, String[] words, int finIndex) {
            for (int j = 0; j < finIndex; j++) {
                String strT = str;
                if (strT.startsWith(words[j])) {
                    strT = doFilt(strT.substring(words[j].length()), words, finIndex);
                }
                if (strT == null || strT.length() == 0) {
                    return "";
                }
            }
            return str;
        }
    }


    @Test
    public void test() {
        String[] arr = {"cat", "banana", "dog", "nana", "walk", "walker", "dogwalker"};
        System.out.println(new Solution().longestWord(arr));
    }
}
