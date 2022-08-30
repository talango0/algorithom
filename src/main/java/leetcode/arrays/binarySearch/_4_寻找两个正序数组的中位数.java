package leetcode.arrays.binarySearch;
//给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
//
//算法的时间复杂度应该为 O(log (m+n)) 。
//
//示例 1：
//
//输入：nums1 = [1,3], nums2 = [2]
//输出：2.00000
//解释：合并数组 = [1,2,3] ，中位数 2
//示例 2：
//
//输入：nums1 = [1,2], nums2 = [3,4]
//输出：2.50000
//解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
//提示：
//
//nums1.length == m
//nums2.length == n
//0 <= m <= 1000
//0 <= n <= 1000
//1 <= m + n <= 2000
//-106 <= nums1[i], nums2[i] <= 106
//Related Topics
//
//👍 5786, 👎 0

/**
 * @author mayanwei
 * @date 2022-08-30.
 */
public class _4_寻找两个正序数组的中位数{
    // O(m+n) 不满足题意

    class Solution{
        public double findMedianSortedArrays(int[] A, int[] B) {
            int m = A.length;
            int n = B.length;
            int len = m + n;
            int left = -1, right = -1;
            // 分别指向当前数组A 和 数组B的指针
            int aStart = 0, bStart = 0;
            for (int i = 0; i <= len / 2; i++) {
                left = right;
                if (aStart < m && (bStart >= n || A[aStart] < B[bStart])) {
                    right = A[aStart++];
                }
                else {
                    right = B[bStart++];
                }
            }
            if ((len & 1) == 0) {
                return (left + right) / 2.0;
            }
            else {
                return right;
            }
        }
    }
}
