package leetcode.程序员面试金典;
//在一个整数数组中，“峰”是大于或等于相邻整数的元素，相应地，“谷”是小于或等于相邻整数的元素。
// 例如，在数组{5, 8, 4, 2, 3, 4, 6}中，{8, 6}是峰，
// {5, 2}是谷。现在给定一个整数数组，将该数组按峰与谷的交替顺序排序。
//
//示例:
//
//输入: [5, 3, 1, 2, 3]
//输出:[5, 1, 3, 2, 3]
//提示：
//
//nums.length <= 10000
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/peaks-and-valleys-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-05-31.
 */
public class _10_11_峰与谷{
    /**
     * 时间复杂度 O(nlogn)
     */
    class Solution {
        public void wiggleSort(int[] nums) {
            Arrays.sort(nums);
            for (int i = 1; i<nums.length; i+=2) {
                if (nums[i] > nums[i-1]) {
                    swap(nums, i, i-1);
                }
            }
        }
        void swap(int [] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }


    /**
     * 时间复杂度O(n)
     */
    class Solution1 {
        public void wiggleSort(int[] nums) {
            // i为偶数 nums[i] >= nums[i-1]
            // i为奇数 nums[i] <= nums[i-1]
            for (int i = 1; i<nums.length; i++) {
                if ((((i&1)==1 )&& nums[i]>nums[i-1]) || (((i&1)==0 )&& nums[i]<nums[i-1]) ) {
                    swap(nums, i, i-1);
                }
            }
        }
        void swap(int [] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }

}
