package leetcode.dp;
//给定一个整数 n，计算所有小于等于 n 的非负整数中数字 1 出现的个数。
//
//
//
// 示例 1：
//
//
//输入：n = 13
//输出：6
//
//
// 示例 2：
//
//
//输入：n = 0
//输出：0
//
//
//
//
// 提示：
//
//
// 0 <= n <= 10⁹
//
//
// Related Topics 递归 数学 动态规划 👍 458 👎 0

/**
 * 时间复杂度 O(logn) n包含的数位个数与n成对数关系
 * 空间复杂度 O(1)
 * @author mayanwei
 * @date 2022-09-20.
 */
public class _233_数字1的个数{
    /**
     * 思路：
     * n/10^(k+1) * 10 ^ k + min(max(n % 10^(k+1) - 10 ^ k , 0), 10 ^ k)
     */
    class Solution{
        public int countDigitOne(int n) {
            // mulk 表示 10^k
            // 在下面的代码中，可以发现 k 并没有被直接使用到（都是使用 10^k）
            // 但为了让代码看起来更加直观，这里保留了 k
            long mulk = 1;
            int ans = 0;
            for (int k = 0; n >= mulk; ++k) {
                ans += (n / (mulk * 10)) * mulk + Math.min(Math.max(n % (mulk * 10) - mulk + 1, 0), mulk);
                mulk *= 10;
            }
            return ans;
        }
    }
}
