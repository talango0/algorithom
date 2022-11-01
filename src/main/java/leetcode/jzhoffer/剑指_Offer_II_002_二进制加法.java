package leetcode.jzhoffer;

import leetcode.math._67_二进制求和;
//给定两个 01 字符串a和b，请计算它们的和，并以二进制字符串的形式输出。
//
//输入为 非空 字符串且只包含数字1和0。
//
//
//
//示例1:
//
//输入: a = "11", b = "10"
//输出: "101"
//示例2:
//
//输入: a = "1010", b = "1011"
//输出: "10101"
//
//
//提示：
//
//每个字符串仅由字符 '0' 或 '1' 组成。
//1 <= a.length, b.length <= 10^4
//字符串如果不是 "0" ，就都不含前导零。
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/JFETK5
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2022-10-29.
 * @see _67_二进制求和
 */
public class 剑指_Offer_II_002_二进制加法{
    class Solution {
        public String addBinary(String a, String b) {
            StringBuffer ans = new StringBuffer();

            int n = Math.max(a.length(), b.length()), carry = 0;
            for (int i = 0; i < n; ++i) {
                carry += i < a.length() ? (a.charAt(a.length() - 1 - i) - '0') : 0;
                carry += i < b.length() ? (b.charAt(b.length() - 1 - i) - '0') : 0;
                ans.append((char) (carry % 2 + '0'));
                carry /= 2;
            }

            if (carry > 0) {
                ans.append('1');
            }
            ans.reverse();

            return ans.toString();
        }
    }
}
