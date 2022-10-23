package leetcode.jzhoffer;
//给定一个字符串数组 strs ，将 变位词 组合在一起。 可以按任意顺序返回结果列表。
//
// 注意：若两个字符串中每个字符出现的次数都相同，则称它们互为变位词。
//
//
//
// 示例 1:
//
//
//输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
//输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
//
// 示例 2:
//
//
//输入: strs = [""]
//输出: [[""]]
//
//
// 示例 3:
//
//
//输入: strs = ["a"]
//输出: [["a"]]
//
//
//
// 提示：
//
//
// 1 <= strs.length <= 10⁴
// 0 <= strs[i].length <= 100
// strs[i] 仅包含小写字母
//
//
//
//
//
// 注意：本题与主站 49 题相同： https://leetcode-cn.com/problems/group-anagrams/
//
// Related Topics 数组 哈希表 字符串 排序 👍 40 👎 0

import leetcode.string._49_字母异位词分组;

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-10-23.
 * @see _49_字母异位词分组
 */
public class 剑指_Offer_II_033_变位词组{
    // Hash 应用
    class Solution{
        public List<List<String>> groupAnagrams(String[] strs) {
            // 编码到分组的映射
            Map<String, List<String>> codeToGroup = new HashMap<>();
            for (String s : strs) {
                // 对字符串进行编码
                String code = encode(s);

                // 把编码相同的字符串放在一起
                codeToGroup.putIfAbsent(code, new LinkedList<>());
                codeToGroup.get(code).add(s);
            }
            List<List<String>> ans = new ArrayList<>(codeToGroup.size());
            for (List<String> group : codeToGroup.values()) {
                ans.add(group);
            }
            return ans;
        }

        // 使得字母异位词的编码相同，利用
        String encode(String s) {
            char[] count = new char[26];
            for (char c : s.toCharArray()) {
                int delta = c - 'a';
                count[delta]++;
            }
            return new String(count);
        }
    }
}
