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

import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2022-07-31.
 */
public class _651_四键的键盘{
    /**
     * 这道题给了四个操作，分别是 【打印A】、【全选】、【复制】、【粘贴】。
     * 每个操作都算一个步骤，给了我们一个数字N，问N个操作最多能输出多个A。
     * <p>最优的序列应该是这种形式：A,A..C-A,C-C,C-V,C-V..C-A,C-C,C-V..。
     * <p>
     * 状态： 剩余敲击次数
     * 最优按键序列一定只有两种情况：
     * <li>一直按 A：A...A （当N比较小时）
     * <li>A..C-V..：A...A,C-V,C-V...C-V
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

    /**
     * 如何在 N 次敲击按钮后得到最多的 A？我们穷举呗，每次有对于每次按键，我们可以穷举四种可能，很明显就是一个动态规划问题。
     */
    class Solution0{
        public int maxA(int N) {
            // 可以按 N 次按键，屏幕和剪切板里都还没有 A
            return dp(N, 0, 0);
        }

        // 对于 (n, a_num, copy) 这个状态，
        // 屏幕上能最终最多能有 dp(n, a_num, copy) 个 A
        Map<String, Integer> memo = new HashMap<>();

        int dp(int n, int a_num, int copy) {
            // base case
            if (n <= 0) return a_num;
            String key = n + "," + a_num + "," + copy;
            // 如果状态已经计算过，则直接返回之前的结果
            if (memo.containsKey(key)) return memo.get(key);
            // 几种选择全试一遍，选择最大的结果
            int res = Integer.MIN_VALUE;
            res = Math.max(res, dp(n - 1, a_num + 1, copy)); // A
            res = Math.max(res, dp(n - 1, a_num + copy, copy)); // C-V
            res = Math.max(res, dp(n - 2, a_num, a_num)); // C-A C-C  ,C-A,C-C 一定是搭配使用
            // 把计算结果存入备忘录
            memo.put(key, res);
            return res;
        }
    }

    /**
     * <pre>
     *思路：
     * 这种思路稍微有点复杂，但是效率高。继续走流程，「选择」还是那 4 个，但是这次我们只定义一个「状态」，也就是剩余的敲击次数 n。
     * 这个算法基于这样一个事实，最优按键序列一定只有两种情况：
     * 1. 要么一直按 A：A,A,...A（当 N 比较小时）。
     * 2. 要么是这么一个形式：A,A,...C-A,C-C,C-V,C-V,...C-V（当 N 比较大时）。
     *
     * 因为字符数量少（N 比较小）时，C-A C-C C-V 这一套操作的代价相对比较高，可能不如一个个按 A；而当 N 比较大时，后期 C-V 的收获肯定很大。
     * 这种情况下整个操作序列大致是：开头连按几个 A，然后 C-A C-C 组合再接若干 C-V，然后再 C-A C-C 接着若干 C-V，循环下去。
     *
     * 换句话说，最后一次按键要么是 A 要么是 C-V。
     * {@code
     * int[] dp = new int[N + 1];
     * // 定义：dp[i] 表示 i 次操作后最多能显示多少个 A
     * for (int i = 0; i <= N; i++)
     *     dp[i] = max(
     *             这次按 A 键，
     *             这次按 C-V
     *         )
     * }
     *
     * 对于1. dp[i] = dp[i-1]+1
     * 对于2. 如果要按 C-V，还要考虑之前是在哪里 C-A C-C 的。
     * 刚才说了，最优的操作序列一定是 C-A C-C 接着若干 C-V，所以我们用一个变量 j 作为若干 C-V 的起点。
     * 那么 j 之前的 2 个操作就应该是 C-A C-C 了
     *
     *
     *          j-2          j           i
     *
     *      ...  3   4   5   6   7   8   9
     *     ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
     *  dp │   │ 3 │   │   │   │   │   │   │   │   │   │   │
     *     └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
     *              C-A C-C C-V C-V C-V C-V
     *  这样，此算法就完成了，时间复杂度 O(N^2)，空间复杂度 O(N)，这种解法应该是比较高效的了
     * </pre>
     */
    class Solution2{
        public int maxA(int N) {
            int[] dp = new int[N + 1];
            dp[0] = 0;
            for (int i = 1; i <= N; i++) {
                // 按 A 键
                dp[i] = dp[i - 1] + 1;
                for (int j = 2; j < i; j++) {
                    // 全选 & 复制 dp[i-2]， 连续复制 i-j 次,加上 dp[i-2]本身
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
