package leetcode.jzhoffer;
//在英语中，有一个叫做 词根(root) 的概念，它可以跟着其他一些词组成另一个较长的单词——我们称这个词为 继承词(successor)。例如，词根an，跟
//随着单词 other(其他)，可以形成新的单词 another(另一个)。
//
// 现在，给定一个由许多词根组成的词典和一个句子，需要将句子中的所有继承词用词根替换掉。如果继承词有许多可以形成它的词根，则用最短的词根替换它。
//
// 需要输出替换之后的句子。
//
//
//
// 示例 1：
//
//
//输入：dictionary = ["cat","bat","rat"], sentence = "the cattle was rattled by
//the battery"
//输出："the cat was rat by the bat"
//
//
// 示例 2：
//
//
//输入：dictionary = ["a","b","c"], sentence = "aadsfasf absbs bbab cadsfafs"
//输出："a a b c"
//
//
// 示例 3：
//
//
//输入：dictionary = ["a", "aa", "aaa", "aaaa"], sentence = "a aa a aaaa aaa aaa
//aaa aaaaaa bbb baba ababa"
//输出："a a a a a a a a bbb baba a"
//
//
// 示例 4：
//
//
//输入：dictionary = ["catt","cat","bat","rat"], sentence = "the cattle was
//rattled by the battery"
//输出："the cat was rat by the bat"
//
//
// 示例 5：
//
//
//输入：dictionary = ["ac","ab"], sentence = "it is abnormal that this solution is
//accepted"
//输出："it is ab that this solution is ac"
//
//
//
//
// 提示：
//
//
// 1 <= dictionary.length <= 1000
// 1 <= dictionary[i].length <= 100
// dictionary[i] 仅由小写字母组成。
// 1 <= sentence.length <= 10^6
// sentence 仅由小写字母和空格组成。
// sentence 中单词的总量在范围 [1, 1000] 内。
// sentence 中每个单词的长度在范围 [1, 1000] 内。
// sentence 中单词之间由一个空格隔开。
// sentence 没有前导或尾随空格。
//
//
//
//
//
// 注意：本题与主站 648 题相同： https://leetcode-cn.com/problems/replace-words/
//
// Related Topics 字典树 数组 哈希表 字符串 👍 29 👎 0

import leetcode.design._211_添加与搜索单词;
import leetcode.design._648_单词替换;
import leetcode.design._677_键值映射;

import java.util.List;

/**
 * @author mayanwei
 * @date 2022-11-03.
 * @see _648_单词替换
 * @see _211_添加与搜索单词
 * @see _677_键值映射
 */
public class 剑指_Offer_II_063_替换单词{
    class Solution{
        public String replaceWords(List<String> dictionary, String sentence) {
            TrieSet set = new TrieSet();
            for (String key : dictionary) {
                set.add(key);
            }
            StringBuilder sb = new StringBuilder();
            String[] words = sentence.split(" ");
            for (int i = 0; i < words.length; i++) {
                String prefix = set.shortestPrefixOf(words[i]);
                if (!prefix.isEmpty()) {
                    sb.append(prefix);
                }
                else {
                    sb.append(words[i]);
                }
                if (i != words.length - 1) {
                    sb.append(' ');
                }
            }
            return sb.toString();
        }
    }

    class TrieSet{
        private TrieMap<Object> map = new TrieMap<Object>();

        public void add(String key) {
            map.put(key, new Object());
        }

        public String shortestPrefixOf(String key) {
            return map.shortestPrefixOf(key);
        }
    }

    class TrieMap<V>{
        public static final int R = 256;
        private int size = 0;
        private TrieNode<V> root = null;

        private class TrieNode<V>{
            V val;
            TrieNode<V>[] children = new TrieNode[R];
        }

        public void put(String key, V val) {
            root = put(root, key, val, 0);
        }

        private TrieNode<V> put(TrieNode<V> root, String key, V val, int index) {
            if (root == null) {
                root = new TrieNode<>();
            }
            if (index == key.length()) {
                root.val = val;
                return root;
            }
            char c = key.charAt(index);
            root.children[c] = put(root.children[c], key, val, index + 1);
            return root;
        }

        public String shortestPrefixOf(String key) {
            TrieNode<V> p = root;
            for (int i = 0; i < key.length(); i++) {
                if (p == null) {
                    return "";
                }
                if (p.val != null) {
                    return key.substring(0, i);
                }
                char c = key.charAt(i);
                p = p.children[c];
            }
            if (p != null && p.val != null) {
                return key;
            }
            return "";
        }

    }
}
