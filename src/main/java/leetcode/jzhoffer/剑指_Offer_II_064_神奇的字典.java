package leetcode.jzhoffer;

import leetcode.design._676_实现一个魔法字典;

/**
 * @author mayanwei
 * @date 2022-11-03.
 * @see _676_实现一个魔法字典
 */
public class 剑指_Offer_II_064_神奇的字典{
    /**
     * 时间复杂度 O(nl + q*Σ)
     * 空间复杂度 O(nl),即字典树需要使用的空间
     */
    class MagicDictionary{

        private Trie root;

        /**
         * Initialize your data structure here.
         */
        public MagicDictionary() {
            root = new Trie();
        }

        public void buildDict(String[] dictionary) {
            for (String word : dictionary) {
                Trie cur = root;
                for (int i = 0; i < word.length(); i++) {
                    char ch = word.charAt(i);
                    int idx = ch - 'a';
                    if (cur.child[idx] == null) {
                        cur.child[idx] = new Trie();
                    }
                    cur = cur.child[idx];
                }
                cur.isFinished = true;
            }
        }

        public boolean search(String searchWord) {
            return dfs(searchWord, root, 0, false);
        }

        private boolean dfs(String searchWord, Trie node, int pos, boolean modified) {
            if (pos == searchWord.length()) {
                return modified && node.isFinished;
            }
            int idx = searchWord.charAt(pos) - 'a';
            if (node.child[idx] != null) {
                if (dfs(searchWord, node.child[idx], pos + 1, modified)) {
                    return true;
                }
            }
            if (!modified) {
                for (int i = 0; i < 26; i++) {
                    if (i != idx && node.child[i] != null) {
                        if (dfs(searchWord, node.child[i], pos + 1, true)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    class Trie{
        boolean isFinished;
        Trie[] child;

        public Trie() {
            isFinished = false;
            child = new Trie[26];
        }
    }

}
