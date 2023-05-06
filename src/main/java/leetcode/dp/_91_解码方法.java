package leetcode.dp;
//一条包含字母A-Z 的消息通过以下映射进行了 编码 ：
//
//'A' -> "1"
//'B' -> "2"
//...
//'Z' -> "26"
//要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：
//
//"AAJF" ，将消息分组为 (1 1 10 6)
//"KJF" ，将消息分组为 (11 10 6)
//注意，消息不能分组为 (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
//
//给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
//
//题目数据保证答案肯定是一个 32 位 的整数。
//
//
//
//示例 1：
//
//输入：s = "12"
//输出：2
//解释：它可以解码为 "AB"（1 2）或者 "L"（12）。
//示例 2：
//
//输入：s = "226"
//输出：3
//解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
//示例 3：
//
//输入：s = "0"
//输出：0
//解释：没有字符映射到以 0 开头的数字。
//含有 0 的有效映射是 'J' -> "10" 和 'T'-> "20" 。
//由于没有字符，因此没有有效的方法对此进行解码，因为所有数字都需要映射。
//
//
//提示：
//
//1 <= s.length <= 100
//s 只包含数字，并且可能包含前导零
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/decode-ways
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


public class _91_解码方法 {
    class Solution {
        /**
         * <pre>
         * 状态转移关系
         * 1. s[i] 本身可能表示一个字母，这种情况下解码数量为 numDecodings(s[0,..., i-1])
         * 2. s[i] 可能和 s[i-1]结合起来表示一个字母，这种情况下解码数量为 numDecodeings(s[0,...,i-2])
         * 状态转移方程为
         * numDecodeing(s) = numDecoding(s[0,...,n-1]) + numDecoding(s[0,..., n-2])
         * </pre>
         */
        public int numDecodings(String s) {
            int n = s.length();
            if (n < 1) {
                return 0;
            }
            //定义 dp[i] 表示 s[0..i-1]的解码数量,即长度为i的前缀最多解码个数
            int[] dp = new int[n + 1];
            // base case
            dp[0] = 1;
            dp[1] = s.charAt(0) == '0' ? 0 : 1;
            // 注意dp数组和s之间的索引偏移一位
            for (int i = 2; i <= n; i++) {
                char c = s.charAt(i - 1), d = s.charAt(i - 2);
                if ('1' <= c && c <= '9') {
                    // 1. s[i] 本身 可以作为一个字母
                    dp[i] += dp[i - 1];
                }
                if (d == '1' || (d == '2' && c <= '6')) {
                    // 2. s[i] 和 s[i-1]结合起来表示一个字母
                    dp[i] += dp[i - 2];
                }
            }
            return dp[n];
        }
    }
}
