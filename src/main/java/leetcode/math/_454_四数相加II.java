package leetcode.math;
//给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
//
//
// 0 <= i, j, k, l < n
// nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
//
//
//
//
// 示例 1：
//
//
//输入：nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
//输出：2
//解释：
//两个元组如下：
//1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1)
// + 2 = 0
//2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1)
// + 0 = 0
//
//
// 示例 2：
//
//
//输入：nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
//输出：1
//
//
//
//
// 提示：
//
//
// n == nums1.length
// n == nums2.length
// n == nums3.length
// n == nums4.length
// 1 <= n <= 200
// -2²⁸ <= nums1[i], nums2[i], nums3[i], nums4[i] <= 2²⁸
//
//
// Related Topics 数组 哈希表 👍 659 👎 0

import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2022-09-25.
 */
public class _454_四数相加II{
    /**
     * 分组 + 哈希表
     * 将四个数组分成两部分， A和B位一组，C和D位另外一组。
     * 对于A，B使用二重循环对它们进行遍历。得到所有 A[i] + B[j] 的值饼存入哈希映射中。对于哈希映射中的每个键值对，每个键表示一种 A[i] + B[j] 对应的值为 A[i] 和 B[i] 出现次数。
     * 对于 C 和 D，我们同样适用二重循环进行比那里，当遍历到 C[k] + D[j] 时，如果 -(C[k] + D[l]) 出现在哈希映着中，那么将  -(C[k] + D[l]) 对应的值累加进答案中。
     * <p>
     * 最终即可达到满足 A[i] + B[j] + C[k] + D[l] = 0 的四元组数目。
     *
     * 时间复杂度 O(n^2)
     * 空间复杂度 O(n^2)
     *
     * @see _18_四数之和
     */
    class Solution{
        public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
            Map<Integer, Integer> countAB = new HashMap<>();
            for (int u : A) {
                for (int v : B) {
                    countAB.put(u + v, countAB.getOrDefault(u + v, 0) + 1);
                }

            }
            int ans = 0;
            for (int u : C) {
                for (int v : D) {
                    if (countAB.containsKey(-u - v)) {
                        ans += countAB.get(-u - v);
                    }
                }
            }
            return ans;
        }
    }

}
