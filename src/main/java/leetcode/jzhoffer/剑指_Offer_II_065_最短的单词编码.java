package leetcode.jzhoffer;

import leetcode.trie._820_单词的压缩编码;

import java.util.HashMap;
import java.util.Map;
//单词数组 words 的 有效编码 由任意助记字符串 s 和下标数组 indices 组成，且满足：
//
//
// words.length == indices.length
// 助记字符串 s 以 '#' 字符结尾
// 对于每个下标 indices[i] ，s 的一个从 indices[i] 开始、到下一个 '#' 字符结束（但不包括 '#'）的 子字符串 恰好与
//words[i] 相等
//
//
// 给定一个单词数组 words ，返回成功对 words 进行编码的最小助记字符串 s 的长度 。
//
//
//
// 示例 1：
//
//
//输入：words = ["time", "me", "bell"]
//输出：10
//解释：一组有效编码为 s = "time#bell#" 和 indices = [0, 2, 5] 。
//words[0] = "time" ，s 开始于 indices[0] = 0 到下一个 '#' 结束的子字符串，如加粗部分所示 "time#bell#"
//words[1] = "me" ，s 开始于 indices[1] = 2 到下一个 '#' 结束的子字符串，如加粗部分所示 "time#bell#"
//words[2] = "bell" ，s 开始于 indices[2] = 5 到下一个 '#' 结束的子字符串，如加粗部分所示 "time#bell#"
//
//
// 示例 2：
//
//
//输入：words = ["t"]
//输出：2
//解释：一组有效编码为 s = "t#" 和 indices = [0] 。
//
//
//
//
// 提示：
//
//
// 1 <= words.length <= 2000
// 1 <= words[i].length <= 7
// words[i] 仅由小写字母组成
//
//
//
//
//
// 注意：本题与主站 820 题相同： https://leetcode-cn.com/problems/short-encoding-of-words/
//
// Related Topics 字典树 数组 哈希表 字符串 👍 28 👎 0

/**
 * @author mayanwei
 * @date 2022-11-03.
 * @see _820_单词的压缩编码
 */
public class 剑指_Offer_II_065_最短的单词编码{
    class Solution{
        /**
         * Trie
         * 时间复杂度 O(∑ wi)
         * 空间复杂度 O(S * ∑ wi)
         * 思路
         * 目标就是保留所有不是其他单词后缀的单词。
         * 算法
         * 去找到是否不同的单词具有相同的后缀，我们可以将其反序之后插入字典树中。例如，我们有 "time" 和 "me"，可以将 "emit" 和 "em" 插入字典树中。
         * 然后，字典树的叶子节点（没有孩子的节点）就代表没有后缀的单词，统计叶子节点代表的单词长度加一的和即为我们要的答案。
         */
        public int minimumLengthEncoding(String[] words) {
            TrieNode trie = new TrieNode();
            Map<TrieNode, Integer> nodes = new HashMap<TrieNode, Integer>();
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                TrieNode cur = trie;
                for (int j = word.length() - 1; j >= 0; j--) {
                    cur = cur.get(word.charAt(j));
                }
                nodes.put(cur, i);
            }
            int ans = 0;
            for (TrieNode node : nodes.keySet()) {
                if (node.count == 0) {
                    ans += words[nodes.get(node)].length() + 1;
                }
            }
            return ans;
        }
    }

    class TrieNode{
        TrieNode[] children;
        int count;

        TrieNode() {
            children = new TrieNode[26];
            count = 0;
        }

        public TrieNode get(char c) {
            if (children[c - 'a'] == null) {
                children[c - 'a'] = new TrieNode();
                count++;
            }
            return children[c - 'a'];
        }
    }
}
