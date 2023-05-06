package leetcode.dp;
//Imagine you have a special keyboard with the following keys:
//
//Key 1: (A): Print one 'A' on screen.
//
//Key 2: (Ctrl-A): Select the whole screen.
//
//Key 3: (Ctrl-C): Copy selection to buffer.
//
//Key 4: (Ctrl-V): Print buffer on screen appending it after what has already been printed.
//
//Now, you can only press the keyboard for N times (with the above four keys),
// find out the maximum numbers of 'A' you can print on screen.
//
//Example 1:
//
//Input: N = 3
//Output: 3
//Explanation:
//We can at most get 3 A's on screen by pressing following key sequence:
//A, A, A
//
//
//Example 2:
//
//Input: N = 7
//Output: 9
//Explanation:
//We can at most get 9 A's on screen by pressing following key sequence:
//A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
//
//
//Note:
//
//1 <= N <= 50
//Answers will be in the range of 32-bit signed integer.

import org.junit.Assert;
import org.junit.Test;

/**
 * @author mayanwei
 * @date 2022-07-31.
 */
public class _651_四键的键盘{
    /**
     * 这道题给了四个操作，分别是 【打印A】、【全选】、【复制】、【粘贴】。
     * 每个操作都算一个步骤，给了我们一个数字N，问N个操作最多能输出多个A。
     * //最优的序列应该是这种形式：A,A..C-A,C-C,C-V,C-V..C-A,C-C,C-V..。
     * 状态： 剩余敲击次数
     * 最优按键序列一定只有两种情况：
     * <li>一直按 A：A...A （当N比较小时）</li>
     * <li>A..C-V..：A...A,C-V,C-V...C-v</li>
     * 换句话说，最后一次按键要么是A，要么是C-V
     */

    class Solution{
        public int maxA(int n) {
            int res = n;
            for (int i = 1; i < n - 2; i++) {
                res = Math.max(res, maxA(i) * (n - i - 1));
            }
            return res;
        }
    }

    class Solution2{
        public int maxA(int N) {
            int[] dp = new int[N + 1];
            dp[0] = 0;
            for (int i = 1; i <= N; i++) {
                // 按 A 键
                dp[i] = dp[i - 1] + 1;
                for (int j = 2; j < i; j++) {
                    // 全选 & 复制 dp[i-2]， 连续复制 i-j 次
                    // 屏幕上共有 dp[j-2] * (i-j+1) 个 A
                    dp[i] = Math.max(dp[i], dp[j - 2] * (i - j + 1));
                }
            }
            return dp[N];
        }
    }

    @Test
    public void main() {
        Solution solution = new Solution();
        Solution2 solution2 = new Solution2();
        Assert.assertEquals(solution2.maxA(5), solution.maxA(5));
    }
}
