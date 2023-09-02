package leetcode.arrays;

//给定一个由整数数组 A 表示的环形数组 C，求 C 的非空子数组的最大可能和。
// 在此处，环形数组意味着数组的末端将会与开头相连呈环状。（形式上，当0 <= i < A.length 时 C[i] = A[i]，且当 i >= 0 时
//C[i+A.length] = C[i]）
// 此外，子数组最多只能包含固定缓冲区 A 中的每个元素一次。（形式上，对于子数组 C[i], C[i+1], ..., C[j]，不存在 i <= k1,
//k2 <= j 其中 k1 % A.length = k2 % A.length）
//
//
//
// 示例 1：
// 输入：[1,-2,3,-2]
//输出：3
//解释：从子数组 [3] 得到最大和 3
//
//
// 示例 2：
// 输入：[5,-3,5]
//输出：10
//解释：从子数组 [5,5] 得到最大和 5 + 5 = 10
//
//
// 示例 3：
// 输入：[3,-1,2,-1]
//输出：4
//解释：从子数组 [2,-1,3] 得到最大和 2 + (-1) + 3 = 4
//
//
// 示例 4：
// 输入：[3,-2,2,-3]
//输出：3
//解释：从子数组 [3] 和 [3,-2,2] 都可以得到最大和 3
//
//
// 示例 5：
// 输入：[-2,-3,-1]
//输出：-1
//解释：从子数组 [-1] 得到最大和 -1
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

import leetcode.dp._53_最大子数组和;
import org.junit.Assert;
import org.junit.Test;

/**
 * @see _53_最大子数组和
 */
public class _918_环形子数组{


    /**
     * <pre>
     * 环形数组和普通数组的区别是环形数组多一种情况，就是子数组取了头尾相连的情况。
     * 也就是头部取从a[0]到a[i], 然后尾部取a[j]到a[N-1]， 然后0 <= i < j <= N-1
     * 对于任何一个i，从a[0]到a[i]的和可以简单的从前向后遍历计算，可以建立表格left[i] = sum(a[0]….a[i])
     * 对于任何一个j，从a[j]到a[N-1]的和可以简单的从后向前计算, 可以建立表格right[j] = sum(a[j]…a[N-1])
     * 由于i < j, 所以我们可以修改left和right表格，可以在遍历数组k=0…N-1的时候，
     * 对于任何一个k, left[k]表示从a[0]开始到a[i], 其中i <=k 的最大和, right[k]表示从a[j]到a[N-1]，其中j >k的最大和，
     * 这样的话，一次遍历, 然后取left[k] + right[k+1]的最大值，就可以了。
     * 复杂度分析
     * 时间复杂度：O(N)，其中 N 是 A 的长度。
     * 空间复杂度：O(N)。
     * </pre>
     */
    class Solution{
        public int maxSubarraySumCircular(int[] A) {
            int N = A.length;

            int ans = A[0], cur = A[0];
            for (int i = 1; i < N; ++i) {
                cur = A[i] + Math.max(cur, 0);
                ans = Math.max(ans, cur);
            }

            // ans is the answer for 1-interval subArrays.
            // Now, let's consider all 2-interval subArrays.
            // For each i, we want to know
            // the maximum of sum(A[j:]) with j >= i+2

            // rightSums[i] = A[i] + A[i+1] + ... + A[N-1]
            int[] rightSums = new int[N];
            rightSums[N - 1] = A[N - 1];
            for (int i = N - 2; i >= 0; --i)
                rightSums[i] = rightSums[i + 1] + A[i];

            // maxRight[i] = max_{j >= i} rightSums[j]
            int[] maxRight = new int[N];
            maxRight[N - 1] = A[N - 1];
            for (int i = N - 2; i >= 0; --i) {
                maxRight[i] = Math.max(maxRight[i + 1], rightSums[i]);
            }

            int leftSum = 0;
            for (int i = 0; i < N - 2; ++i) {
                leftSum += A[i];
                ans = Math.max(ans, leftSum + maxRight[i + 2]);
            }
            return ans;
        }
    }

    @Test
    public void test() {
        Solution solution = new Solution();
        Assert.assertEquals(solution.maxSubarraySumCircular(new int[]{3, -1, 2, -1}), 4);
    }

}
