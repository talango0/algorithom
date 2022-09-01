package leetcode.string;
//49. 字母异位词分组
//给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
//
//字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。
//
//
//
//示例 1:
//
//输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
//输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
//示例 2:
//
//输入: strs = [""]
//输出: [[""]]
//示例 3:
//
//输入: strs = ["a"]
//输出: [["a"]]
//
//
//提示：
//
//1 <= strs.length <= 104
//0 <= strs[i].length <= 100
//strs[i] 仅包含小写字母

import java.util.*;

public class _49_字母异位词分组 {
    // Hash 应用
    class Solution {
        public List<List<String>> groupAnagrams(String[] strs) {
            // 编码到分组的映射
            Map<String, List<String>> codeToGroup = new HashMap<>();
            for (String s:strs) {
                // 对字符串进行编码
                String code = encode(s);

                // 把编码相同的字符串放在一起
                codeToGroup.putIfAbsent(code, new LinkedList<>());
                codeToGroup.get(code).add(s);
            }
            List<List<String>> ans = new ArrayList<>(codeToGroup.size());
            for (List<String> group:codeToGroup.values()) {
                ans.add(group);
            }
            return ans;
        }

        // 使得字母异位词的编码相同，利用
        String encode(String s) {
            char [] count = new char[26];
            for (char c:s.toCharArray()) {
                int delta = c-'a';
                count[delta]++;
            }
            return new String(count);
        }
    }
}
