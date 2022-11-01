package leetcode.math;

//给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。
//
//
//
// 示例 1：
//
//
//输入:a = "11", b = "1"
//输出："100"
//
// 示例 2：
//
//
//输入：a = "1010", b = "1011"
//输出："10101"
//
//
//
// 提示：
//
//
// 1 <= a.length, b.length <= 10⁴
// a 和 b 仅由字符 '0' 或 '1' 组成
// 字符串如果不是 "0" ，就不含前导零
//
//
// Related Topics 位运算 数学 字符串 模拟 👍 906 👎 0

/**
 * @author mayanwei
 * @date 2022-10-29.
 */
public class _67_二进制求和{
    class Solution {
        public String addBinary(String a, String b) {
            int m = a.length(), n = b.length();
            StringBuilder sb = new StringBuilder();
            int l = Math.max(m,n);
            int carry = 0;
            for (int i = 0; i < l; i++){
                carry += i < m ? (a.charAt(m - 1 - i) - '0') : 0;
                carry += i < n ? (b.charAt(n - 1 - i) - '0') : 0;
                sb.append((char) (carry % 2 + '0'));
                carry /= 2;
            }
            if (carry > 0) {
                sb.append('1');
            }
            sb.reverse();
            return sb.toString();
        }
    }
}
