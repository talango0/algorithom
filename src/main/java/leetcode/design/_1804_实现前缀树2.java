package leetcode.design;
//A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.
//
//Implement the Trie class:
//
//Trie() Initializes the trie object.
//void insert(String word) Inserts the string word into the trie.
//int countWordsEqualTo(String word) Returns the number of instances of the string word in the trie.
//int countWordsStartingWith(String prefix) Returns the number of strings in the trie that have the string prefix as a prefix.
//void erase(String word) Erases the string word from the trie.
//Example 1:
//
//Input
//["Trie", "insert", "insert", "countWordsEqualTo", "countWordsStartingWith", "erase", "countWordsEqualTo", "countWordsStartingWith", "erase", "countWordsStartingWith"]
//[[], ["apple"], ["apple"], ["apple"], ["app"], ["apple"], ["apple"], ["app"], ["apple"], ["app"]]
//Output
//[null, null, null, 2, 2, null, 1, 1, null, 0]
//
//Explanation
//Trie trie = new Trie();
//trie.insert("apple");               // Inserts "apple".
//trie.insert("apple");               // Inserts another "apple".
//trie.countWordsEqualTo("apple");    // There are two instances of "apple" so return 2.
//trie.countWordsStartingWith("app"); // "app" is a prefix of "apple" so return 2.
//trie.erase("apple");                // Erases one "apple".
//trie.countWordsEqualTo("apple");    // Now there is only one instance of "apple" so return 1.
//trie.countWordsStartingWith("app"); // return 1
//trie.erase("apple");                // Erases "apple". Now the trie is empty.
//trie.countWordsStartingWith("app"); // return 0
//Constraints:
//
//1 <= word.length, prefix.length <= 2000
//word and prefix consist only of lowercase English letters.
//At most 3 * 104 calls in total will be made to insert, countWordsEqualTo, countWordsStartingWith, and erase.
//It is guaranteed that for any function call to erase, the string word will exist in the trie.
//
//————————————————
//版权声明：本文为CSDN博主「CP Coding」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/hgq522/article/details/121780688

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-07-23.
 */
public class _1804_实现前缀树2{
    class Solution{
        class Trie{
            TrieMap<Integer> map = new TrieMap<>();

            public void insert(String word) {
                if (!map.containsKey(word)) {
                    map.put(word, 1);
                }
                else {
                    map.put(word, map.get(word) + 1);
                }
            }

            public int countWordsEqualTo(String word) {
                if (!map.containsKey(word)) {
                    return 0;
                }
                else {
                    return map.get(word);
                }
            }

            public int countWordsStartWith(String prefix) {
                int res = 0;
                for (String key : map.keysWithPrefix(prefix)) {
                    res += map.get(key);
                }
                return res;
            }

            public void erase(String word) {
                Integer count = map.get(word);
                if (count - 1 == 0) {
                    map.remove(word);
                }
                else {
                    map.put(word, count - 1);
                }
            }
        }

        class TrieMap<V>{
            private int size;
            private TrieNode<V> root;
            private static final int R = 256;

            private class TrieNode<V>{
                V val;
                TrieNode<V>[] children = new TrieNode[256];
            }

            public boolean containsKey(String key) {
                return get(key) != null;
            }

            // add/update
            public void put(String key, V value) {
                if (!containsKey(key)) {
                    size += 1;
                }
                put(root, key, value, 0);
            }

            private <V> TrieNode put(TrieNode<V> node, String key, V value, int i) {
                if (node == null) {
                    node = new TrieNode<>();
                }
                if (i == key.length()) {
                    node.val = value;
                    return node;
                }
                char c = key.charAt(i);
                node.children[c] = put(node.children[c], key, value, i + 1);
                return node;
            }

            public V get(String key) {
                TrieNode<V> node = getNode(key);
                if (node != null && node.val != null) {
                    return node.val;
                }
                return null;
            }

            private TrieNode<V> getNode(String key) {
                TrieNode<V> node = root;
                if (node == null) {
                    return null;
                }
                for (int i = 0; i < key.length(); i++) {
                    if (node == null) {
                        return null;
                    }
                    char c = key.charAt(i);
                    node = node.children[c];
                }
                return node;
            }

            public List<String> keysWithPrefix(String prefix) {
                List<String> keys = new LinkedList<>();
                TrieNode<V> node = getNode(prefix);
                if (node == null) {
                    return keys;
                }
                travers(root, new StringBuilder(prefix), keys);
                return keys;
            }

            private void travers(TrieNode<V> node, StringBuilder path, List<String> keys) {
                if (node == null) {
                    return;
                }
                if (node.val != null) {
                    keys.add(path.toString());
                }
                // 回溯算法
                for (int c = 0; c < R; c++) {
                    path.append(c);
                    travers(node.children[c], path, keys);
                    path.deleteCharAt(path.length()-1);
                }
            }

            public void remove(String key) {
                if (!containsKey(key)) {
                    return;
                }
                root = remove(root, key, 0);
                size--;

            }

            private TrieNode<V> remove(TrieNode<V> node, String key, int i) {
                if (node == null) {
                    return null;
                }
                if (i == key.length()) {
                    node.val = null;
                }
                else {
                    char c = key.charAt(i);
                    node.children[c] = remove(node.children[c], key, i+1);
                }

                if (node.val != null) {
                    return node;
                }
                for (int j = 0; j<R; j++) {
                    if (node.val != null) {
                        return node;
                    }
                }
                return null;
            }

        }
    }

}
