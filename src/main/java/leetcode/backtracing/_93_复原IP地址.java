package leetcode.backtracing;

import leetcode.jzhoffer.剑指_Offer_II_086_分割回文子字符串;

import java.util.ArrayList;
import java.util.List;
//有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
//
//
// 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312"
//和 "192.168@1.1" 是 无效 IP 地址。
//
//
// 给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能的有效 IP 地址，这些地址可以通过在 s 中插入 '.' 来形成。你 不能 重新
//排序或删除 s 中的任何数字。你可以按 任何 顺序返回答案。
//
//
//
// 示例 1：
//
//
//输入：s = "25525511135"
//输出：["255.255.11.135","255.255.111.35"]
//
//
// 示例 2：
//
//
//输入：s = "0000"
//输出：["0.0.0.0"]
//
//
// 示例 3：
//
//
//输入：s = "101023"
//输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 20
// s 仅由数字组成
//
//
// Related Topics 字符串 回溯 👍 1063 👎 0

/**
 * @author mayanwei
 * @date 2022-11-04.
 * @see 剑指_Offer_II_086_分割回文子字符串
 */
public class _93_复原IP地址{
    class Solution{
        List<String> temp = new ArrayList<>();
        List<String> list = new ArrayList<>();

        public List<String> restoreIpAddresses(String s) {
            backtrack(s, 0);
            return list;
        }

        private void backtrack(String s, int begin) {
            if (temp.size() == 4 && begin != s.length()) {
                return;
            }
            if (temp.size() == 4 && begin == s.length()) {
                //这个join函数用时2ms，手动StringBuilder的话就是1ms
                list.add(String.join(".", temp));
                return;
            }
            for (int i = begin; i < s.length() && i < begin + 3; i++) {
                String sub = s.substring(begin, i + 1);
                if (Integer.parseInt(sub) <= 255) {
                    if (sub.length() > 1 && s.charAt(begin) == '0') {
                        return;
                    }
                    temp.add(sub);
                    backtrack(s, i + 1);
                    temp.remove(temp.size() - 1);
                }
                else {
                    return;
                }
            }
        }

    }


    class Solution2 {
        int          SEGMENTS = 4;
        List<String> result   = new ArrayList<>();
        String[]     ipAttr   = new String[4];

        public List<String> restoreIpAddresses(String s) {
            if (s.length() < SEGMENTS || s.length() > 3 * SEGMENTS) {
                return new ArrayList<>();
            }
            dfs(0, 0, s, s.length());
            return result;
        }

        private void dfs(int start, int segCount, String s, int strSize) {
            if (segCount == SEGMENTS) {
                if (start == strSize) {
                    result.add(convert(ipAttr));
                    return;
                }
            } else {
                boolean flag = true;
                for (int end = start; end < strSize && flag; end++) {
                    if (valid(start, end, s)) {
                        ipAttr[segCount] = s.substring(start, end + 1);
                        dfs(end + 1, segCount + 1, s, strSize);
                    } else {
                        flag = false;
                    }
                }
            }
        }

        private boolean valid(int start, int end, String s) {
            int length = end - start + 1;
            if (length > 3 || length < 1) {
                return false;
            }
            if (length > 1 && s.charAt(start) == '0') {
                return false;
            }
            return Integer.parseInt(s.substring(start, end + 1)) <= 255;
        }

        private String convert(String[] ipAttr) {
            StringBuilder builder = new StringBuilder();
            builder.append(ipAttr[0]);
            for (int i = 1; i < SEGMENTS; i++) {
                builder.append(".");
                builder.append(ipAttr[i]);
            }
            return builder.toString();
        }
    }
}
