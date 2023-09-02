package leetcode.dp;
//最初记事本上只有一个字符 'A' 。你每次可以对这个记事本进行两种操作：
//
//Copy All（复制全部）：复制这个记事本中的所有字符（不允许仅复制部分字符）。
//Paste（粘贴）：粘贴 上一次 复制的字符。
//给你一个数字 n ，你需要使用最少的操作次数，在记事本上输出 恰好 n 个 'A' 。
// 返回能够打印出 n 个 'A' 的最少操作次数。
//
//示例 1：
//输入：3
//输出：3
//解释：
//最初, 只有一个字符 'A'。
//第 1 步, 使用 Copy All 操作。
//第 2 步, 使用 Paste 操作来获得 'AA'。
//第 3 步, 使用 Paste 操作来获得 'AAA'。
//
//示例 2：
//输入：n = 1
//输出：0
//提示：
//
//1 <= n <= 1000
//Related Topics
//数学
//动态规划
//
//👍 474
//👎 0

/**
 * @author mayanwei
 * @date 2022-07-31.
 */
public class _650_只有两个键的键盘{
    /**
     * dp[i] 表示打印i个A需要的最少操作次数。
     * <p>
     * 只允许两种操作，复制+粘贴。
     * 想要得到i个A，则首先需要j个A，然后指向一次【复制】操作，
     * 再用若干次【粘贴】操作得到i个A。
     * <p>
     * 因此 j 必须是 i 的因数，可以枚举j进行状态转移，状态转移
     * 方程如下：
     * <p>
     * f[i] = min{f[i], i/j}, j|i ，其中j|i表示i能够被j整除
     */
    class Solution1{
        public int minSteps(int n) {
            int dp[] = new int[n + 1];
            for (int i = 2; i <= n; ++i) {
                dp[i] = i;
                for (int j = i - 1; j > 1; --j) {
                    if (i % j == 0) {
                        dp[i] = Math.min(dp[i], dp[j] + i / j);
                        //用 dp[j] + i/j 来更新 dp[i]，然后直接 break
                        // 掉j的循环，之后的j就不用再考虑了，因为因数越大，其需要的按键数就越少
                        break;
                    }
                }
            }
            return dp[n];
        }
    }

    class Solution2{
        public int minSteps(int n) {
            if (n == 1) {
                return 0;
            }
            int res = n;
            for (int i = n - 1; i > 1; i--) {
                if (n % i == 0) {
                    res = Math.min(res, minSteps(n / i) + i);
                }
            }
            return res;
        }
    }

    class Solution3{
        int minSteps(int n) {
            int res = 0;
            for (int i = 2; i <= n; ++i) {
                while (n % i == 0) {
                    res += i;
                    n /= i;
                }
            }
            return res;
        }
    }

}
