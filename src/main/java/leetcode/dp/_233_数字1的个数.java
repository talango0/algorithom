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
 *
 * @author mayanwei
 * @date 2022-09-20.
 */
public class _233_数字1的个数{
    /**
     * 思路：
     * n/10^(k+1) * 10 ^ k + min(max(n % 10^(k+1) - 10 ^ k + 1 , 0), 10 ^ k)
     * <p>
     * 以 n = 1234567 为例
     * 需要统计百位上的1出现的次数。
     * 从 0开始每 1000 个数，百位上的数字1都会出现100次，即数最后三位每1000个数都呈现  [000, 999] 的循环，
     * 其中的 [100, 199] 在百位上位1，共有100个。
     * n 拥有1234个这样的循环，每个循环[百位]上都出现了100次1，这样就一共出现了1234 * 100 次1。 (n/1000) * 100
     * <p>
     * 在剩余不在完整的循环中的部分，最后三位为 [000, 567]，其中 567 = n mod 1000 表示。记n' = n mod 1000,
     * 这一部分在百位上出现1 的次数可以通过分类讨论得出
     * 当 n' < 100 时，百位上不会出现0
     * 当 100 <= n' < 200 时， 百位上出现1的范围为 [100, n']，所以一共出现了 n' - 100 + 1次 1；
     * 当 n' >= 200 时，百位上出现了全部 100 次1。
     * 可以发现，
     * 当 n' < 100 时， n'-100+1值小于等于0，而我们希望得到的答案是 0；
     * n' >= 200 时， n'-100+1 的值大于100，而我们希望得到100， 因此我们可以总结归纳出这一部分在百位上1的出现次数为
     * <p>
     * min(max(n' - 100 +1 , 0), 100)
     * <p>
     * 因此 [0, n] 中[百位]上数字1出现的次数为
     * (n/1000) * 100 + min(max(n mod 1000 - 100 + 1, 0), 100)
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
