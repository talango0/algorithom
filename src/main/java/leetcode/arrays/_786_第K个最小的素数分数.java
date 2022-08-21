package leetcode.arrays;

//给你一个按递增顺序排序的数组 arr 和一个整数 k 。数组 arr 由 1 和若干 素数 组成，且其中所有整数互不相同。
//
//对于每对满足 0 <= i < j < arr.length 的 i 和 j ，可以得到分数 arr[i] / arr[j] 。
//
//那么第 k 个最小的分数是多少呢? 以长度为 2 的整数数组返回你的答案, 这里 answer[0] == arr[i] 且 answer[1] == arr[j] 。
//
//示例 1：
//
//输入：arr = [1,2,3,5], k = 3
//输出：[2,5]
//解释：已构造好的分数,排序后如下所示:
//1/5, 1/3, 2/5, 1/2, 3/5, 2/3
//很明显第三个最小的分数是 2/5
//示例 2：
//
//输入：arr = [1,7], k = 1
//输出：[1,7]
//提示：
//
//2 <= arr.length <= 1000
//1 <= arr[i] <= 3 * 104
//arr[0] == 1
//arr[i] 是一个 素数 ，i > 0
//arr 中的所有数字 互不相同 ，且按 严格递增 排序
//1 <= k <= arr.length * (arr.length - 1) / 2
//Related Topics
//
//👍 228, 👎 0


import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-08-21.
 */
public class _786_第K个最小的素数分数{

    /**
     * 快速排序
     */
    class Solution{
        public int[] kthSmallestPrimeFraction(int[] arr, int k) {
            int n = arr.length;
            List<int[]> frac = new ArrayList<int[]>();
            for (int i = 0; i < n; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    frac.add(new int[]{arr[i], arr[j]});
                }
            }
            //a/b < c/d  <==> a*d < b*c
            Collections.sort(frac, (x, y) -> x[0] * y[1] - y[0] * x[1]);
            return frac.get(k - 1);
        }
    }

    /**
     * 优先级队列
     * <p>
     * 时间复杂度：O(klogn)，其中 n 是数组 arr 的长度。优先队列的单次操作时间复杂度为O(logn)，一共需要进行 O(k) 次操作。
     * <p>
     * 空间复杂度：O(n)，即为优先队列需要使用的空间。
     */
    class Solution2{
        /**
         * 思路：当分母为给定的 arr[j] 时，分子可以在 arr[0],⋯,arr[j−1] 中进行选择。由于数组 arr 是严格递增的，那么记分子为
         * arr[i](0≤i<j)，随着 i 的增加，分数的值也是严格递增的。
         */
        public int[] kthSmallestPrimeFraction(int[] arr, int k) {
            int n = arr.length;
            PriorityQueue<int[]> pq = new PriorityQueue<int[]>((x, y) -> {
                return arr[x[0]] * arr[y[1]] - arr[x[1]] * arr[y[0]];
            });
            // arr[0] 列表含有0个，直接忽略，从1开始
            for (int j = 1; j < n; j++) {
                pq.offer(new int[]{0, j});
            }
            for (int i = 1; i < k; i++) {
                int[] frac = pq.poll();
                int x = frac[0], y = frac[1];
                if (x + 1 < y) {
                    pq.offer(new int[]{x + 1, y});
                }
            }
            return new int[]{arr[pq.peek()[0]], arr[pq.peek()[1]]};
        }
    }

    class Solution3{
        /**
         * 思路：二分查找+双指针
         */
        public int[] kthSmallestPrimeFraction(int[] arr, int k) {
            int n = arr.length;
            double left = 0.0, right = 1.0;
            while (true) {
                double mid = (left + right) / 2;
                int i = -1, count = 0;
                // 记录最大的分数
                int x = 0, y = 1;

                for (int j = 1; j < n; ++j) {
                    while ((double) arr[i + 1] / arr[j] < mid) {
                        ++i;
                        if (arr[i] * y > arr[j] * x) {
                            x = arr[i];
                            y = arr[j];
                        }
                    }
                    count += i + 1;
                }

                if (count == k) {
                    return new int[]{x, y};
                }
                if (count < k) {
                    left = mid;
                }
                else {
                    right = mid;
                }
            }
        }
    }

    @Test
    public void test() {
        int[] arr = new int[]{1, 2, 3, 5};
        int k = 3;
        int[] res = new int[]{2, 5};
        Solution solution = new Solution();
        Assert.assertArrayEquals(solution.kthSmallestPrimeFraction(arr, k), res);
    }
}
