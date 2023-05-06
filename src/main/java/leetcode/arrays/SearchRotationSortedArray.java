package leetcode.arrays;

import leetcode.arrays.binarySearch._33_搜索旋转排序数组;

/**
 * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
 * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
 * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
 * 你可以假设数组中不存在重复的元素。
 * 你的算法时间复杂度必须是 O(log n) 级别。
 *
 * 示例 1:
 * 输入: nums = [4,5,6,7,0,1,2], target = 0
 * 输出: 4
 *
 * 示例 2:
 * 输入: nums = [4,5,6,7,0,1,2], target = 3
 * 输出: -1
 *
 * @see _33_搜索旋转排序数组
 */
public class SearchRotationSortedArray {
    /**
     * 思路：原版的二分查找是要求有序，而有序序列经过旋转后变成了部分有序，关键在于如果找到有序的部分。
     * 首先看总共有多少种旋转方式
     *
     * 0,1,2,4,5,6,7
     * 1,2,4,5,6,7,0
     * 2,4,5,6,7,0,1
     * 4,5,6,7,0,1,2
     * 5,6,7,0,1,2,4
     * 6,7,0,1,2,4,5
     * 7,0,1,2,4,5,6
     *
     * 发现规律：
     * 中间的数若大于最左边，则左侧部分有序
     * 中间的数
     */
    static class Solution {
        public int search(int[] nums, int target) {
            if(nums.length<1)return -1;
            if(nums.length == 1 ){
                return target == nums[0]? 0: -1;
            }

            int index;
            int left = 0;
            int right = nums.length-1;

            while (left<=right){
                index = (left + right)/2;
                if (target == nums[index] ){
                    return index;
                }
                if(nums[left] <= nums[index]){ //左边有序
                    if(target < nums[index] && target>=nums[left]){//target位于左边有序部分
                        right = index - 1;
                    }else{//target位于右边边无序部分
                        left = index + 1;
                    }
                }else{ //右边有序
                    if(target>nums[index]  && target<=nums[right]){//target位于右边有序部分
                        left = index + 1;
                    }else{//target位于左边边无序部分
                        right = index  -1;
                    }
                }
            }

            return -1;
        }
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        int []nums = {4,5,6,7,0,1,2};
        int target = 5;
        int index = solution.search(nums,target);
        System.out.println(index);

    }


    /**
     * public static int search(int[] nums, int target) {
     *         int top=nums.length-1,bot=0,maxPos;
     *         if(nums.length==0)
     *         	return -1;
     *         if(nums.length==1) {
     *         	if(target==nums[0]) {
     *         		return 0;
     *                        }
     *         	else return -1;
     *         }
     *         if(nums[bot]<nums[top])
     *         	return binarySearch(bot,top,target,nums);
     *         maxPos = findMax(nums);
     *         if(nums[bot]<=target)
     *         	return binarySearch(bot,maxPos,target,nums);
     *         else
     *         	return binarySearch(maxPos+1,top,target,nums);
     *     }
     *
     *     public static int findMax(int[] nums) {
     *         int low = 0,high = nums.length-1,mid;
     *         if(nums[low]<nums[high])
     *         	return high;
     *     	while(!(high-low==1)) {
     *     		mid = (low + high)/2;
     *     		if(nums[mid]>=nums[low]) {//
     *     			low = mid;
     *     		}
     *     		else if(nums[mid]<=nums[high]) {
     *     			high = mid;
     *     		}
     *     	}
     *     	System.out.println(nums[high]+"         "+nums[low]);
     *     	return low;
     *     }
     *
     *     public static int binarySearch(int low,int high,int target,int[] nums) {
     *     	int mid;
     *     	while(low<=high) {
     *     		mid = (low+high)/2;
     *     		if(target==nums[mid])
     *     			return mid;
     *     		if(target>nums[mid])
     *     			low = mid +1;
     *     		else if(target<nums[mid])
     *     			high = mid-1;
     *     	}
     *     	return -1;
     *     }
     */

    /**
     * class Solution {
     *     public int search(int[] nums, int target) {
     *         if(nums == null || nums.length == 0)
     *             return -1;
     *         int ans = searchCore(nums,0,nums.length -1 ,target);
     *         return ans;
     *     }
     *     public int searchCore(int[] arr,int low,int high,int target){
     *         int mid;
     *         int ans = -1;
     *         mid = (low + high) >> 1;
     *         if(low > high){
     *             return -1;
     *         }
     *         if(arr[mid] == target){
     *             return mid;
     *         }
     *         //左半边有序 且target在这个范围之内
     *         else if(arr[low] < arr[mid] && target < arr[mid] && target >= arr[low]){
     *             ans =  searchCore(arr,low,mid-1,target);
     *         }else if(arr[mid] < arr[high] && target <= arr[high] && target > arr[mid]){
     *             ans = searchCore(arr,mid+1,high,target);
     *         }
     *         if(ans == -1){
     *             ans =  searchCore(arr,low,mid-1,target);
     *         }
     *         return ans !=-1? ans : searchCore(arr,mid+1,high,target);
     *     }
     * }
     */
}
