package leetcode.jzhoffer;

import leetcode.design._677_键值映射;

import java.util.LinkedList;
import java.util.List;
//实现一个 MapSum 类，支持两个方法，insert 和 sum：
//
//
// MapSum() 初始化 MapSum 对象
// void insert(String key, int val) 插入 key-val 键值对，字符串表示键 key ，整数表示值 val 。如果键
//key 已经存在，那么原来的键值对将被替代成新的键值对。
// int sum(string prefix) 返回所有以该前缀 prefix 开头的键 key 的值的总和。
//
//
//
//
// 示例：
//
//
//输入：
//inputs = ["MapSum", "insert", "sum", "insert", "sum"]
//inputs = [[], ["apple", 3], ["ap"], ["app", 2], ["ap"]]
//输出：
//[null, null, 3, null, 5]
//
//解释：
//MapSum mapSum = new MapSum();
//mapSum.insert("apple", 3);
//mapSum.sum("ap");           // return 3 (apple = 3)
//mapSum.insert("app", 2);
//mapSum.sum("ap");           // return 5 (apple + app = 3 + 2 = 5)
//
//
//
//
// 提示：
//
//
// 1 <= key.length, prefix.length <= 50
// key 和 prefix 仅由小写英文字母组成
// 1 <= val <= 1000
// 最多调用 50 次 insert 和 sum
//
//
//
//
//
// 注意：本题与主站 677 题相同： https://leetcode-cn.com/problems/map-sum-pairs/
//
// Related Topics 设计 字典树 哈希表 字符串 👍 23 👎 0


/**
 * @author mayanwei
 * @date 2022-11-02.
 * @see _677_键值映射
 */
public class 剑指_Offer_II_066_单词之和{
    class MapSum{
        final TrieMap<Integer> map;

        /**
         * Initialize your data structure here.
         */
        public MapSum() {
            map = new TrieMap<>();
        }

        public void insert(String key, int val) {
            map.put(key, val);
        }

        public int sum(String prefix) {
            List<String> keys = map.keysWithPrefix(prefix);
            int res = 0;
            System.out.println(keys.size());
            for (String key : keys) {
                res += map.get(key);
            }
            return res;
        }
    }

    class TrieMap<V>{
        //ASCII 码个数
        private static final int R = 256;
        // 当前存在Map中的键值对个数
        private int size = 0;
        // Tire 树的根节点
        private TrieNode<V> root;

        private class TrieNode<V>{
            V val = null;
            TrieNode<V>[] children = new TrieNode[R];
        }

        public void put(String key, V val) {
            if (!containsKey(key)) {
                // 新增键值对
                size++;
            }
            // 需要一个额外的函数，并接收其返回值
            root = put(root, key, val, 0);
        }

        private boolean containsKey(String key) {
            return get(key) != null;
        }

        private TrieNode<V> put(TrieNode<V> node, String key, V val, int index) {
            if (node == null) {
                //System.out.println("put" + key + val + " " + index);
                node = new TrieNode<>();
            }
            if (index == key.length()) {
                // System.out.println("put" + key + val + " " + index);
                node.val = val;
                return node;
            }
            char c = key.charAt(index);
            // 递归插入子节点，并接收返回值
            node.children[c] = put(node.children[c], key, val, index + 1);
            return node;
        }

        // 搜索 key对应的值，不存在则返回
        public V get(String key) {
            // 从 root 开始搜索 key
            TrieNode<V> x = getNode(root, key);

            if (x == null || x.val == null) {
                return null;
            }
            return x.val;
        }

        private TrieNode<V> getNode(TrieNode<V> node, String key) {
            TrieNode<V> p = node;
            // 从节点 node 开始搜索 key
            for (int i = 0; i < key.length(); i++) {
                if (p == null) {
                    // 无法向下搜索
                    System.out.println("key " + key + " " + i);
                    return null;
                }
                // 向下搜索
                char c = key.charAt(i);
                p = p.children[c];
            }
            return p;
        }

        public List<String> keysWithPrefix(String prefix) {
            List<String> res = new LinkedList<>();
            // 找到匹配 prefix 在 Trie 树中的那个节点
            TrieNode<V> x = getNode(root, prefix);
            if (x == null) {
                return res;
            }
            // DFS 遍历以 x 为根的这棵 Trie 树
            traverse(x, new StringBuilder(prefix), res);
            return res;
        }

        // 遍历以 node 节点为根的 Trie 树，找到所有键
        private void traverse(TrieNode<V> node, StringBuilder path, List<String> res) {
            if (node == null) {
                // 到达 Trie 树底部叶子结点
                return;
            }

            if (node.val != null) {
                // 找到一个 key，添加到结果列表中
                res.add(path.toString());
            }

            // 回溯算法遍历框架
            for (char c = 0; c < R; c++) {
                // 做选择
                path.append(c);
                traverse(node.children[c], path, res);
                // 撤销选择
                path.deleteCharAt(path.length() - 1);
            }
        }
    }

}
