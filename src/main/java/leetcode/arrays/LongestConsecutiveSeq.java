package leetcode.arrays;


import java.util.Arrays;

/**
 * 给定一个未排序的整数数组，找出最长连续序列的长度。
 *
 *  要求算法的时间复杂度为 O(n)。
 *
 *         示例:
 *
 *         输入: [100, 4, 200, 1, 3, 2]
 *         输出: 4
 *         解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为 4。
 */
public class  LongestConsecutiveSeq {

    static class Solution {
        public int longestConsecutive(int[] nums) {
            if(nums ==null || nums.length < 1){
                return 0;
            }
            Arrays.sort(nums);
            int k = 1;
            int res = 1;
            for(int i=1;i<nums.length;i++){
                if(nums[i] == nums[i-1]) {
                    continue;
                }
                if(nums[i] == nums[i-1]+1) {
                    k++;
                    res = Math.max(res, k);
                }else{
                   k = 1;
                }
            }
            return res;
        }

    }

    public static void main(String[] args) {
        //int [] nums = {100, 4, 200, 1, 3, 2};

        int [] nums = {1,2,0,1};

        Solution solution = new Solution();

        System.out.println(solution.longestConsecutive(nums));
    }
}
