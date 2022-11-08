package leetcode.design;

//实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。
//
// 示例:
//
// Trie trie = new Trie();
//
//trie.insert("apple");
//trie.search("apple");   // 返回 true
//trie.search("app");     // 返回 false
//trie.startsWith("app"); // 返回 true
//trie.insert("app");
//trie.search("app");     // 返回 true
//
// 说明:
//
//
// 你可以假设所有的输入都是由小写字母 a-z 构成的。
// 保证所有输入均为非空字符串。
//
// Related Topics 设计 字典树
// 👍 457 👎 0


import org.junit.Test;

public class _208_实现Trie前缀树{
//    class Trie {
//
//        /** Initialize your data structure here. */
//        public Trie() {
//
//        }
//
//        /** Inserts a word into the trie. */
//        public void insert(String word) {
//
//        }
//
//        /** Returns if the word is in the trie. */
//        public boolean search(String word) {
//
//        }
//
//        /** Returns if there is any word in the trie that starts with the given prefix. */
//        public boolean startsWith(String prefix) {
//
//        }
//    }

    /**
     * Your Trie object will be instantiated and called as such:
     * Trie obj = new Trie();
     * obj.insert(word);
     * boolean param_2 = obj.search(word);
     * boolean param_3 = obj.startsWith(prefix);
     */


    class Trie{

        private Node root;

        private class Node{
            private Object val;
            private Node[] next = new Node[256];
        }

        /**
         * Initialize your data structure here.
         */
        public Trie() {

        }

        /**
         * Inserts a word into the trie.
         */
        public void insert(String word) {
            root = insert(root, word, 0);
        }

        private Node insert(Node x, String s, int d) {
            if (x == null) {
                x = new Node();
            }
            if (d == s.length()) {
                x.val = new Object();//表示s对应的val
                return x;
            }
            char c = s.charAt(d);
            x.next[c] = insert(x.next[c], s, d + 1);
            return x;
        }

        /**
         * Returns if the word is in the trie.
         */
        public boolean search(String word) {
            return search(root, word, 0);
        }

        private boolean search(Node x, String word, int d) {
            if (x == null) {
                return false;
            }
            if (d == word.length()) {
                if (x.val != null) {
                    return true;
                }
                else {
                    return false;
                }
            }
            return search(x.next[word.charAt(d)], word, d + 1);
        }

        /**
         * Returns if there is any word in the trie that starts with the given prefix.
         */
        public boolean startsWith(String prefix) {
            return startsWith(root, prefix, 0);
        }

        private boolean startsWith(Node x, String prefix, int d) {
            if (x == null) {
                if (d < prefix.length()) {
                    return false;
                }
                else {
                    return true;
                }
            }
            if (d == prefix.length()) {
                return true;
            }
            return startsWith(x.next[prefix.charAt(d)], prefix, d + 1);
        }


    }

    class Solution2{
        class Trie{
            private Trie[] children;
            private boolean isEnd;

            public Trie() {
                children = new Trie[26];
                isEnd = false;
            }

            public void insert(String word) {
                Trie node = this;
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

            public boolean search(String word) {
                Trie node = searchPrefix(word);
                return node != null && node.isEnd;
            }

            public boolean startsWith(String prefix) {
                return searchPrefix(prefix) != null;
            }

            private Trie searchPrefix(String prefix) {
                Trie node = this;
                for (int i = 0; i < prefix.length(); i++) {
                    char ch = prefix.charAt(i);
                    int index = ch - 'a';
                    if (node.children[index] == null) {
                        return null;
                    }
                    node = node.children[index];
                }
                return node;
            }
        }
    }

    @Test
    public void testTrie() {
        Trie trie = new Trie();
        trie.insert("hello");
        System.out.println(trie.search("hell"));
        System.out.println(trie.search("helloa"));
        System.out.println(trie.startsWith("app"));
        trie.insert("app");
        System.out.println(trie.search("app"));
    }


}
