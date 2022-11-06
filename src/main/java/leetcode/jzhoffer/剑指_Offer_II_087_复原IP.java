package leetcode.jzhoffer;
//给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能从 s 获得的 有效 IP 地址 。你可以按任何顺序返回答案。
//
// 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
//
// 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312"
//和 "192.168@1.1" 是 无效 IP 地址。
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
//输入：s = "1111"
//输出：["1.1.1.1"]
//
//
// 示例 4：
//
//
//输入：s = "010010"
//输出：["0.10.0.10","0.100.1.0"]
//
//
// 示例 5：
//
//
//输入：s = "10203040"
//输出：["10.20.30.40","102.0.30.40","10.203.0.40"]
//
//
//
//
// 提示：
//
//
// 0 <= s.length <= 3000
// s 仅由数字组成
//
//
//
//
//
// 注意：本题与主站 93 题相同：https://leetcode-cn.com/problems/restore-ip-addresses/
//
// Related Topics 字符串 回溯 👍 42 👎 0

import leetcode.backtracing._93_复原IP地址;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-11-04.
 * @see _93_复原IP地址
 */
public class 剑指_Offer_II_087_复原IP{
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
}
