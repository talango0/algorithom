package leetcode.math;

import java.util.Arrays;
//给定整数 n ，返回 所有小于非负整数 n 的质数的数量 。
//
//
//
// 示例 1：
//
//
//输入：n = 10
//输出：4
//解释：小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
//
//
// 示例 2：
//
//
//输入：n = 0
//输出：0
//
//
// 示例 3：
//
//
//输入：n = 1
//输出：0
//
//
//
//
// 提示：
//
//
// 0 <= n <= 5 * 10⁶
//
//
// Related Topics 数组 数学 枚举 数论 👍 932 👎 0
/**
 * @author mayanwei
 * @date 2022-08-05.
 */
public class _204_计数质数{
    class Solution {
        //12 = 2 × 6
        // 12 = 3 × 4
        // 12 = sqrt(12) × sqrt(12)
        // 12 = 4 × 3
        // 12 = 6 × 2
        // 换句话说，如果在 [2,sqrt(n)] 这个区间之内没有发现可整除因子，就可以直接断定 n 是素数了，因为在区间 [sqrt(n),n] 也一定不会发现可整除因子
        // 从 2 开始知道 2是一个素数 那么 2*2 = 4， 3*2 = 6， 4*2 = 8 ... 都不可能是素数了
        // 3也是素数，那么 3*2 = 6， 3*3 = 9，3*4 = 12... 也都不是素数了
        // Sieve of Eratosthenes
        // n/2 + n/3 + n/5 + n/7 + … = n × (1/2 + 1/3 + 1/5 + 1/7…)
        // 括号中是素数的倒数。其最终结果是 O(N * loglogN)
        public int countPrimes(int n) {
            boolean [] isPrimes = new boolean[n];
            // 将数组初始化为 true
            Arrays.fill(isPrimes, true);

            for (int i = 2; i * i< n; i++) {
                if (isPrimes(i)) {
                    // i的倍数不可能是素数了
                    for (int j = i*i; j < n; j+=i) {
                        isPrimes[j] = false;
                    }
                }
            }
            int count = 0;
            for (int i = 2; i < n; i++) {
                if (isPrimes[i]) count++;
            }
            return count;
        }

        boolean isPrimes(int n) {
            for (int i = 2; i * i <= n; i++) {
                if (n % i == 0) {
                    // 有其他整除因子
                    return false;
                }
            }
            return true;
        }
    }
}
