package leetcode.arrays;

public class _4_寻找两个正序数组的中位数 {

//给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
//
// 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
//
//
//
// 示例 1：
//
// 输入：nums1 = [1,3], nums2 = [2]
//输出：2.00000
//解释：合并数组 = [1,2,3] ，中位数 2
//
//
// 示例 2：
//
// 输入：nums1 = [1,2], nums2 = [3,4]
//输出：2.50000
//解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
//
//
// 示例 3：
//
// 输入：nums1 = [0,0], nums2 = [0,0]
//输出：0.00000
//
//
// 示例 4：
//
// 输入：nums1 = [], nums2 = [1]
//输出：1.00000
//
//
// 示例 5：
//
// 输入：nums1 = [2], nums2 = []
//输出：2.00000
//
//
//
//
// 提示：
//
//
// nums1.length == m
// nums2.length == n
// 0 <= m <= 1000
// 0 <= n <= 1000
// 1 <= m + n <= 2000
// -106 <= nums1[i], nums2[i] <= 106
//
// Related Topics 数组 二分查找 分治算法
// 👍 3444 👎 0

    /**
     * 分析：
     * A[m], B[n]
     * 若 m + n 为奇数， 则中位数为两个有序数组的第 (m+n)/2 个元素。
     * 若 m + n 为偶数，则中位数为两个有序数组的第 (m+n)/2 个元素 和 (m+n)/2 + 1 个元素 的均值。
     *
     * 因此题目可以转换为求两个有序数组的第k个元素。其中k为  (m+n)/2 或  (m+n)/2 + 1 。
     *
     * 可以比较 A[k/2 - 1] 和 B[k/2 - 1] ,
     * 1) 如果 A[k/2 - 1] < B[k/2 - 1], 则比 A[k/2 -1] 小的数最多只有A的前 k/2 - 1 个数和B的前 k/2 - 1 个数 即比 A[k/2 -1] 小的数最
     * 多只有 k - 2 个，因此 A[k/2 -1] 不可能是第 k 个数。 A[0 .. k/2-1] 也都不可能是第 k 个数，可以全部排除。
     * 2) 如果 B[k/2 - 1] < A[k/2 - 1], 则可以排除 B[0 .. k/2-1] 。
     * 3) 如果 A[k/2 - 1] = B[k/2 - 1], 则可以归入第一类情况处理。
     * 在排除后的新数组上继续进行二分查找，并且根据排除数的个数，减少 k 的值，这是因为我们排除的数都是不大于第 k 小的数。
     *
     * 下面3中情况需要特殊处理(边界条件)。
     * 1） 如果A[k/2-1] 或者 B[k/2-1] 越界，那么我们可以选取对应数组中的最后一个元素。在这种情况下，我们必须根据排除的个数减少k的值，而不能直
     * 接将k减去k/2。
     * 2） 如果一个数组为空，说明该数组中的所有元素都被排除，我们可以直接返回另一个数组中的第k小的元素。
     * 3） 如果k=1，我只需要返回两个数组首元素的最小值。
     */
    static class Solution1 {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int m = nums1.length, n = nums2.length, total = m+n;
            double media;
            int midIndex = total >> 1;
            if(total % 2 == 1){
                media = getKthElement(nums1, nums2, midIndex+1);
            }else{
                media = (getKthElement(nums1, nums2, midIndex) + getKthElement(nums1, nums2, midIndex+1))/2.0;
            }
            return media;
        }

        private double getKthElement(int[] nums1, int[] nums2, int k) {
            int m = nums1.length, n = nums2.length;
            int index1 = 0, index2 = 0;
            while (true){
                if(index1 == m){
                    return nums2[index2 + k -1];
                }
                if(index2 == n){
                    return nums1[index1 + k -1];
                }
                if(k == 1){
                    return Math.min(nums1[index1], nums2[index2]);
                }

                int half = k >> 1;
                int newIndex1 = Math.min(index1 + half, m) - 1;
                int newIndex2 = Math.min(index2 + half, n) - 1;
                int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
                if(pivot1 <= pivot2){
                    k -= (newIndex1 - index1 + 1);
                    index1 = newIndex1 + 1;
                }else{
                    k -= (newIndex2 - index2 + 1);
                    index2 = newIndex2 + 1;
                }
            }
        }

    }




    static class Solution2 {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            if (nums1.length > nums2.length) {
                return findMedianSortedArrays(nums2, nums1);
            }

            int m = nums1.length;
            int n = nums2.length;
            int left = 0, right = m, ansi = -1;
            // median1：前一部分的最大值
            // median2：后一部分的最小值
            int median1 = 0, median2 = 0;

            while (left <= right) {
                // 前一部分包含 nums1[0 .. i-1] 和 nums2[0 .. j-1]
                // 后一部分包含 nums1[i .. m-1] 和 nums2[j .. n-1]
                int i = (left + right) / 2;
                int j = (m + n + 1) / 2 - i;

                // nums_im1, nums_i, nums_jm1, nums_j 分别表示 nums1[i-1], nums1[i], nums2[j-1], nums2[j]
                int nums_im1 = (i == 0 ? Integer.MIN_VALUE : nums1[i - 1]);
                int nums_i = (i == m ? Integer.MAX_VALUE : nums1[i]);
                int nums_jm1 = (j == 0 ? Integer.MIN_VALUE : nums2[j - 1]);
                int nums_j = (j == n ? Integer.MAX_VALUE : nums2[j]);

                if (nums_im1 <= nums_j) {
                    ansi = i;
                    median1 = Math.max(nums_im1, nums_jm1);
                    median2 = Math.min(nums_i, nums_j);
                    left = i + 1;
                }
                else {
                    right = i - 1;
                }
            }

            return (m + n) % 2 == 0 ? (median1 + median2) / 2.0 : median1;
        }
    }



}
