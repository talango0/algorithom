package leetcode.程序员面试金典;
//给定一个较长字符串big和一个包含较短字符串的数组smalls，设计一个方法，根据smalls中的每一个较短字符串，对big进行搜索。
// 输出smalls中的字符串在big里出现的所有位置positions，其中positions[i]为smalls[i]出现的所有位置。
//
//示例：
//
//输入：
//big = "mississippi"
//smalls = ["is","ppi","hi","sis","i","ssippi"]
//输出： [[1,4],[8],[],[3],[1,4,7,10],[5]]
//提示：
//
//0 <= len(big) <= 1000
//0 <= len(smalls[i]) <= 1000
//smalls的总字符数不会超过 100000。
//你可以认为smalls中没有重复字符串。
//所有出现的字符均为英文小写字母。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/multi-search-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.design._208_实现Trie前缀树;
import leetcode.design._677_键值映射;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2023-06-25.
 * @see _208_实现Trie前缀树
 * @see _677_键值映射
 */
public class _17_17_多次搜索{

    /**
     * 该方法的算法时间复杂度为 O(kbt)，k是 smalls 中最长的字符串的长度，b是较大字符串 big 的长度，t是字符串 smalls 中较小字符串的数量。
     */
    class Solution{
        Map<String, List<Integer>> searchAll(String big, String[] smalls) {
            Map<String, List<Integer>> lookup = new HashMap<>();
            for (String small : smalls) {
                List<Integer> locations = search(big, small);
                lookup.put(small, locations);
            }
            return lookup;
        }

        /**
         * 在较大字符串中找到所有较小字符串的位置
         *
         * @param big
         * @param small
         * @return
         */
        List<Integer> search(String big, String small) {
            ArrayList<Integer> locations = new ArrayList<>();
            for (int i = 0; i < big.length() - small.length(); i++) {
                if (isSubStringAtLocation(big, small, i)) {
                    locations.add(i);
                }
            }
            return locations;
        }

        boolean isSubStringAtLocation(String big, String small, int offset) {
            for (int i = 0; i < small.length(); i++) {
                if (big.charAt(offset + i) != small.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * <pre>
     * 为了优化这个问题，我们应该考虑如何一次性对 smalls 的所有元素，或者以某种方式对计算进行重用。
     * 一种方法是使用较大字符串 big 中的每个后缀创建一个类似与Trie 的数据结构。对于字符 bibs 其后缀的列表
     * 为：bibs, ibs, bs, s。
     *                         ┌──┐
     *                         └──┘
     *                ┌──────────┼──────────┐
     *              ┌─▼─┐      ┌─▼─┐      ┌─▼─┐
     *              │ b │      │ i │      │ s │
     *              └───┘      └───┘      └───┘
     *            ┌───┴───┐      └─────┐
     *          ┌─▼─┐   ┌─▼─┐        ┌─▼─┐
     *          │ i │   │ s │        │ b │
     *          └───┘   └───┘        └───┘
     *       ┌────┘                    └────┐
     *     ┌─▼─┐                          ┌─▼─┐
     *     │ b │                          │ s │
     *     └───┘                          └───┘
     *   ┌───┘
     * ┌─▼─┐
     * │ s │
     * └───┘
     * 然后要做的就是在后缀树种搜索 smalls 中的每一个字符串，注意，如果 b是一个单词，那么你会得到两个位置。
     *
     * 时间复杂度：
     * 需要 O (b^2) 的时间创建树和 O(kt) 的时间搜索位置。
     *
     * </pre>
     */
    class Solution1{
        Map<String, List<Integer>> searchAll(String big, String[] smalls) {
            Map<String, List<Integer>> lookup = new HashMap<>();
            Trie tree = createTrieFromString(big);
            for (String s : smalls) {
                // 获取每次出现的结束位置
                ArrayList<Integer> locations = tree.search(s);
                // 调整开始位置
                subtractValue(locations, s.length());
                // 插入
                lookup.put(s, locations);
            }
            return lookup;

        }

        private void subtractValue(ArrayList<Integer> locations, int delta) {
            if (locations == null)  {
                return;
            }
            for (int i = 0; i < locations.size(); i++) {
                locations.set(i, locations.get(i) - delta);
            }
        }

        private Trie createTrieFromString(String s) {
            Trie trie = new Trie();
            for (int i = 0; i < s.length(); i++) {
                String suffix = s.substring(i);
                trie.insertString(suffix, i);
            }
            return trie;
        }

        class Trie{
            TrieNode root = new TrieNode();

            public Trie() {
            }

            public Trie(String s) {
                insertString(s, 0);
            }

            public ArrayList<Integer> search(String s) {
                return root.search(s);
            }

            public void insertString(String str, int location) {
                root.insertString(str, location);
            }

            public TrieNode getRoot() {
                return root;
            }
        }

        class TrieNode{
            private HashMap<Character, TrieNode> children;
            private ArrayList<Integer> indexes;

            public TrieNode() {
                children = new HashMap<>();
                indexes = new ArrayList<>();
            }

            public void insertString(String s, int index) {
                if (s == null) {
                    return;
                }
                indexes.add(index);
                if (s.length() > 0) {
                    char value = s.charAt(0);
                    TrieNode child = null;
                    if (children.containsKey(value)) {
                        child = children.get(value);
                    }
                    else {
                        child = new TrieNode();
                        children.put(value, child);
                    }
                    String remainder = s.substring(1);
                    child.insertString(remainder, index + 1);
                }
                else {
                    children.put('\0', null);// 终止字符
                }

            }

            public ArrayList<Integer> search(String s) {
                if (s == null || s.length() == 0) {
                    return indexes;
                }
                else {
                    char first = s.charAt(0);
                    if (children.containsKey(first)) {
                        String remainder = s.substring(1);
                        return children.get(first).search(remainder);
                    }
                }
                return null;
            }

            public boolean terminates() {
                return children.containsKey('\0');
            }

            public TrieNode getChild(char c) {
                return children.get(c);
            }
        }
    }

    /**
     * <pre>
     * 思路3，将所有较小的字符串添加到 trie 中，例如，字符串 {i,is, pp, ms}
     *           ┌─┐
     *           │i│
     *           └─┘
     *        ┌───┼────┐
     *       ┌▼┐ ┌▼┐  ┌▼┐
     *       │i│ │p│  │m│
     *       └─┘ └─┘  └─┘
     *     ┌──┴─┐ └───┐└────┐
     *    ┌▼┐  ┌▼┐   ┌▼┐   ┌▼┐
     *    │s│  │*│   │p│   │s│
     *    └─┘  └─┘   └─┘   └─┘
     *  ┌──┘          └──┐  └───┐
     * ┌▼┐              ┌▼┐    ┌▼┐
     * │*│              │*│    │*│
     * └─┘              └─┘    └─┘
     * 时间复杂度：O(kt) 时间创建 trie，O(bk) 的时间搜索所有字符串
     * k是T中最长的长度，b是较大字符串的长度，t是字符串T中较小字符串的数量。
     * O(kt + bk)一定比 O(kbt)快。该算法时间复杂对优于算法2
     * </pre>
     */
    class Solution3 {

        class Trie{
            TrieNode root = new TrieNode();

            public Trie() {
            }

            public Trie(String s) {
                insertString(s, 0);
            }

            public ArrayList<Integer> search(String s) {
                return root.search(s);
            }

            public void insertString(String str, int location) {
                root.insertString(str, location);
            }

            public TrieNode getRoot() {
                return root;
            }
        }

        class TrieNode{
            private HashMap<Character, TrieNode> children;
            private ArrayList<Integer> indexes;

            public TrieNode() {
                children = new HashMap<>();
                indexes = new ArrayList<>();
            }

            public void insertString(String s, int index) {
                if (s == null) {
                    return;
                }
                indexes.add(index);
                if (s.length() > 0) {
                    char value = s.charAt(0);
                    TrieNode child = null;
                    if (children.containsKey(value)) {
                        child = children.get(value);
                    }
                    else {
                        child = new TrieNode();
                        children.put(value, child);
                    }
                    String remainder = s.substring(1);
                    child.insertString(remainder, index + 1);
                }
                else {
                    children.put('\0', null);// 终止字符
                }

            }

            public ArrayList<Integer> search(String s) {
                if (s == null || s.length() == 0) {
                    return indexes;
                }
                else {
                    char first = s.charAt(0);
                    if (children.containsKey(first)) {
                        String remainder = s.substring(1);
                        return children.get(first).search(remainder);
                    }
                }
                return null;
            }

            public boolean terminates() {
                return children.containsKey('\0');
            }

            public TrieNode getChild(char c) {
                return children.get(c);
            }
        }


        Map<String, List<Integer>> searchAll(String big, String [] smalls) {
            Map<String, List<Integer>> lookup = new HashMap<>();
            int maxLen = big.length();
            TrieNode root = createTreeFromString(smalls, maxLen).getRoot();
            for (int i = 0; i < big.length(); i++) {
                List<String> strings = findStringAtLoc(root,big, i);
                insertIntoHashMap(strings, lookup, i);
            }
            return lookup;
        }


        private void insertIntoHashMap(List<String> strings, Map<String, List<Integer>> lookup, int i) {
            for (String string : strings) {
                List<Integer> positions = lookup.getOrDefault(string, new ArrayList<>());
                positions.add(i);
                lookup.put(string, positions);
            }
        }

        /**
         * 以较大字符串中start位置为起始位置，在trie 中查找字符串
         */
        private List<String> findStringAtLoc(TrieNode root, String big, int start) {
            ArrayList<String> strings = new ArrayList<>();
            int index = start;
            while (index < big.length()) {
                root = root.getChild(big.charAt(index));
                if (root == null) {
                    break;
                }
                if (root.terminates()) {
                    strings.add(big.substring(start, index + 1));
                }
            }
            return strings;
        }


        /**
         * 将每个字符串插入到 trie 中（每个字符串均不超过 maxLen）
         */
        private Trie createTreeFromString(String[] smalls, int maxLen) {
            Trie trie = new Trie("");
            for (String s : smalls) {
                if (s.length() <= maxLen) {
                    trie.insertString(s, 0);
                }
            }
            return trie;
        }
    }

    class Solution4{

        private Node root = new Node();

        public int[][] multiSearch(String big, String[] smalls) {

            int n = smalls.length;
            // 初始化结果集
            List<Integer>[] res = new List[n];
            for (int i = 0; i < n; i++)
                res[i] = new ArrayList<>();
            // 建树
            for (int i = 0; i < smalls.length; i++)
                insert(smalls[i], i);

            for (int i = 0; i < big.length(); i++) {

                Node tmp = root;

                for (int j = i; j < big.length(); j++) {
                    //不存在以该串为prefix的敏感词
                    if (tmp.children[big.charAt(j) - 'a'] == null)
                        break;

                    tmp = tmp.children[big.charAt(j) - 'a'];

                    if (tmp.isWord)
                        res[tmp.id].add(i);
                }
            }
            // 返回二维数组
            int[][] ret = new int[n][];

            for (int i = 0; i < n; i++) {

                ret[i] = new int[res[i].size()];

                for (int j = 0; j < ret[i].length; j++)
                    ret[i][j] = res[i].get(j);
            }

            return ret;
        }

        private void insert(String word, int id) {

            Node tmp = root;

            for (int i = 0; i < word.length(); i++) {

                if (tmp.children[word.charAt(i) - 'a'] == null)
                    tmp.children[word.charAt(i) - 'a'] = new Node();

                tmp = tmp.children[word.charAt(i) - 'a'];
            }

            tmp.isWord = true;
            tmp.id = id;
        }

        class Node{

            Node[] children;
            boolean isWord;
            int id;

            public Node() {

                children = new Node[26];
                isWord = false;
                id = 0;
            }
        }
    }
}
