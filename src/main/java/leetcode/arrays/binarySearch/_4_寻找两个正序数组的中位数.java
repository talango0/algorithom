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

    /**
     * 时间复杂度：每进行一次循环，我们就减少 k/2 个元素，所以时间复杂度是 O(log(k)，而 k=(m+n)/2，所以最终的复杂也就是
     * O(log(m+n）。
     * <p>
     * 空间复杂度：虽然我们用到了递归，但是可以看到这个递归属于尾递归，所以编译器不需要不停地堆栈，所以空间复杂度为
     * O(1)。
     */
    class Solution2{
        public double findMedianSortedArrays(int[] A, int[] B) {
            int n = A.length;
            int m = B.length;
            int left = (n + m + 1) / 2;
            int right = (n + m + 2) / 2;
            // 将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k
            return (getKth(A, 0, n - 1, B, 0, m - 1, left) + getKth(A, 0, n - 1, B, 0, m - 1, right)) * 0.5;
        }

        private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
            int len1 = end1 - start1 + 1;
            int len2 = end2 - start2 + 1;
            // 让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是len1
            if (len1 > len2) {
                return getKth(nums2, start2, end2, nums1, start1, end1, k);
            }
            if (len1 == 0) {
                return nums2[start2 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[start1], nums2[start2]);
            }
            int i = start1 + Math.min(len1, k / 2) - 1;
            int j = start2 + Math.min(len2, k / 2) - 1;

            if (nums1[i] > nums2[j]) {
                return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
            }
            else {
                return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
            }
        }
    }


    class Solution3{
        public double findMedianSortedArrays(int[] A, int[] B) {
            int m = A.length;
            int n = B.length;
            if (m > n) {
                return findMedianSortedArrays(B, A);
            }
            int iMin = 0, iMax = m;
            while (iMax <= m) {
                int i = (iMax + iMax) / 2;
                int j = (m + n + 1) / 2 - i;
                if (j != 0 && i != m && B[j - 1] > A[i]) {
                    // i需要增大
                    iMin = i - 1;
                }
                else if (i != 0 && j != n && A[i - 1] > B[j]) {
                    // i需要减小
                    iMax = i - 1;
                }
                else {
                    // 达到要求， 并且将边界条件列出来单独考虑
                    int maxLeft = 0;
                    if (i == 0) {
                        maxLeft = B[j - 1];
                    }
                    else if (j == 0) {
                        maxLeft = A[i - 1];
                    }
                    else {
                        maxLeft = Math.max(A[i - 1], B[j - 1]);
                    }
                    // 奇数的话不需要考虑右半部分
                    if ((m + n) % 2 == 1) {
                        return maxLeft;
                    }

                    int minRight = 0;
                    if (i == m) {
                        minRight = B[j];
                    }
                    else if (j == n) {
                        minRight = A[i];
                    }
                    else {
                        minRight = Math.min(B[j], A[i]);
                    }
                    return (maxLeft + minRight) * 0.5;

                }
            }
            return 0.0;
        }

    }
}
