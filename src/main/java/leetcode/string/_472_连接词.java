package leetcode.string;
//给你一个 不含重复 单词的字符串数组 words ，请你找出并返回 words 中的所有 连接词 。
//
// 连接词 定义为：一个完全由给定数组中的至少两个较短单词组成的字符串。
//
//
//
// 示例 1：
//
//
//输入：words = ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses",
//"rat","ratcatdogcat"]
//输出：["catsdogcats","dogcatsdog","ratcatdogcat"]
//解释："catsdogcats" 由 "cats", "dog" 和 "cats" 组成;
//     "dogcatsdog" 由 "dog", "cats" 和 "dog" 组成;
//     "ratcatdogcat" 由 "rat", "cat", "dog" 和 "cat" 组成。
//
//
// 示例 2：
//
//
//输入：words = ["cat","dog","catdog"]
//输出：["catdog"]
//
//
//
// 提示：
//
//
// 1 <= words.length <= 10⁴
// 0 <= words[i].length <= 30
// words[i] 仅由小写字母组成
// 0 <= sum(words[i].length) <= 10⁵
//
//
// Related Topics 深度优先搜索 字典树 数组 字符串 动态规划 👍 271 👎 0

import leetcode.design._208_实现Trie前缀树;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-09-21.
 * @see _140_单词拆分II
 * @see _208_实现Trie前缀树
 */
public class _472_连接词{
    class Solution{
        /**
         * 1. 将数组 words 按照字符串长度递增的顺序排序，排序后可以确保当遍历到任意单词的时，比该单词短的单词一定都遍历过，因此可以根据
         * 已经遍历过的全部单词判断当前单词是不是连接词。
         * <p>
         * 2. 将数组 words 排序后，遍历数组，跳过空字符串，对于每个非空单词，判断该单词是不是连接词，如果是连接次则将该单词加入结果数组，
         * 如果不是连接词，则将该单词加入字典序。
         * <p>
         * 3. 判断一个单词是不是连接词的做法是在字典数中搜索，从该单词的第一个字符开始，在字典树中一次搜索每个字符对应的节点，存在一下几种情况：
         * <p>
         * 如果一个字符对应的结点是单词的词尾，则找到一个更短的单词，从该字符的后一个字符开始搜索下一个更短的单词；
         * <p>
         * 如果一个字符对应的结点在字典树中不存在，则当前搜索结果失败， 从该字符的后一个字符开始搜索下一个更短的单词。
         * <p>
         * 由于一个连接词由多个更短的非空单词组成，如果存在一个较长的连接词的组成部分之一是一个较短的连接词，则一定可以将这个较短的连接词
         * 替换成更短的非空单词，因此不需要将连接次加入字典树。
         * <p>
         * 为了降低时间复杂度，需要使用记忆化搜索。对于每个单词，创建与单词相同长度的数组记录该单词的每一个小标是否被访问过，然后进行记忆化搜索。
         * 搜索的过程中，如果一个下标已经被访问过，则从该下标到末尾的部分一定不是由给定数组的一个或多个非空单词组成。（否则上次访问时已经可以
         * 知道当前单词是连接词），只有尚未访问过的下标才需要进行搜索。
         */
        Trie trie = new Trie();

        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            List<String> ans = new ArrayList<>();
            Arrays.sort(words, (a, b) -> a.length() - b.length());
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (word.length() == 0) {
                    continue;
                }
                boolean[] visited = new boolean[word.length()];
                if (dfs(word, 0, visited)) {
                    ans.add(word);
                }
                else {
                    insert(word);
                }
            }
            return ans;
        }

        public boolean dfs(String word, int start, boolean[] visited) {
            if (word.length() == start) {
                return true;
            }
            if (visited[start]) {
                return false;
            }
            visited[start] = true;
            Trie node = trie;
            for (int i = start; i < word.length(); i++) {
                char ch = word.charAt(i);
                int index = ch - 'a';
                node = node.children[index];
                if (node == null) {
                    return false;
                }
                if (node.isEnd) {
                    if (dfs(word, i + 1, visited)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void insert(String word) {
            Trie node = trie;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                int index = ch - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new Trie();
                }
                node = node.children[index];
            }
            node.isEnd = true;
        }

        class Trie{
            Trie[] children;
            boolean isEnd;

            public Trie() {
                children = new Trie[26];
                isEnd = false;
            }
        }
    }
}
