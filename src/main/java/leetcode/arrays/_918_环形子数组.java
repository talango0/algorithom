package leetcode.arrays;

public class _918_环形子数组 {

    //给定一个由整数数组 A 表示的环形数组 C，求 C 的非空子数组的最大可能和。
//
// 在此处，环形数组意味着数组的末端将会与开头相连呈环状。（形式上，当0 <= i < A.length 时 C[i] = A[i]，且当 i >= 0 时
//C[i+A.length] = C[i]）
//
// 此外，子数组最多只能包含固定缓冲区 A 中的每个元素一次。（形式上，对于子数组 C[i], C[i+1], ..., C[j]，不存在 i <= k1,
//k2 <= j 其中 k1 % A.length = k2 % A.length）
//
//
//
// 示例 1：
//
// 输入：[1,-2,3,-2]
//输出：3
//解释：从子数组 [3] 得到最大和 3
//
//
// 示例 2：
//
// 输入：[5,-3,5]
//输出：10
//解释：从子数组 [5,5] 得到最大和 5 + 5 = 10
//
//
// 示例 3：
//
// 输入：[3,-1,2,-1]
//输出：4
//解释：从子数组 [2,-1,3] 得到最大和 2 + (-1) + 3 = 4
//
//
// 示例 4：
//
// 输入：[3,-2,2,-3]
//输出：3
//解释：从子数组 [3] 和 [3,-2,2] 都可以得到最大和 3
//
//
// 示例 5：
//
// 输入：[-2,-3,-1]
//输出：-1
//解释：从子数组 [-1] 得到最大和 -1
//
//
//
//
// 提示：
//
//
// -30000 <= A[i] <= 30000
// 1 <= A.length <= 30000
//
// Related Topics 数组
// 👍 127 👎 0


    /**
     * 复杂度分析
     *
     * 时间复杂度：O(N)O(N)，其中 NN 是 A 的长度。
     * 空间复杂度：O(N)O(N)。
     */
    class Solution {
        public int maxSubarraySumCircular(int[] A) {
            int N = A.length;

            int ans = A[0], cur = A[0];
            for (int i = 1; i < N; ++i) {
                cur = A[i] + Math.max(cur, 0);
                ans = Math.max(ans, cur);
            }

            // ans is the answer for 1-interval subarrays.
            // Now, let's consider all 2-interval subarrays.
            // For each i, we want to know
            // the maximum of sum(A[j:]) with j >= i+2

            // rightsums[i] = A[i] + A[i+1] + ... + A[N-1]
            int[] rightsums = new int[N];
            rightsums[N-1] = A[N-1];
            for (int i = N-2; i >= 0; --i)
                rightsums[i] = rightsums[i+1] + A[i];

            // maxright[i] = max_{j >= i} rightsums[j]
            int[] maxright = new int[N];
            maxright[N-1] = A[N-1];
            for (int i = N-2; i >= 0; --i) {
                maxright[i] = Math.max(maxright[i+1], rightsums[i]);
            }

            int leftsum = 0;
            for (int i = 0; i < N-2; ++i) {
                leftsum += A[i];
                ans = Math.max(ans, leftsum + maxright[i+2]);
            }

            return ans;

        }
    }


}
