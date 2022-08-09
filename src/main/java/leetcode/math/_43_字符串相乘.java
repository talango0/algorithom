package leetcode.math;
//给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
//
// 注意：不能使用任何内置的 BigInteger 库或直接将输入转换为整数。
//
//
//
// 示例 1:
//
//
//输入: num1 = "2", num2 = "3"
//输出: "6"
//
// 示例 2:
//
//
//输入: num1 = "123", num2 = "456"
//输出: "56088"
//
//
//
// 提示：
//
//
// 1 <= num1.length, num2.length <= 200
// num1 和 num2 只能由数字组成。
// num1 和 num2 都不包含任何前导零，除了数字0本身。
//
//
// Related Topics 数学 字符串 模拟 👍 1008 👎 0
/**
 * @author mayanwei
 * @date 2022-08-07.
 */
public class _43_字符串相乘{
    class Solution {
        public String multiply(String num1, String num2) {
            int m = num1.length(), n = num2.length();
            // 结果最多为m+n 为
            int[] res = new int[m+n];
            // 从个位数开始逐位相乘
            for (int i = m-1; i >= 0; i--) {
                for (int j = n-1; j >= 0; j--) {
                    int mul = (num1.charAt(i) - '0') * (num2.charAt(j)-'0');
                    // 乘积在 res 对应的索引位置
                    int p1 = i+j;
                    int p2 = i+j+1;
                    // 叠加到 res 上
                    int sum = mul + res[p2];
                    res[p2] = sum%10;
                    res[p1] += sum/10;
                }
            }
            // 结果前缀可能存的 0 （未使用的位）
            int i = 0;
            while (i < res.length && res[i] == 0) {
                i++;
            }
            // 将计算结果转化成字符串
            StringBuilder sb = new StringBuilder();
            for (;i<res.length; i++) {
                sb.append(res[i]);
            }
            return sb.length() == 0 ? "0" : sb.toString();


        }
    }
}
