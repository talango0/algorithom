package leetcode;

import java.util.Arrays;

public class _4_寻找两个正序数组中的中位数 {

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
// 👍 3297 👎 0


    //leetcode submit region begin(Prohibit modification and deletion)
    static class Solution {
            public double findMedianSortedArrays(int[] nums1, int[] nums2) {
                int length = nums1.length + nums2.length;
                double result = 0;
                //分别进行奇数偶数处理
                if(length % 2 != 0){
                    result = getNum(nums1,nums2,length/2);
                }else{
                    result = getNum(nums1,nums2,length/2-1)/2 + getNum(nums1,nums2,length/2)/2;
                }
                return result;
            }

            public double getNum(int[] nums1, int[] nums2, int k){
                int[] result = new int[nums1.length+nums2.length];
                int i = 0, j = 0;
                int cur = 0;
                while(i < nums1.length && j <nums2.length && cur <= k){
                    if(nums1[i] < nums2[j]) result[cur++] = nums1[i++];
                    else result[cur++] = nums2[j++];
                }
                while(i < nums1.length && cur <=k) result[cur++] = nums1[i++];
                while(j < nums2.length && cur <=k) result[cur++] = nums2[j++];
                return result[cur-1];
            }

    }
    //leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {

    }
}
