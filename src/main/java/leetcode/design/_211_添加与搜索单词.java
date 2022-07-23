package leetcode.design;

import java.util.LinkedList;
import java.util.List;
//请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配 。
//
// 实现词典类 WordDictionary ：
//
//
// WordDictionary() 初始化词典对象
// void addWord(word) 将 word 添加到数据结构中，之后可以对它进行匹配
// bool search(word) 如果数据结构中存在字符串与 word 匹配，则返回 true ；否则，返回 false 。word 中可能包含一些
//'.' ，每个 . 都可以表示任何一个字母。
//
//
//
//
// 示例：
//
//
//输入：
//["WordDictionary","addWord","addWord","addWord","search","search","search",
//"search"]
//[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
//输出：
//[null,null,null,null,false,true,true,true]
//
//解释：
//WordDictionary wordDictionary = new WordDictionary();
//wordDictionary.addWord("bad");
//wordDictionary.addWord("dad");
//wordDictionary.addWord("mad");
//wordDictionary.search("pad"); // 返回 False
//wordDictionary.search("bad"); // 返回 True
//wordDictionary.search(".ad"); // 返回 True
//wordDictionary.search("b.."); // 返回 True
//
//
//
//
// 提示：
//
//
// 1 <= word.length <= 25
// addWord 中的 word 由小写英文字母组成
// search 中的 word 由 '.' 或小写英文字母组成
// 最多调用 10⁴ 次 addWord 和 search
//
// Related Topics 深度优先搜索 设计 字典树 字符串 👍 445 👎 0


public class _211_添加与搜索单词{

    /**
     * solution1
     */
    class solution1{
        class WordDictionary{
            private Trie root;

            public WordDictionary() {
                root = new Trie();
            }

            public void addWord(String word) {
                root.insert(word);
            }

            public boolean search(String word) {
                return dfs(word, 0, root);
            }

            private boolean dfs(String word, int index, Trie node) {
                if (index == word.length()) {
                    return node.isEnd();
                }
                char ch = word.charAt(index);
                if (Character.isLetter(ch)) {
                    int childIndex = ch - 'a';
                    Trie child = node.getChildren()[childIndex];
                    if (child != null && dfs(word, index + 1, child)) {
                        return true;
                    }
                }
                else {
                    for (int i = 0; i < 26; i++) {
                        Trie child = node.getChildren()[i];
                        if (child != null && dfs(word, index + 1, child)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }

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

            public Trie[] getChildren() {
                return children;
            }

            public boolean isEnd() {
                return isEnd;
            }
        }
    }

    /**
     * Solution2
     */
    class solution2{
        class WordDictionary{
            TrieSet set = new TrieSet();

            public WordDictionary() {

            }

            public void addWord(String word) {
                set.add(word);
            }

            public boolean search(String word) {
                return set.hasKeyWithPattern(word);
            }
        }

        class TrieMap<V>{
            private static final int R = 256;
            private int size = 0;
            private TrieNode<V> root = null;

            class TrieNode<V>{
                V val = null;
                TrieNode<V>[] children = new TrieNode[R];
            }

            /**
             * 增，改
             */
            public void put(String key, V value) {
                if (!containsKey(key)) {
                    //新增键值对
                    size++;
                }
                root = put(root, key, value, 0);
            }

            private TrieNode<V> put(TrieNode<V> node, String key, V value, int index) {
                if (node == null) {
                    node = new TrieNode<>();
                }
                // key 的路径已插入完成，将值 val 存入节点
                if (key.length() == index) {
                    node.val = value;
                    return node;
                }
                char c = key.charAt(index);
                node.children[c] = put(node.children[c], key, value, index + 1);
                return node;
            }

            /**
             * 删
             */
            public void remove(String key) {
                // 如果本身不含 key，则返回
                // 如果删除key对应的val ，向上递归，如果每个节点含有值不为空的节点为止，否则删除改节点
                if (!containsKey(key)) {
                    return;
                }
                // 递归删除 key
                root = remove(root, key, 0);
                size--;
            }

            private TrieNode<V> remove(TrieNode<V> node, String key, int index) {
                if (node == null) {
                    return null;
                }
                if (index == key.length()) {
                    // 找到了 key 对应的TrieNode， 删除 val
                    node.val = null;
                }
                else {
                    char c = key.charAt(index);
                    node.children[c] = remove(node.children[c], key, index + 1);
                }

                // 后序遍历的位置，离开的时候要判断子树中是否存在 val不为空的，如果不存在则删除该节点
                for (int c = 0; c < R; c++) {
                    if (node.children[c] != null) {
                        // 只要存在一个子节点（后缀树枝）， 就不需要被清理
                        return node;
                    }
                }
                return null;
            }

            /**
             * 查
             */
            public V get(String key) {
                TrieNode<V> x = getNode(root, key);
                if (x == null || x.val == null) {
                    return null;
                }
                return x.val;
            }

            /**
             * 判断 key 是否存在Map中
             */
            public boolean containsKey(String key) {
                return get(key) != null;
            }

            public boolean hasKeyWithPrefix(String prefix) {
                return getNode(root, prefix) != null;
            }

            public String shortestPrefixOf(String query) {
                TrieNode<V> p = root;
                // 从节点 node 开始搜索 key
                for (int i = 0; i < query.length(); i++) {
                    if (p == null) {
                        return "";
                    }
                    if (p.val != null) {
                        return query.substring(0, i);
                    }
                    char c = query.charAt(i);
                    p = p.children[c];
                }

                if (p != null && p.val != null) {
                    return query;
                }
                return "";
            }

            public String longestPrefixOf(String query) {
                TrieNode<V> p = root;
                int max_len = 0;
                for (int i = 0; i < query.length(); i++) {
                    if (p == null) {
                        break;
                    }
                    if (p.val != null) {
                        max_len = i;
                    }
                    char c = query.charAt(i);
                    p = p.children[c];
                }

                if (p != null && p.val != null) {
                    return query;
                }
                return query.substring(0, max_len);
            }

            public List<String> keysWithPrefix(String prefix) {
                List<String> res = new LinkedList<>();
                TrieNode<V> x = getNode(root, prefix);
                if (x == null) {
                    return res;
                }
                traverse(x, new StringBuilder(prefix), res);
                return res;
            }

            private void traverse(TrieNode<V> node, StringBuilder path, List<String> res) {
                if (node == null) {
                    return;
                }
                if (node.val != null) {
                    res.add(path.toString());
                }

                for (char c = 0; c < R; c++) {
                    path.append(c);
                    traverse(node.children[c], path, res);
                    path.deleteCharAt(path.length() - 1);
                }
            }

            public List<String> keysWithPattern(String pattern) {
                List<String> res = new LinkedList<>();
                traverse(root, new StringBuilder(), pattern, 0, res);
                return res;
            }

            // 遍历函数，尝试在「以 node 为根的 Trie 树中」匹配 pattern[i..]
            private void traverse(TrieNode<V> node, StringBuilder path, String pattern, int i, List<String> res) {
                if (node == null) {
                    // 树枝不存在，即匹配失败
                    return;
                }
                if (i == pattern.length()) {
                    // pattern 匹配完成
                    if (node.val != null) {
                        // 如果这个节点存储着 val，则找到一个匹配的键
                        res.add(path.toString());
                    }
                    return;
                }
                char c = pattern.charAt(i);
                if (c == '.') {
                    // pattern[i] 是通配符，可以变化成任意字符
                    // 多叉树（回溯算法）遍历框架
                    for (char j = 0; j < R; j++) {
                        path.append(j);
                        traverse(node.children[j], path, pattern, i + 1, res);
                        path.deleteCharAt(path.length() - 1);
                    }
                }
                else {
                    // pattern[i] 是普通字符 c
                    path.append(c);
                    traverse(node.children[c], path, pattern, i + 1, res);
                    path.deleteCharAt(path.length() - 1);
                }
            }


            // 判断是和否存在前缀为 prefix 的键
            public boolean hasKeyWithPattern(String pattern) {
                // 从 root 节点开始匹配 pattern[0..]
                return hasKeyWithPattern(root, pattern, 0);
            }

            // 函数定义：从 node 节点开始匹配 pattern[i..]，返回是否成功匹配
            private boolean hasKeyWithPattern(TrieNode<V> node, String pattern, int i) {
                if (node == null) {
                    // 树枝不存在，即匹配失败
                    return false;
                }
                if (i == pattern.length()) {
                    // 模式串走到头了，看看匹配到的是否是一个键
                    return node.val != null;
                }
                char c = pattern.charAt(i);
                // 没有遇到通配符
                if (c != '.') {
                    // 从 node.children[c] 节点开始匹配 pattern[i+1..]
                    return hasKeyWithPattern(node.children[c], pattern, i + 1);
                }
                // 遇到通配符
                for (int j = 0; j < R; j++) {
                    // pattern[i] 可以变化成任意字符，尝试所有可能，只要遇到一个匹配成功就返回
                    if (hasKeyWithPattern(node.children[j], pattern, i + 1)) {
                        return true;
                    }
                }
                // 都没有匹配
                return false;
            }

            private TrieNode<V> getNode(TrieNode<V> node, String key) {
                TrieNode<V> p = node;
                for (int i = 0; i < key.length(); i++) {
                    if (p == null) {
                        return null;
                    }
                    char c = key.charAt(i);
                    p = p.children[c];
                }
                return p;
            }

            public int size() {
                return size;
            }

        }

        class TrieSet{
            // 底层用一个 TrieMap，键就是 TrieSet，值仅仅起到占位的作用
            // 值的类型可以随便设置，我参考 Java 标准库设置成 Object
            private final TrieMap<Object> map = new TrieMap<>();

            /***** 增 *****/

            // 在集合中添加元素 key
            public void add(String key) {
                map.put(key, new Object());
            }

            /***** 删 *****/

            // 从集合中删除元素 key
            public void remove(String key) {
                map.remove(key);
            }

            /***** 查 *****/

            // 判断元素 key 是否存在集合中
            public boolean contains(String key) {
                return map.containsKey(key);
            }

            // 在集合中寻找 query 的最短前缀
            public String shortestPrefixOf(String query) {
                return map.shortestPrefixOf(query);
            }

            // 在集合中寻找 query 的最长前缀
            public String longestPrefixOf(String query) {
                return map.longestPrefixOf(query);
            }

            // 在集合中搜索前缀为 prefix 的所有元素
            public List<String> keysWithPrefix(String prefix) {
                return map.keysWithPrefix(prefix);
            }

            // 判断集合中是否存在前缀为 prefix 的元素
            public boolean hasKeyWithPrefix(String prefix) {
                return map.hasKeyWithPrefix(prefix);
            }

            // 通配符 . 匹配任意字符，返回集合中匹配 pattern 的所有元素
            public List<String> keysWithPattern(String pattern) {
                return map.keysWithPattern(pattern);
            }

            // 通配符 . 匹配任意字符，判断集合中是否存在匹配 pattern 的元素
            public boolean hasKeyWithPattern(String pattern) {
                return map.hasKeyWithPattern(pattern);
            }

            // 返回集合中元素的个数
            public int size() {
                return map.size();
            }
        }
    }

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
}
