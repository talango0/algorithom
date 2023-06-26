package leetcode.程序员面试金典;
//给定一份单词的清单，设计一个算法，创建由字母组成的面积最大的矩形，其中每一行组成一个单词(自左向右)，每一列也组成一个单词(自上而下)。
// 不要求这些单词在清单里连续出现，但要求所有行等长，所有列等高。
//
//如果有多个面积最大的矩形，输出任意一个均可。一个单词可以重复使用。
//
//示例 1:
//
//输入: ["this", "real", "hard", "trh", "hea", "iar", "sld"]
//输出:
//[
//  "this",
//  "real",
//  "hard"
//]
//示例 2:
//
//输入: ["aa"]
//输出: ["aa","aa"]
//说明：
//
//words.length <= 1000
//words[i].length <= 100
//数据保证单词足够随机
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/word-rectangle-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.sql.Array;
import java.util.*;

/**
 * @author mayanwei
 * @date 2023-06-25.
 */
public class _17_25_单词矩阵{
    class Solution{
        class Trie{
            Trie[] children;
            boolean isLeaf;

            public Trie() {
                this.children = new Trie[26];
            }
        }

        Trie root;
        Map<Integer, Set<String>> map; // 把清单根据单词长度分组
        int maxArea;
        int maxLength;
        List<String> ans; // 最后转换成数组输出

        public String[] maxRectangle(String[] words) {
            root = new Trie(); // 构建字典树
            for (String word : words) {
                Trie node = root;
                for (char c : word.toCharArray()) {
                    if (node.children[c - 'a'] == null) {
                        node.children[c - 'a'] = new Trie();
                    }
                    node = node.children[c - 'a'];
                }
                node.isLeaf = true;
            }

            map = new HashMap<>();
            ans = new ArrayList<>();
            maxArea = 0;
            maxLength = 0;
            for (String w : words) {
                int len = w.length();
                maxLength = Math.max(maxLength, len);
                Set<String> set = map.getOrDefault(len, new HashSet<>());
                set.add(w);
                map.put(len, set);
            }

            List<String> path = new ArrayList<>();
            for (Integer key : map.keySet()) {
                path.clear();
                // 回溯需要的参数是：相同长度的单词的集合，存放路径的列表，当前单词的长度。
                dfs(map.get(key), path, key);
            }
            String [] ultimate = new String[ans.size()];
            return ans.toArray(ultimate);

        }

        // 回溯的套路
        private void dfs(Set<String> dic, List<String> path, Integer wordLen) {
            // 剪枝，dic 里的情况不可能得到最优解，提前过滤掉不考虑
            if (wordLen * maxLength <= maxArea) {
                return;
            }

            // 终止条件，如果 path 矩阵的高度已经超过了清单中最长单词长度，结束
            if (path.size() > maxLength) {
                return;
            }

            for (String s : dic) {
                // 做选择
                path.add(s);

                boolean [] res = isValid(path);
                if (res[0]) { // 下面还可以在添加
                    int area = path.size() * path.get(0).length();
                    if (res[1] && (area > maxArea)) { //  每列都是单词的矩阵
                        maxArea = area;
                        //ans = path; //  注意，这里不能直接把 path 引用交给ans
                        ans = new ArrayList<>(path);
                    }
                    dfs (dic, path, wordLen);
                }

                // 插销选择
                path.remove(path.size() - 1);
            }

        }

        /**
         * <pre>
         * 判断一个矩阵是否每一列形成的单词都在清单里。
         * 存在两种情况：
         * 1. 有的列中字母不再字典树中，即这一列不能构成单词，整个举证不符合要求。
         * 2. 每列的所有字母在字典树中但有的结尾不是Leaf，也就是有的列目前还不是单词，所以需要一个 boolean 数组 res[] 来存放结果。
         * res[0]表示是否有字母不再字典树中，true；false 有不在的。
         * res[1] 表示是否所有列都构成了清单里的单词。
         *</pre>
         * @param input
         * @return
         */
        private boolean[] isValid(List<String> input) {
            boolean[] res = new boolean[2];
            boolean allLeaf = true;
            int m = input.size();
            int n = input.get(0).length();
            for (int j = 0; j < n; j++) {
                // 接下来看单词是否在字典树
                Trie node = root;
                for (int i = 0; i < m; i++) {
                    int c = input.get(i).charAt(j) - 'a';
                    if (node.children[c] == null) {
                        return new boolean[]{false, false};
                    }
                    node = node.children[c];
                }
                if (!node.isLeaf) {
                    allLeaf = false;
                }
            }
            return new boolean[]{true, allLeaf};
        }
    }
}
