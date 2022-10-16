package leetcode.graph;
//现有一种使用字母的全新语言，这门语言的字母顺序与英语顺序不同。
//
//假设，您并不知道其中字母之间的先后顺序。
// 但是，会收到词典中获得一个 不为空的 单词列表。
// 因为是从词典中获得的，所以该单词列表内的单词已经 按这门新语言的字母顺序进行了排序。
//
//您需要根据这个输入的列表，还原出此语言中已知的字母顺序。
//示例 1：
//输入:
//[
//  "wrt",
//  "wrf",
//  "er",
//  "ett",
//  "rftt"
//]
//输出: "wertf"
//
//示例 2：
//输入:
//[
//  "z",
//  "x"
//]
//输出: "zx"
//
//示例 3：
//输入:
//[
//  "z",
//  "x",
//  "z"
//]
//输出: ""
//解释: 此顺序是非法的，因此返回 ""。
//
//提示：
//你可以默认输入的全部都是小写字母
//若给定的顺序是不合法的，则返回空字符串即可
//若存在多种可能的合法字母顺序，请返回其中任意一种顺序即可

import leetcode.string._953_验证外星语词典;

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-10-16.
 * @see _953_验证外星语词典
 */
public class _269_火星词典{
    /**
     * 题目的意思：外星文字典中的字母和字母顺序可以看成有向图，字典顺序即为所有字母中的一种排列，满足每一条有向边的起点字母和终点字母的顺序
     * 和这两个字母在排序中的顺序相同，该排列即为有向图的拓扑排序。
     * 只有当有向图中无环时，才有拓扑排序，且拓扑排序可能不止一种，如果有向图中有还，则环内的字母不存在符合要求的排列，因此没有拓扑排序。
     * 使用拓扑排序求解是，将外星文字典中的每个字母看成一个节点，将字母之间的顺序关系看成有向边。对于外星文字典中的两个相邻单词，同时从左到右遍历，当遇到第一个不相同的字母是，该位置的两个字母之间既存在顺序关系。
     * 以下两种情况不存在合法的顺序：
     * 1. 字母之间的顺序关系存在由至少2个字母组成的环，例如 words = [a,b,a]
     * 2. 相邻两个单词满足后面的单词是前面单词的前缀，且后面的单词的长度小于前面的单词的长度，例如words = [ab, a]
     * 其余情况下都存在合法字母顺序，可以使用拓扑排序得到字典顺序。
     * 拓扑排序可以采用深度优先或广度优先搜索实现。
     * <p>
     * [
     * "wrt",
     * "wrf",
     * "er",
     * "ett",
     * "rftt"]
     * <p>
     * t -> f
     * w -> e
     * r -> t
     * e -> r
     * <p>
     * w -> e -> r > t -> f
     * wertf
     */
    class Solution{
        private Map<Character, List<Character>> edges = new HashMap<>();
        Map<Character, Boolean> states = new HashMap<>();
        boolean valid = true;
        char[] order;
        int index;

        /**
         * 拓扑排序 dfs
         * 时间复杂度：O(n*L + |𝛴|)
         * n 数组word的长度
         * L 平均单词长度
         * 𝛴 字典中的字母集合
         * 需要O(n*L)时间构造有向图。
         * 空间负载度：O(n+|𝛴|)
         */
        public String alienOrder(String[] words) {
            int length = words.length;
            for (String word : words) {
                int wordLen = word.length();
                for (int j = 0; j < wordLen; j++) {
                    char c = word.charAt(j);
                    edges.putIfAbsent(c, new ArrayList<Character>());
                }
            }
            for (int i = 1; i < length && valid; i++) {
                addEdge(words[i - 1], words[i]);
            }
            if (!valid) {
                return "";
            }
            order = new char[edges.size()];
            index = edges.size() - 1;
            Set<Character> letterSet = edges.keySet();
            for (char u : letterSet) {
                if (!states.containsKey(u)) {
                    dfs(u);
                }
            }
            if (!valid) {
                return "";
            }
            return new String(order);
        }

        private void dfs(char u) {
            states.put(u, true);
            List<Character> adjacent = edges.get(u);
            for (char v : adjacent) {
                if (!states.containsKey(v)) {
                    dfs(v);
                    if (!valid) {
                        return;
                    }
                }
                else if (states.get(v)) {
                    valid = false;
                    return;
                }
            }
            states.put(u, false);
            order[index] = u;
            index--;

        }

        private void addEdge(String before, String after) {
            int length1 = before.length(), length2 = after.length();
            int length = Math.min(length1, length2);
            int index = 0;
            while (index < length) {
                char c1 = before.charAt(index), c2 = after.charAt(index);
                if (c1 != c2) {
                    edges.get(c1).add(c2);
                    break;
                }
                index++;
            }
            if (index == length && length1 > length2) {
                valid = false;
            }
        }
    }

    class Solution2{
        private Map<Character, List<Character>> edges = new HashMap<>();
        Map<Character, Integer> indegrees = new HashMap<>();
        boolean valid = true;
        StringBuffer order = new StringBuffer();

        /**
         * 拓扑排序 bfs
         */
        public String alienOrder(String[] words) {
            int length = words.length;
            for (String word : words) {
                int wordLen = word.length();
                for (int j = 0; j < wordLen; j++) {
                    char c = word.charAt(j);
                    edges.putIfAbsent(c, new ArrayList<Character>());
                }
            }
            for (int i = 1; i < length && valid; i++) {
                addEdge(words[i - 1], words[i]);
            }
            if (!valid) {
                return "";
            }
            Queue<Character> queue = new LinkedList<>();
            Set<Character> letterSet = edges.keySet();
            for (char u : letterSet) {
                if (!indegrees.containsKey(u)) {
                    queue.offer(u);
                }
            }
            while (!queue.isEmpty()) {
                char u = queue.poll();
                order.append(u);
                List<Character> adjacent = edges.get(u);
                for (char v : adjacent) {
                    indegrees.put(v, indegrees.get(v) - 1);
                    if (indegrees.get(v) == 0) {
                        queue.offer(v);
                    }
                }
            }
            return order.length() == edges.size() ? order.toString() :"";
        }

        private void addEdge(String before, String after) {
            int length1 = before.length(), length2 = after.length();
            int length = Math.min(length1, length2);
            int index = 0;
            while (index < length) {
                char c1 = before.charAt(index), c2 = after.charAt(index);
                if (c1 != c2) {
                    edges.get(c1).add(c2);
                    indegrees.put(c2, indegrees.getOrDefault(c2, 0) + 1);
                    break;
                }
                index++;
            }
            if (index == length && length1 > length2) {
                valid = false;
            }
        }
    }
}
