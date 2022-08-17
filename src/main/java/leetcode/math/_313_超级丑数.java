package leetcode.math;
//超级丑数 是一个正整数，并满足其所有质因数都出现在质数数组 primes 中。
//
//给你一个整数 n 和一个整数数组 primes ，返回第 n 个 超级丑数 。
//
//题目数据保证第 n 个 超级丑数 在 32-bit 带符号整数范围内。
//
//示例 1：
//
//输入：n = 12,
//primes
// =
//[2,7,13,19]
//
//输出：32
//解释：给定长度为 4 的质数数组 primes = [2,7,13,19]，前 12 个超级丑数序列为：[1,2,4,7,8,13,14,16,19,26,28,32] 。
//示例 2：
//
//输入：n = 1, primes = [2,3,5]
//输出：1
//解释：1 不含质因数，因此它的所有质因数都在质数数组 primes = [2,3,5] 中。
//提示：
//
//1 <= n <= 105
//1 <= primes.length <= 100
//2 <= primes[i] <= 1000
//题目数据 保证 primes[i] 是一个质数
//primes 中的所有值都 互不相同 ，且按 递增顺序 排列
//Related Topics
//
//👍 326, 👎 0
import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-08-16.
 */
public class _313_超级丑数{
    class Solution {
        public int nthSuperUglyNumber(int n, int[] primes) {
            // 优先队列中装3元组 int[]{product, prime, pi}
            // 其中 product 代表链表节点的值，prime 是计算下一个节点所需要的质数因子，pi 代表链表上的指针。
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)->{
                // 优先级队列按照节点的值排序
                return a[0] - b[0];
            });
            // 把多条链表的头结点加入优先级队列
            for (int i = 0; i < primes.length; i++) {
                pq.offer(new int[]{1, primes[i], 1});
            }

            // 可以理解为最终合并的有序链表（结果链表）
            int [] ugly = new int[n+1];
            // 可以理解为结果链表上的指针
            int p = 1;
            while (p <= n) {
                // 取3个链表的最小结点
                int[] pair = pq.poll();
                int product = pair[0];
                int prime = pair[1];
                int index = pair[2];

                // 避免结果链表出现重复
                if (product != ugly[p-1]) {
                    // 连接到结果链表上
                    ugly[p] = product;
                    p++;
                }
                int [] nextPair = new int[]{ugly[index] * prime, prime, index + 1};
                pq.offer(nextPair);
            }
            return ugly[n];
        }
    }
}
