package leetcode.arrays;

import java.util.Stack;

/**
 * 给定一个未经排序的整数数组，找到最长且连续的的递增序列。
 *
 * 示例 1:
 *
 * 输入: [1,3,5,4,7]
 * 输出: 3
 * 解释: 最长连续递增序列是 [1,3,5], 长度为3。
 * 尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为5和7在原数组里被4隔开。
 * 示例 2:
 *
 * 输入: [2,2,2,2,2]
 * 输出: 1
 * 解释: 最长连续递增序列是 [2], 长度为1。
 * 注意：数组长度不会超过10000。
 */
public class LongestContinousIncreasingSequence {

    /**
     * 思路：
     * 要求连续递增
     * 可以将递增的序列压栈,栈中元素最多的即为最长连续序列
     */
    static class Solution {
        public int findLengthOfLCIS(int[] nums) {
            if(nums==null||nums.length<1){
                return 0;
            }
            Stack<Integer> longestContinousSeqStack = new Stack<>();
            int length=1;
            longestContinousSeqStack.push(nums[0]);
            for(int i=1;i<nums.length;i++){

                Integer peek = longestContinousSeqStack.peek();
                if(peek<nums[i]){
                    longestContinousSeqStack.push(nums[i]);
                    if(longestContinousSeqStack.size()>length){
                        length = longestContinousSeqStack.size();
                    }
                }else{
                     if(longestContinousSeqStack.size()>length){
                         length = longestContinousSeqStack.size();
                     }else{
                         longestContinousSeqStack.clear();
                         longestContinousSeqStack.push(nums[i]);
                     }
                }
            }
            return length;
        }
    }

    public static void main(String[] args) {
//       int [] nums = {1,3,5,7};
//       int [] nums = {1,3,5,4,7};
        int [] nums = {2,2,2,2,2};
        Solution solution = new Solution();
        int length = solution.findLengthOfLCIS(nums);
        System.out.println(length);
    }


    /**
     * class Solution {
     *     public int findLengthOfLCIS(int[] nums) {
     *          if (nums.length < 2) {
     *             return nums.length;
     *         }
     *         int k = 1;
     *         int max = 1;
     *         for (int i = 0; i < nums.length - 1; i++) {
     *             if (nums[i + 1] > nums[i]) {
     *                 k++;
     *                 max = Math.max(max, k);
     *             } else {
     *                 k = 1;
     *             }
     *         }
     *         return max;
     *
     *     }
     * }
     */
}
