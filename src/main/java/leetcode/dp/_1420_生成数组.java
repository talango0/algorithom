package leetcode.dp;
//给你三个整数 n、m 和 k 。下图描述的算法用于找出正整数数组中最大的元素。
//
//
//
// 请你生成一个具有下述属性的数组 arr ：
//
//
// arr 中有 n 个整数。
// 1 <= arr[i] <= m 其中 (0 <= i < n) 。
// 将上面提到的算法应用于 arr ，search_cost 的值等于 k 。
//
//
// 返回上述条件下生成数组 arr 的 方法数 ，由于答案可能会很大，所以 必须 对 10^9 + 7 取余。
//
//
//
// 示例 1：
//
// 输入：n = 2, m = 3, k = 1
//输出：6
//解释：可能的数组分别为 [1, 1], [2, 1], [2, 2], [3, 1], [3, 2] [3, 3]
//
//
// 示例 2：
//
// 输入：n = 5, m = 2, k = 3
//输出：0
//解释：没有数组可以满足上述条件
//
//
// 示例 3：
//
// 输入：n = 9, m = 1, k = 1
//输出：1
//解释：可能的数组只有 [1, 1, 1, 1, 1, 1, 1, 1, 1]
//
//
// 示例 4：
//
// 输入：n = 50, m = 100, k = 25
//输出：34549172
//解释：不要忘了对 1000000007 取余
//
//
// 示例 5：
//
// 输入：n = 37, m = 17, k = 7
//输出：418930126
//
//
//
//
// 提示：
//
//
// 1 <= n <= 50
// 1 <= m <= 100
// 0 <= k <= n
//
//
// Related Topics 动态规划 👍 71 👎 0


/**
 * @author mayanwei
 * @date 2022-09-14.
 */
public class _1420_生成数组{
    /**
     *                                 j-1
     * f[i][s][j] = f[i-1][s][j] * j +  ∑ f[i-1][s-1][j]
     *                                  1
     * i 表述数组长度为i
     * s 表示搜索代价为s
     * j 表示数组中最大数为j
     *
     * 时间复杂度 O(N*M^2*K)
     * 空间复杂度 O(N*M*K)
     */
    class Solution{
        int[][][] f = new int[51][51][101];
        final int MOD = 1000000007;

        public int numOfArrays(int n, int m, int k) {
            // 不存在搜索代价为 0 的数组
            if (k == 0) {
                return 0;
            }

            // 边界条件，所有长度为 1 的数组的搜索代价都为 1
            for (int j = 1; j <= m; j++) {
                f[1][1][j] = 1;
            }
            for (int i = 2; i <= n; ++i) {
                // 搜索代价不会超过数组长度
                for (int s = 1; s <= k && s <= i; ++s) {
                    for (int j = 1; j <= m; ++j) {
                        f[i][s][j] = (int) ((long) f[i - 1][s][j] * j % MOD);
                        for (int j0 = 1; j0 < j; ++j0) {
                            f[i][s][j] += f[i - 1][s - 1][j0];
                            f[i][s][j] %= MOD;
                        }
                    }
                }
            }

            // 最终的答案是所有 f[n][k][..] 的和
            // 即数组长度为 n，搜索代价为 k，最大值任意
            int ans = 0;
            for (int j = 1; j <= m; ++j) {
                ans += f[n][k][j];
                ans %= MOD;
            }
            return ans;
        }
    }


    /**
     * 前缀和优化
     * 时间复杂度 O(N*M^2*K)
     * 空间复杂度 O(N*M*K)
     */
    class Solution0{
        int[][][] f = new int[51][51][101];
        final int MOD = 1000000007;

        public int numOfArrays(int n, int m, int k) {
            // 不存在搜索代价为 0 的数组
            if (k == 0) {
                return 0;
            }

            // 边界条件，所有长度为 1 的数组的搜索代价都为 1
            for (int j = 1; j <= m; j++) {
                f[1][1][j] = 1;
            }
            for (int i = 2; i <= n; ++i) {
                // 搜索代价不会超过数组长度
                for (int s = 1; s <= k && s <= i; ++s) {
                    // 利用前缀和优化
                    int preSum_j = 0;
                    for (int j = 1; j <= m; j++) {
                        f[i][s][j] = (int) ((long) f[i - 1][s][j] * j % MOD);
                        f[i][s][j] = (f[i][s][j] + preSum_j) % MOD;
                        preSum_j = (preSum_j + f[i-1][s-1][j]) % MOD;
                    }
                }
            }

            // 最终的答案是所有 f[n][k][..] 的和
            // 即数组长度为 n，搜索代价为 k，最大值任意
            int ans = 0;
            for (int j = 1; j <= m; ++j) {
                ans += f[n][k][j];
                ans %= MOD;
            }
            return ans;
        }
    }
}
