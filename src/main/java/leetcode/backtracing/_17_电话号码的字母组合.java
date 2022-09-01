package leetcode.backtracing;
//给定一个仅包含数字2-9的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
//
//给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。

import java.util.ArrayList;
import java.util.List;
//
//示例 1：
//
//输入：digits = "23"
//输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
//示例 2：
//
//输入：digits = ""
//输出：[]
//示例 3：
//
//输入：digits = "2"
//输出：["a","b","c"]
//
//提示：
//
//0 <= digits.length <= 4
//digits[i] 是范围 ['2', '9'] 的一个数字。

public class _17_电话号码的字母组合 {
    class Solution {
        // 组合的问题本质上就是遍历一颗回溯树，套用回溯算法框架即可
        // keyBoard[i] 表示数字 i对应的字母
        String[] keyBoard = new String[] {
                "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs","tuv", "wxyz"
        };
        List<String> ans = new ArrayList<>();
        public List<String> letterCombinations(String digits) {
            if (digits == null || digits.length() == 0) {
                return ans;
            }
            backtrace(digits.toCharArray(), 0, new StringBuilder());
            return ans;
        }
        public void backtrace(char[] digits, int start, StringBuilder sb) {
            if (sb.length() == digits.length) {
                // 到达回溯树底部
                ans.add(new String(sb));
            }
            //回溯算法框架
            for (int i = start; i < digits.length; i++){
                char[] keys = keyBoard[digits[i] - '0'].toCharArray();
                for (int j = 0; j<keys.length; j++) {
                    // 做选择
                    sb.append(keys[j]);
                    backtrace(digits, i+1, sb);
                    // 撤销选择
                    sb.deleteCharAt(sb.length()-1);
                }
            }
        }
    }
}
