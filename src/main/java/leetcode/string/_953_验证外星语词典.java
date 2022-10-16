package leetcode.string;
//某种外星语也使用英文小写字母，但可能顺序 order 不同。字母表的顺序（order）是一些小写字母的排列。
//
// 给定一组用外星语书写的单词 words，以及其字母表的顺序 order，只有当给定的单词在这种外星语中按字典序排列时，返回 true；否则，返回
//false。
//
//
//
// 示例 1：
//
//
//输入：words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
//输出：true
//解释：在该语言的字母表中，'h' 位于 'l' 之前，所以单词序列是按字典序排列的。
//
// 示例 2：
//
//
//输入：words = ["word","world","row"], order = "worldabcefghijkmnpqstuvxyz"
//输出：false
//解释：在该语言的字母表中，'d' 位于 'l' 之后，那么 words[0] > words[1]，因此单词序列不是按字典序排列的。
//
// 示例 3：
//
//
//输入：words = ["apple","app"], order = "abcdefghijklmnopqrstuvwxyz"
//输出：false
//解释：当前三个字符 "app" 匹配时，第二个字符串相对短一些，然后根据词典编纂规则 "apple" > "app"，因为 'l' > '∅'，其中 '∅
//' 是空白字符，定义为比任何其他字符都小（更多信息）。
//
//
//
//
// 提示：
//
//
// 1 <= words.length <= 100
// 1 <= words[i].length <= 20
// order.length == 26
// 在 words[i] 和 order 中的所有字符都是英文小写字母。
//
//
// Related Topics 数组 哈希表 字符串 👍 221 👎 0

/**
 * @author mayanwei
 * @date 2022-10-16.
 */
public class _953_验证外星语词典{
    class Solution{
        /**
         * 给定字母表 order,检测给定的字符串数组words是否按照order的字典升序排列， 只需要依次检测words中的字符串
         * 前一个字符串和后一个字符串在给定的字母表下的字典序即可。
         * 时间复杂度：O(m*n)
         */
        public boolean isAlienSorted(String[] words, String order) {
            int[] index = new int[26];
            for (int i = 0; i < order.length(); i++) {
                index[order.charAt(i) - 'a'] = i;
            }

            for (int i = 1; i < words.length; i++) {
                boolean valid = false;
                for (int j = 0; j < words[i - 1].length() && j < words[i].length(); j++) {
                    int prev = index[words[i - 1].charAt(j) - 'a'];
                    int curr = index[words[i].charAt(j) - 'a'];
                    if (prev < curr) {
                        valid = true;
                        break;
                    }
                    else if (prev > curr) {
                        return false;
                    }
                }
                if (!valid) {
                    /**比较两个字符串的长度 */
                    if (words[i - 1].length() > words[i].length()) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
