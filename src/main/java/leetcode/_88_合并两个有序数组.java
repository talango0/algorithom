package leetcode;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import static org.junit.Assert.*;


//给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
//
//
//
// 说明：
//
//
// 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
// 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
//
//
//
//
// 示例：
//
//
//输入：
//nums1 = [1,2,3,0,0,0], m = 3
//nums2 = [2,5,6],       n = 3
//
//输出：[1,2,2,3,5,6]
//
//
//
// 提示：
//
//
// -10^9 <= nums1[i], nums2[i] <= 10^9
// nums1.length == m + n
// nums2.length == n
//
// Related Topics 数组 双指针
// 👍 678 👎 0
public class _88_合并两个有序数组 {


    /**
     * 从前往后往nums1插入
     */
    class Solution1 {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            if(m == 0){
                if(n == 0){
                    return;
                }else {
                    System.arraycopy(nums2,0,nums1,0,n);
                }
            }
            if(n == 0){
                return;
            }

            int [] n1 = Arrays.copyOfRange(nums1,0,m);
            int i = 0, j = 0, k = 0;
            while (i<m && j<n){
                nums1[k++] =  n1[i] < nums2[j] ? n1[i++] :  nums2[j++];
            }
            while (i < m){
                nums1[k++] = n1[i++];
            }
            while (j < n){
                nums1[k++] = nums2[j++];
            }
            return;
        }

    }


    /**
     * 从后往前往num1中比较插入
     */
    class Solution2 {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            // two get pointers for nums1 and nums2
            int p1 = m - 1;
            int p2 = n - 1;
            // set pointer for nums1
            int p = m + n - 1;

            // while there are still elements to compare
            while ((p1 >= 0) && (p2 >= 0)){
                // compare two elements from nums1 and nums2
                // and add the largest one in nums1
                nums1[p--] = (nums1[p1] < nums2[p2]) ? nums2[p2--] : nums1[p1--];
            }

            // add missing elements from nums2
            System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
        }
    }

@Test
public void TestSolution1(){
    int [] nums1 = {1,2,3,0,0,0};
    int m = 3;
    int [] nums2 = {2,5,6};
    int n = 3;
    Solution2 solution = new Solution2();
    solution.merge(nums1,m,nums2,n);
    assertArrayEquals(nums1,new int[]{1,2,2,3,5,6});
}

}
