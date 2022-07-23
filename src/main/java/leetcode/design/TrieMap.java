package leetcode.design;

import leetcode.tree.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * @see _677_键值映射
 * @see _1804_实现前缀树2
 * @param <V>
 */
public class TrieMap<V> implements ITrieMap<V> {
    // ASCII 码的个数
    private  static final int R = 256;
    // 当前存在 Map 中的键值对个数
    private int size;

    // Trie 树的根节点
    private TrieNode<V> root = null;

    private static class TrieNode<V> {
        V val = null;
        TrieNode<V> [] children = new TrieNode[R];
    }

    // 从节点 node 开始搜索 key如果存在返回对应节点，否则返回null
    private TrieNode<V> getNode(TrieNode<V> node, String key) {
        TrieNode<V> p = node;
        //从节点node开始搜索key
        for (int i = 0; i<key.length(); i++) {
            if (p == null) {
                //无法向下进行搜索
                return null;
            }
            // 向下进行搜索
            char c = key.charAt(i);
            p = p.children[c];
        }
        return p;
    }

    @Override
    public void put(String key, V value) {
        if (!containsKey(key)) {
            // 新增键值对
            size ++;
        }
        root = put(root, key, value, 0);
    }

    // 定义： 向以 node 为根的 trie 树中插入 key[i...]，返回插入完成的根节点
    private TrieNode<V> put(TrieNode<V> node, String key, V value, int i) {
        if (node == null) {
            // 如果树枝不存在，新建
            node = new TrieNode<>();
        }
        if (i == key.length()) {
            // key 的路径一插入完成，将值 val 存入节点
            node.val = value;
            return node;
        }
        char c = key.charAt(i);
        // 递归插入子节点，并接受返回值
        node.children[c] = put(node.children[c], key, value, i+1);
        return node;

    }

    @Override
    public void remove(String key) {
        if (!containsKey(key)) {
            return;
        }
        // 递归修改数据结构要接收函数的返回值
        root = remove(root, key, 0);
        size--;
    }

    // 定义：在以 node 为根的 Trie 树中删除 key[i..]，返回删除后的根节点
    private TrieNode<V> remove(TrieNode<V> node, String key, int i) {
        //后序位置的特点：
        //一个节点要先递归处理子树，然后在后序位置检查自己的val字段和children列表，判断自己是否需要被删除。
        //如果自己的val字段为空，说明自己没有存储值，如果同时自己的children数组全是空指针，说明自己下面也没有接树枝，即不是任何一个键的前缀。这种情况下这个节点就没有存在的意义了，应该删掉自己。
        if (node == null) {
            return null;
        }
        if (i == key.length()) {
            // 找到了 key 对应的 TrieNode，删除 val
            node.val = null;
        }
        else {
            char c = key.charAt(i);
            node.children[c] = remove(node.children[c], key, i+1);
        }
        // 后序位置 递归路径上的节点可能需要被清理
        if (node.val != null) {
            //如果该 TrieNode存储这 val ，不需要被清理
            return node;
        }

        // 检查该 TrieNode 是否还有后缀
        for (int c = 0; c<R; c++){
            if (node.children[c] != null) {
                // 只要存在一个子节点（后缀树枝）， 就不需要被清理
                return node;
            }
        }
        // 既没有存储 val，也没有后缀树枝，则该节点需要被清理
        return null;

    }


    @Override
    public V get(String key) {
        // 从 root 开始搜索 key
        TrieNode<V> x = getNode(root, key);
        // 注意，就算getNode(key) 的返回值 x 非空，也只能说明字符串key是一个前缀，除非 x.val 同时非空，才能判断键 key 存在
        if (x == null || x.val == null) {
            // x 为空，或者x 的val 字段为空都说明 key 没有对应的值
            return null;
        }
        else{
            return x.val;
        }
    }

    @Override
    public boolean containsKey(String key) {
        return get(key) != null;
    }

    @Override
    public String shortestPrefixOf(String query) {
        // 只要第一次遇到存有 val 的节点的时候返回就行了
        TrieNode<V> p = root;
        for (int i = 0; i<query.length(); i++) {
            if (p == null) {
                // 无法向下搜索
                return "";
            }
            if (p.val != null) {
                // 找到一个键是 query 的前缀
                return query.substring(0, i);
            }

            char c = query.charAt(i);
            p = p.children[c];
        }
        // 注意for 循环之后还需要检查一下
        if (p!=null && p.val != null) {
            //如果 query 本上就是一个键
            return query;
        }
        return "";
    }

    @Override
    public String longestPrefixOf(String query) {
        TrieNode<V> p = root;
        int maxLen = 0;

        for (int i = 0; i < query.length(); i++) {
            if (p == null) {
                // 无法向下搜索
                break;
            }
            if (p.val != null) {
                // 找到一个键是 query 的前缀，更新前缀的最大长度
                maxLen = i;
            }
            // 向下搜索
            char c = query.charAt(i);
            p = p.children[c];
        }
        if (p != null && p.val != null) {
            return query;
        }

        return query.substring(0, maxLen);
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> res = new LinkedList<>();
        TrieNode<V> x = getNode(root, prefix);
        if (x == null) {
            return res;
        }
        // DFS 遍历以 x 为根的这颗 Trie 树
        traverse(x, new StringBuilder(prefix), res);
        return res;
    }

    private void traverse(TrieNode<V> node, StringBuilder path, List<String> res) {
        if (node == null) {
            //到达 Trie 树底部叶子节点
            return;
        }
        if (node.val != null) {
            // 找到一个key，添加到结果列表
            res.add(path.toString());
        }

        // 回溯算法遍历框架
        for (char c = 0; c<R; c++) {
            // 做选择
            path.append(c);
            traverse(node.children[c], path, res);
            // 撤销选择
            path.deleteCharAt(path.length() - 1);
        }

    }

    @Override
    public boolean hasKeyWithPrefix(String prefix) {
        // 只要能找到 prefix 对应的节点，就是存在前缀
        return getNode(root, prefix) != null;
    }

    @Override
    public List<String> keysWithPattern(String pattern) {
        List<String> res = new LinkedList<>();
        traverse(root, new StringBuilder(), pattern, 0, res);
        return null;
    }

    // 遍历函数尝试在以node为根的Trie 树中 匹配 pattern[i...]
    private void traverse(TrieNode<V> node, StringBuilder path, String pattern, int i, List<String> res) {
        if (node == null) {
            //树枝不存在，即匹配失败
            return;
        }
        if (i == pattern.length()) {
            // pattern 匹配完成
            if (node.val != null) {
                // 如果这个节点存储着 val，则找到一个匹配的键
                res.add(path.toString());
            }
        }

        char c = pattern.charAt(i);
        if (c == '.') {
            // pattern[i] 是通配符，可以变化成任意字符
            // 多叉树（回溯算法）遍历框架
            for( char j = 0; j<R; j++) {
                path.append(j);
                traverse(node.children[j], path, pattern, i+1, res);
                path.deleteCharAt(path.length()-1);
            }
        }
        else {
            // pattern[i] 是普通字符
            path.append(c);
            traverse(node.children[c], path, pattern, i+1, res);
            path.deleteCharAt(path.length()-1);
        }
    }

    @Override
    public boolean hasKeyWithPattern(String pattern) {
//        return !keysWithPattern(pattern).isEmpty();
        return hasKeyWithPattern(root, pattern, 0);
    }

    private boolean hasKeyWithPattern(TrieNode<V> node, String pattern, int i) {
        if (node == null) {
            // 树枝不存在，即匹配失败
            return false;
        }
        if ( i == pattern.length()) {
            // 模式串走到头了，看着匹配到的是否是一个键
            return node.val != null;
        }
        char c = pattern.charAt(i);
        if (c!='.') {
            return hasKeyWithPattern(node.children[c], pattern, i+1);
        }
        else {
            // 得到通配符
            for (int j = 0; j<R; j++) {
                if (hasKeyWithPattern(node.children[j], pattern, i+1)) {
                    return true;
                }
            }
        }
        // 未匹配到
        return false;
    }

    @Override
    public int size() {
        return size;
    }
}
